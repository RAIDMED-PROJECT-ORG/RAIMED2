<script setup lang="ts">
import PrescriptionRow from '@/components/modal/prescriptionModal/PrescriptionRow.vue';
import type { Prescription } from '@/models/prescription/prescription.model';
import GenericList from '@/components/modal/genericModal/GenericList.vue';

defineProps<{
  prescriptions: Prescription[];
}>();

defineEmits<{
  (e: 'deletePrescription', id: string): void;
  (e: 'updatePrescription', id: string): void;
}>();
</script>

<template>
  <GenericList
    title="Liste des prescriptions"
    size="small"
    :columns="{
      'Type': 15,
      'Prescription': 25,
      'Résultat': 45,
      'Actions': 15
    }"
  >
    <PrescriptionRow
      v-for="(prescription, index) in prescriptions"
      :key="index"
      :id="prescription.id"
      :type="prescription.type"
      :name="prescription.name"
      :result="prescription.result ?? ''"
      @delete-prescription="() => $emit('deletePrescription', prescription.id)"
      @update-prescription="() => $emit('updatePrescription', prescription.id)"
    />
  </GenericList>
</template>

<style scoped></style>
