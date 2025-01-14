<script setup lang="ts">
import { Color } from '@/models/new-patient/color.model';
import GenericModal from '@/components/modal/genericModal/GenericModal.vue';
import PrescriptionListe from '@/components/modal/prescriptionModal/PrescriptionListe.vue';
import PrescriptionForm from '@/components/modal/prescriptionModal/PrescriptionForm.vue';
import { ref } from 'vue';
import type { Prescription } from '@/models/prescription/prescription.model';
import { PrescriptionType } from '@/models/prescription/prescriptionType.enum';

const props = defineProps<{
  prescriptions: Prescription[];
  initialType: PrescriptionType;
  existingPrescriptions: Prescription[];
  onValidation: (data: Prescription[]) => void;
  onBack: () => void;
}>();

const prescriptions = ref<Prescription[]>(props.prescriptions);

const prescriptionToUpdate = ref<Prescription>();

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
</script>

<template>
  <GenericModal
      :title="`Prescriptions`"
      validationLabel="Valider"
      :onValidation="handleValidation"
      :headerColor="Color.Purple"
      :onBack="onBack"
  >
    <div class="max-h-[60vh] flex py-5 px-10">
      <PrescriptionListe
          :prescriptions="prescriptions"
          @delete-prescription="deletePrescription"
          @update-prescription="updatePrescription"
      />
      <PrescriptionForm
          @add-prescription="insertOrUpdatePrescription"
          :prescription-to-update="prescriptionToUpdate"
          :prescriptions="prescriptions"
          :initial-type="props.initialType"
          :existing-prescriptions="props.existingPrescriptions"
      />
    </div>
  </GenericModal>
</template>

<style scoped></style>