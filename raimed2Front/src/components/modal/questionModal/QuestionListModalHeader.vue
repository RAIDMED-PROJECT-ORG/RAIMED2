<script setup lang="ts">
import {
  getQuestionFilterDisplayName,
  QuestionFilter
} from '@/models/question/questionFilter.enum';

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
  <div class="flex gap-5 px-10">
    <span class="flex items-center gap-1">
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
    <span class="flex items-center gap-1">
      <label for="filter-search" class="whitespace-nowrap font-bold">Filtre</label>
      <select
        id="filter-search"
        class="w-full border border-gray-300 rounded-lg p-2"
        aria-label="Genre concerné"
        v-model="model.genderFilter"
      >
        <option :value="null"></option>
        <option :value="QuestionFilter.MIXED">
          {{ getQuestionFilterDisplayName(QuestionFilter.MIXED) }}
        </option>
        <option :value="QuestionFilter.FEMALE">
          {{ getQuestionFilterDisplayName(QuestionFilter.FEMALE) }}
        </option>
        <option :value="QuestionFilter.MALE">
          {{ getQuestionFilterDisplayName(QuestionFilter.MALE) }}
        </option>
      </select>
    </span>
  </div>
</template>

<style scoped></style>