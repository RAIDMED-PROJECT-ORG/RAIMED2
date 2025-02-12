<script setup lang="ts">
import { faSearch, faSliders } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { PrescriptionType } from '@/models/prescription/prescriptionType.enum';

defineProps<{
  searchQuery: string;
  selectedType: PrescriptionType | null;
}>();

const emits = defineEmits<{
  (e: 'updateSearchQuery', value: string): void;
  (e: 'updateSelectedType', value: PrescriptionType | null): void;
}>();

const updateSearchQuery = (event: Event) => {
  emits('updateSearchQuery', (event.target as HTMLInputElement).value);
};

const updateSelectedType = (event: Event) => {
  const value = (event.target as HTMLSelectElement).value;
  emits('updateSelectedType', value ? (value as PrescriptionType) : null);
};
</script>

<template>
  <div class="flex gap-4 mb-4">
    <div class="flex-1">
      <label for="search" class="font-bold">
        <FontAwesomeIcon :icon="faSearch" class="mr-2" />
        Recherche
      </label>
      <input
        id="search"
        type="text"
        :value="searchQuery"
        @input="updateSearchQuery"
        class="w-full border border-gray-300 rounded p-2"
        placeholder="Rechercher par nom..."
      />
    </div>
    <div class="flex-1">
      <label for="type" class="font-bold">
        <FontAwesomeIcon :icon="faSliders" class="mr-2" />
        Type
      </label>
      <select
        id="type"
        :value="selectedType"
        @change="updateSelectedType"
        class="w-full border border-gray-300 rounded p-2"
      >
        <option :value="null">Tous les types</option>
        <option :value="PrescriptionType.BIOLOGY">Biologie</option>
        <option :value="PrescriptionType.IMAGERY">Imagerie</option>
        <option :value="PrescriptionType.BIOPSY">Biopsie</option>
      </select>
    </div>
  </div>
</template>

<style scoped></style>
