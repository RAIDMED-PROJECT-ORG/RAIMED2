<script setup lang="ts">
import PrescriptionRow from '@/components/modal/prescriptionModal/PrescriptionRow.vue';
import type { Prescription } from '@/models/prescription/prescription.model';

defineProps<{
  prescriptions: Prescription[];
}>();

defineEmits<{
  (e: 'deletePrescription', id: string): void;
  (e: 'updatePrescription', id: string): void;
}>();
</script>

<template>
  <div class="flex flex-col border-r border-1 pr-5">
    <h3 class="self-center text-black font-bold text-xl mb-3">Liste des prescriptions</h3>
    <div class="pr-5 overflow-scroll">
      <div class="flex justify-between border-b border-1 border-[#9CA3AF] text-black font-bold">
        <h4>Prescriptions</h4>
        <h4>Actions</h4>
      </div>
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
    </div>
  </div>
</template>

<style scoped></style>