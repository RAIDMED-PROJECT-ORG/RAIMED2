<script setup lang="ts">
import axiosInstance from '@/service/httpClient/axios.config';
import { useDiagnosticStore } from '@/stores/diagnostic.store';
import { useQuery } from '@tanstack/vue-query';
import { storeToRefs } from 'pinia';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import ErrorAlert from '@/components/alert/ErrorAlert.vue';
import { type ExamType, ExamTypeDisplayNames } from '@/models/exam/examType.enum';
import type { Action } from '@/models/virtual-patient/action.model';
import { useApiToLocalPatientMapper } from '@/composable/useApiToLocalPatientMapper';
import { ZoneDisplayNames, Zones } from '@/models/diagnostic/exam.model';

const props = defineProps<{
  examType: ExamType;
  handleOnClose: () => void;
  handleOnAskExam: (actionId: string) => void;
}>();

const { virtualPatient } = storeToRefs(useDiagnosticStore());
const router = useRouter();
const { mapApiActionToLocalExam } = useApiToLocalPatientMapper();
const diagnosticId = router.currentRoute.value.params.diagnosticId as string;

const selectedExamId = ref<string>('');

const queryAllExam = useQuery({
  queryKey: ['getAllExam'],
  queryFn: () =>
    axiosInstance.get<Action[]>(`/diagnostic/${diagnosticId}/exam/${props.examType}`).then((res) => {
      const validActions = res.data.filter((action): action is Action => action.id !== undefined);

      return mapApiActionToLocalExam(validActions, props.examType);
    }),
  refetchOnWindowFocus: false,
  retry: false
});

const handleOnClickAskExam = () => {
  if (selectedExamId.value) {
    const actionAssociatedToExam = virtualPatient.value?.actions?.find(
      (action) => action.id === selectedExamId.value
    );
    if (actionAssociatedToExam && actionAssociatedToExam.id) {
      props.handleOnAskExam(actionAssociatedToExam.id);
    }
  }
};
</script>

<template>
  <div class="w-full h-full card flex-col bg-base-100 rounded-lg shadow-lg border">
    <div class="w-full h-10 min-h-10 flex justify-center items-center bg-slate-50 rounded-lg">
      <h2 class="font-bold">{{ ExamTypeDisplayNames[props.examType] }}</h2>
    </div>
    <div class="h-[60dvh] grow">
      <template v-if="queryAllExam.isLoading.value">
        <div class="flex justify-center items-center h-full">
          <span class="loading loading-dots loading-lg"></span>
        </div>
      </template>

      <template v-else-if="queryAllExam.isError.value">
        <div class="flex justify-center items-center h-full">
          <ErrorAlert
            class="w-3/4"
            :message="
              'Erreur lors du chargement du panel ' +
              ExamTypeDisplayNames[props.examType].toLowerCase() +
              '. Réessayez ultérieurement.'
            "
          />
        </div>
      </template>

      <template v-else>
        <div class="w-full max-h-full h-full overflow-y-auto">
          <template v-if="!queryAllExam.data.value?.length">
            <div class="w-full h-full flex justify-center items-center">
              <p class="text-center text-2xl">
                Aucune
                {{ ExamTypeDisplayNames[props.examType].toLowerCase() }} disponible
              </p>
            </div>
          </template>
          <template v-else>
            <table class="table">
              <tbody>
                <tr
                  v-for="exam in queryAllExam.data.value"
                  :key="exam.signs.join('')"
                  :class="{ 'bg-base-200': selectedExamId === exam.id, hover: true }"
                  @click="selectedExamId = exam.id ?? ''"
                  class="cursor-pointer"
                >
                  <td>{{ ZoneDisplayNames[exam.zone as Zones] }}</td>
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
        @click="handleOnClickAskExam"
        :disabled="!selectedExamId"
      >
        Soumettre l'examen
      </button>
    </div>
  </div>
</template>
