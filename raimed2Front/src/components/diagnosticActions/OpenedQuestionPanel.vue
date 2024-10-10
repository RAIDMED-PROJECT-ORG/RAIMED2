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
  handleOnAskOpenedQuestion: (actionId: string) => void;
}>();

const { getVirtualPatientDetails, virtualPatient } = storeToRefs(useDiagnosticStore());
const router = useRouter();
const diagnosticId: String = router.currentRoute.value.params.diagnosticId as string;

// Selected opened question id
const selectedOpenedQuestionId = ref<string>('');

/**
 * Query to fetch all opened questions available for the virtual patient
 */
const queryAllOpenedQuestion = useQuery({
  queryKey: ['getAllOpenedQuestion'],
  queryFn: () =>
    axiosInstance
      .get<Partial<Question>[]>(`/diagnostic/${diagnosticId}/openedQuestion`, {
        params: { virtualPatientId: getVirtualPatientDetails.value.id }
      })
      .then((res) => res.data),
  refetchOnWindowFocus: false,
  retry: false
});

/**
 * Handle the click on the button to ask an opened question
 */
const handleOnClickAskOpenedQuestion = () => {
  if (selectedOpenedQuestionId.value) {
    const actionAssociatedToQuestion = virtualPatient.value?.actions.find(
      (action) => action.question?.id === selectedOpenedQuestionId.value
    );
    if (actionAssociatedToQuestion) {
      props.handleOnAskOpenedQuestion(actionAssociatedToQuestion.id);
    }
  }
};
</script>

<template>
  <div class="w-full h-full card flex-col bg-base-100 rounded-lg shadow-lg border">
    <div class="w-full h-10 min-h-10 flex justify-center items-center bg-slate-50 rounded-lg">
      <h2 class="font-bold">Questions ouvertes</h2>
    </div>
    <div class="h-[60dvh] grow">
      <template v-if="queryAllOpenedQuestion.isLoading.value">
        <div class="flex justify-center items-center h-full">
          <span class="loading loading-dots loading-lg"></span>
        </div>
      </template>

      <template v-else-if="queryAllOpenedQuestion.isError.value">
        <div class="flex justify-center items-center h-full">
          <ErrorAlert class="w-3/4" message="Erreur lors du chargement des questions ouvertes" />
        </div>
      </template>

      <template v-else>
        <div class="w-full max-h-full h-full overflow-y-auto">
          <template v-if="!queryAllOpenedQuestion.data.value?.length">
            <div class="w-full h-full flex justify-center items-center">
              <p class="text-center text-2xl">Aucune question ouverte disponible</p>
            </div>
          </template>
          <template v-else>
            <table class="table">
              <tbody>
                <tr
                  v-for="question in queryAllOpenedQuestion.data.value"
                  :key="question.id"
                  :class="`${
                    selectedOpenedQuestionId === question.id ? 'bg-base-200' : 'hover'
                  } cursor-pointer`"
                  @click="() => (selectedOpenedQuestionId = question.id ?? '')"
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
        @click="handleOnClickAskOpenedQuestion"
        :disabled="!selectedOpenedQuestionId"
      >
        Poser la question
      </button>
    </div>
  </div>
</template>
