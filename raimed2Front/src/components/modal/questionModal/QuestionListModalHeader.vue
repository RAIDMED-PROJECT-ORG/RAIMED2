<script setup lang="ts">
import {
  QuestionFilter, QuestionFilterDisplayNames
} from '@/models/question/questionFilter.enum';
import ClassicSelector from '@/components/classicSelector/ClassicSelector.vue';

const model = defineModel<{
  nameFilter: string;
  genderFilter: QuestionFilter | null;
}>({
  default: {
    nameFilter: '',
    genderFilter: null
  }
});
</script>

<template>
  <div class="flex justify-around gap-5">
    <span class="flex items-center gap-3">
      <label for="question-search" class="whitespace-nowrap font-bold">Recherche par nom</label>
      <input
        type="text"
        id="question-search"
        class="w-[80%] border border-gray-300 rounded-lg p-2"
        placeholder="Insérer le nom..."
        aria-label="Nom de la question à chercher"
        v-model="model.nameFilter"
      />
    </span>
    <span class="flex items-center gap-3">
      <label for="filter-search" class="whitespace-nowrap font-bold">Genre</label>
      <select
        id="filter-search"
        class="w-full border border-gray-300 rounded-lg p-2"
        aria-label="Genre concerné"
        v-model="model.genderFilter"
      >
        <option :value="null"></option>
        <option :value="QuestionFilter.MIXED">
          {{ QuestionFilterDisplayNames[QuestionFilter.MIXED] }}
        </option>
        <option :value="QuestionFilter.FEMALE">
          {{ QuestionFilterDisplayNames[QuestionFilter.FEMALE] }}
        </option>
        <option :value="QuestionFilter.MALE">
          {{ QuestionFilterDisplayNames[QuestionFilter.MALE] }}
        </option>
      </select>
    </span>
  </div>
</template>

<style scoped></style>