<script setup lang="ts">
import type { Question } from '@/models/question/question.model';
import axiosInstance from '@/service/httpClient/axios.config';
import { useDiagnosticStore } from '@/stores/diagnostic.store';
import { useQuery } from '@tanstack/vue-query';
import { storeToRefs } from 'pinia';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import ErrorAlert from '@/components/alert/ErrorAlert.vue';
import {
  type PrescriptionType,
  PrescriptionTypeDisplayNames
} from '@/models/prescription/prescriptionType.enum';

const props = defineProps<{
  prescriptionType: PrescriptionType;
  handleOnClose: () => void;
  handleOnAskPrescription: (actionId: string) => void;
}>();

const { virtualPatient } = storeToRefs(useDiagnosticStore());
const router = useRouter();
const diagnosticId: String = router.currentRoute.value.params.diagnosticId as string;

const selectedPrescriptionId = ref<string>('');

const queryAllPrescription = useQuery({
  queryKey: ['getAllPrescription'],
  queryFn: () =>
    axiosInstance
      .get<Partial<Question>[]>(`/diagnostic/${diagnosticId}/prescription/${props.prescriptionType}`, {
        params: {
        }
      })
      .then((res) => res.data),
  refetchOnWindowFocus: false,
  retry: false
});


const handleOnClickAskPrescription = () => {
  if (selectedPrescriptionId.value) {
    const actionAssociatedToPrescription = virtualPatient.value?.actions?.find(
      (action) => action.prescription?.id === selectedPrescriptionId.value
    );
    if (actionAssociatedToPrescription && actionAssociatedToPrescription.id) {
      props.handleOnAskPrescription(actionAssociatedToPrescription.id);
    }
  }
};
</script>

<template>
  <div class="w-full h-full card flex-col bg-base-100 rounded-lg shadow-lg border">
    <div class="w-full h-10 min-h-10 flex justify-center items-center bg-slate-50 rounded-lg">
      <h2 class="font-bold">{{ PrescriptionTypeDisplayNames[props.prescriptionType] }}</h2>
    </div>
    <div class="h-[60dvh] grow">
      <template v-if="queryAllPrescription.isLoading.value">
        <div class="flex justify-center items-center h-full">
          <span class="loading loading-dots loading-lg"></span>
        </div>
      </template>

      <template v-else-if="queryAllPrescription.isError.value">
        <div class="flex justify-center items-center h-full">
          <ErrorAlert
            class="w-3/4"
            :message="'Erreur lors du chargement du panel ' + PrescriptionTypeDisplayNames[props.prescriptionType].toLowerCase() + '. Réessayez ultérieurement.'"
          />
        </div>
      </template>

      <template v-else>
        <div class="w-full max-h-full h-full overflow-y-auto">
          <template v-if="!queryAllPrescription.data.value?.length">
            <div class="w-full h-full flex justify-center items-center">
              <p class="text-center text-2xl">
                Aucune
                {{ PrescriptionTypeDisplayNames[props.prescriptionType].toLowerCase() }} disponible
              </p>
            </div>
          </template>
          <template v-else>
            <table class="table">
              <tbody>
                <tr
                  v-for="prescription in queryAllPrescription.data.value"
                  :key="prescription.id"
                  :class="`${
                    selectedPrescriptionId === prescription.id ? 'bg-base-200' : 'hover'
                  } cursor-pointer`"
                  @click="() => (selectedPrescriptionId = prescription.id ?? '')"
                >
                  <td>{{ prescription.content }}</td>
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
        @click="handleOnClickAskPrescription"
        :disabled="!selectedPrescriptionId"
      >
        Soumettre la prescription
      </button>
    </div>
  </div>
</template>
