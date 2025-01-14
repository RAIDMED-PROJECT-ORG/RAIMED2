<script setup lang="ts">
import type { Prescription } from '@/models/prescription/prescription.model';

const props = defineProps<{
  prescriptions: Prescription[];
  selectedPrescriptions: Set<string>;
}>();

const emits = defineEmits<{
  (e: 'toggleSelection', id: string): void;
}>();

const toggleSelection = (id: string) => {
  emits('toggleSelection', id);
};
</script>

<template>
  <div class="overflow-y-auto max-h-96">
    <table class="w-full table-auto">
      <thead>
      <tr class="bg-gray-100">
        <th class="p-2">Type</th>
        <th class="p-2">Nom</th>
        <th class="p-2">Résultat</th>
        <th class="p-2">Sélectionner</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="prescription in prescriptions" :key="prescription.id">
        <td class="p-2">{{ prescription.type }}</td>
        <td class="p-2">{{ prescription.name }}</td>
        <td class="p-2">{{ prescription.result }}</td>
        <td class="p-2 text-center">
          <input type="checkbox" :value="prescription.id" @change="toggleSelection(prescription.id)" />
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped></style>