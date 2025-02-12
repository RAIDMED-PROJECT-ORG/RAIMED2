<script setup lang="ts">
import { QuestionFilter, QuestionFilterDisplayNames } from '@/models/question/questionFilter.enum';
import { Gender } from '@/models/virtual-patient/gender.enum';
import { onMounted } from 'vue';
import { QuestionType, QuestionTypeDisplayNames } from '@/models/question/questionType.enum';

const props = defineProps<{
  patientGender: Gender;
}>();

const model = defineModel<{
  nameFilter: string;
  genderFilter: QuestionFilter | null;
  typeFilter: QuestionType | null;
}>({
  default: {
    nameFilter: '',
    genderFilter: null,
    typeFilter: null,
  }
});

onMounted(() => {
  model.value.genderFilter = QuestionFilter[props.patientGender];
});
</script>

<template>
  <div class="flex justify-around gap-5">
    <span class="flex items-center gap-3">
      <label for="question-search" class="whitespace-nowrap font-bold">Recherche par nom</label>
      <input
        type="text"
        id="question-search"
        class="text-input"
        placeholder="Insérer le nom..."
        aria-label="Nom de la question à chercher"
        v-model="model.nameFilter"
      />
    </span>
    <span class="flex items-center gap-3">
      <label for="type-search" class="whitespace-nowrap font-bold">Type</label>
      <select
        id="type-search"
        class="w-full border border-gray-300 rounded-lg p-2"
        aria-label="type de question"
        v-model="model.typeFilter"
      >
        <option :value="null"></option>
        <option :value="QuestionType.CLOSED">
          {{ QuestionTypeDisplayNames[QuestionType.CLOSED] }}
        </option>
        <option :value="QuestionType.OPENED">
          {{ QuestionTypeDisplayNames[QuestionType.OPENED] }}
        </option>
      </select>
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
        <option v-if="patientGender === Gender.FEMALE" :value="QuestionFilter.FEMALE">
          {{ QuestionFilterDisplayNames[QuestionFilter.FEMALE] }}
        </option>
        <option v-if="patientGender === Gender.MALE" :value="QuestionFilter.MALE">
          {{ QuestionFilterDisplayNames[QuestionFilter.MALE] }}
        </option>
      </select>
    </span>
  </div>
</template>

<style scoped></style>
