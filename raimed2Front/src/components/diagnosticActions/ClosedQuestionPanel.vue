<script setup lang="ts">
import type { Question } from '@/models/question/question.model';
import axiosInstance from '@/service/httpClient/axios.config';
import { useDiagnosticStore } from '@/stores/diagnostic.store';
import { useQuery } from '@tanstack/vue-query';
import { storeToRefs } from 'pinia';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import ErrorAlert from '@/components/alert/ErrorAlert.vue';

const props = defineProps<{
  handleOnClose: () => void;
  handleOnAskClosedQuestion: (actionId: string) => void;
}>();

const { getVirtualPatientDetails, virtualPatient } = storeToRefs(useDiagnosticStore());
const router = useRouter();
const diagnosticId: String = router.currentRoute.value.params.diagnosticId as string;

// Selected closed question id
const selectedClosedQuestionId = ref<string>('');

/**
 * Query to fetch all closed questions available for the virtual patient
 */
const queryAllClosedQuestion = useQuery({
  queryKey: ['getAllClosedQuestion'],
  queryFn: () =>
    axiosInstance
      .get<Partial<Question>[]>(`/diagnostic/${diagnosticId}/closedQuestion`, {
        params: { virtualPatientId: getVirtualPatientDetails.value.id }
      })
      .then((res) => res.data),
  refetchOnWindowFocus: false,
  retry: false
});

/**
 * Handle the click on the button to ask a closed question
 */
const handleOnClickAskClosedQuestion = () => {
  if (selectedClosedQuestionId.value) {
    const actionAssociatedToQuestion = virtualPatient.value?.actions.find(
      (action) => action.question?.id === selectedClosedQuestionId.value
    );
    if (actionAssociatedToQuestion) {
      props.handleOnAskClosedQuestion(actionAssociatedToQuestion.id);
    }
  }
};
</script>

<template>
  <div class="w-full h-full card flex-col bg-base-100 rounded-lg shadow-lg border">
    <div class="w-full h-10 min-h-10 flex justify-center items-center bg-slate-50 rounded-lg">
      <h2 class="font-bold">Questions fermées</h2>
    </div>
    <div class="h-[60dvh] grow">
      <template v-if="queryAllClosedQuestion.isLoading.value">
        <div class="flex justify-center items-center h-full">
          <span class="loading loading-dots loading-lg"></span>
        </div>
      </template>

      <template v-else-if="queryAllClosedQuestion.isError.value">
        <div class="flex justify-center items-center h-full">
          <ErrorAlert class="w-3/4" message="Erreur lors du chargement des questions fermées" />
        </div>
      </template>

      <template v-else>
        <div class="w-full max-h-full h-full p-4 overflow-auto">
          <template v-if="!queryAllClosedQuestion.data.value?.length">
            <p class="text-center">Aucune question fermée disponible</p>
          </template>
          <template v-else>
            <table class="table">
              <tbody>
                <tr
                  v-for="question in queryAllClosedQuestion.data.value"
                  :key="question.id"
                  :class="`${
                    selectedClosedQuestionId === question.id ? 'bg-base-200' : 'hover'
                  } cursor-pointer`"
                  @click="() => (selectedClosedQuestionId = question.id ?? '')"
                >
                  <td>{{ question.content }}</td>
                </tr>
              </tbody>
            </table>
          </template>
        </div>
      </template>
    </div>
    <div class="w-full flex justify-center items-center p-2">
      <button class="btn m-1" @click="handleOnClose">Annuler</button>
      <button
        class="btn btn-success text-white"
        @click="handleOnClickAskClosedQuestion"
        :disabled="!selectedClosedQuestionId"
      >
        Poser la question
      </button>
    </div>
  </div>
</template>
