<script setup lang="ts">
import { Color } from '@/models/new-patient/color.model';
import GenericModal from '@/components/modal/genericModal/GenericModal.vue';
import { ref, watch } from 'vue';
import type { Prescription } from '@/models/prescription/prescription.model';
import PrescriptionList from '@/components/modal/prescriptionModal/PrescriptionList.vue';
import PrescriptionForm from '@/components/modal/prescriptionModal/PrescriptionForm.vue';
import { type PrescriptionType, PrescriptionTypeDisplayNames } from '@/models/prescription/prescriptionType.enum';

const props = defineProps<{
  currentPrescriptions: Prescription[];
  prescriptionType: PrescriptionType;
  onValidation: (data: Prescription[]) => void;
  onBack: () => void;
}>();

const prescriptions = ref<Prescription[]>([...props.currentPrescriptions]);
const prescriptionToUpdate = ref<Prescription>();
const confirmGoBack = ref<boolean>(false);

const deletePrescription = (id: string) => {
  prescriptions.value = prescriptions.value.filter((prescription) => prescription.id !== id);
};

const updatePrescription = (id: string) => {
  prescriptionToUpdate.value = prescriptions.value.find((prescription) => prescription.id === id);
};

const insertOrUpdatePrescription = (prescription: Prescription) => {
  const index = prescriptions.value.findIndex((p) => p.id === prescription.id);
  if (index === -1) {
    prescriptions.value.push(prescription);
  } else {
    prescriptions.value[index] = prescription;
  }
};

const handleValidation = () => {
  props.onValidation(prescriptions.value);
};

watch(
  () => prescriptions.value,
  (newPrescription) => {
    confirmGoBack.value = JSON.stringify(props.currentPrescriptions) !== JSON.stringify(newPrescription);
  },
  { deep: true }
);
</script>

<template>
  <GenericModal
    :title="'Prescription ' + PrescriptionTypeDisplayNames[props.prescriptionType]"
    validationLabel="Valider"
    :onValidation="handleValidation"
    :headerColor="Color.Purple"
    :onBack="onBack"
    :confirmGoBack="confirmGoBack"
  >
    <div class="w-[70vw] h-[60vh] flex px-10">
      <PrescriptionList
        :prescriptions="prescriptions"
        @delete-prescription="deletePrescription"
        @update-prescription="updatePrescription"
      />
      <PrescriptionForm
        :prescription-type="props.prescriptionType"
        :prescription-to-update="prescriptionToUpdate"
        @add-prescription="insertOrUpdatePrescription"
      />
    </div>
  </GenericModal>
</template>

<style scoped></style>
