<script setup lang="ts">
import type { Question } from '@/models/question/question.model';
import axiosInstance from '@/service/httpClient/axios.config';
import { useDiagnosticStore } from '@/stores/diagnostic.store';
import { useQuery } from '@tanstack/vue-query';
import { storeToRefs } from 'pinia';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import ErrorAlert from '@/components/alert/ErrorAlert.vue';
import type { Precision } from '@/models/question/precision.model';
import type { Action } from '@/models/virtual-patient/action.model';
import { useApiToLocalPatientMapper } from '@/composable/useApiToLocalPatientMapper';

const props = defineProps<{
  handleOnClose: () => void;
  handleOnAskPrecision: (actionId: string) => void;
}>();
const { mapApiActionToLocalPrecision } = useApiToLocalPatientMapper();
const { virtualPatient } = storeToRefs(useDiagnosticStore());
const router = useRouter();
const diagnosticId: String = router.currentRoute.value.params.diagnosticId as string;

const selectedPrecisionId = ref<string>('');

const queryAllPrecision = useQuery({
  queryKey: ['getAllExam'],
  queryFn: () =>
    axiosInstance.get<Action[]>(`/diagnostic/${diagnosticId}/precision`).then((res) => {
      const validActions = res.data.filter((action): action is Action => action.id !== undefined);

      return mapApiActionToLocalPrecision(validActions);
    }),
  refetchOnWindowFocus: false,
  retry: false
});

const title = 'Précision de symptôme';

const handleOnClickAskPrecision = () => {
  if (selectedPrecisionId.value) {
    const actionAssociatedToPrecision = virtualPatient.value?.actions?.find(
      (action) => action.id === selectedPrecisionId.value
    );
    if (actionAssociatedToPrecision && actionAssociatedToPrecision.id) {
      props.handleOnAskPrecision(actionAssociatedToPrecision.id);
    }
  }
};
</script>

<template>
  <div class="w-full h-full card flex-col bg-base-100 rounded-lg shadow-lg border">
    <div class="w-full h-10 min-h-10 flex justify-center items-center bg-slate-50 rounded-lg">
      <h2 class="font-bold">{{ title }}</h2>
    </div>
    <div class="h-[60dvh] grow">
      <template v-if="queryAllPrecision.isLoading.value">
        <div class="flex justify-center items-center h-full">
          <span class="loading loading-dots loading-lg"></span>
        </div>
      </template>

      <template v-else-if="queryAllPrecision.isError.value">
        <div class="flex justify-center items-center h-full">
          <ErrorAlert
            class="w-3/4"
            :message="'Erreur lors du chargement du panel ' + title + '. Réessayez ultérieurement.'"
          />
        </div>
      </template>

      <template v-else>
        <div class="w-full max-h-full h-full overflow-y-auto">
          <template v-if="!queryAllPrecision.data.value?.length">
            <div class="w-full h-full flex justify-center items-center">
              <p class="text-center text-2xl">Aucune précision de symptôme disponible</p>
            </div>
          </template>
          <template v-else>
            <table class="table">
              <thead>
                <tr>
                  <th>Primary Element</th>
                  <th>Question</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="precision in queryAllPrecision.data.value"
                  :key="precision.id"
                  :class="`${
                    selectedPrecisionId === precision.id ? 'bg-base-200' : 'hover'
                  } cursor-pointer`"
                  @click="() => (selectedPrecisionId = precision.id ?? '')"
                >
                  <td>{{ precision.primaryElement }}</td>
                  <td>{{ precision.question }}</td>
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
        @click="handleOnClickAskPrecision"
        :disabled="!selectedPrecisionId"
      >
        Soumettre la demande de précision
      </button>
    </div>
  </div>
</template>
