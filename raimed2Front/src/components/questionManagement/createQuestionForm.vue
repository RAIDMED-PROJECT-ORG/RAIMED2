<script setup lang="ts">
import { ref } from 'vue';
import { QuestionType } from '@/models/question/questionType.enum';
import {
  QuestionFilter
} from '@/models/question/questionFilter.enum';
import type { CreateQuestionDto } from '@/models/question/createQuestion.dto';

// Define props to use in the component
const props = defineProps<{
  type: QuestionType;
  isLoading: boolean;
  createQuestion: (createQuestionDto: CreateQuestionDto) => void;
}>();

// Manage question creation form state
const createQuestionDto = ref<CreateQuestionDto>({
  content: '',
  filter: QuestionFilter.MIXED,
  type: props.type
});

/**
 * Handle question creation by calling the parent component's createQuestion function
 * @param event The form submission event
 */
const handleOnCreateQuestion = (event: Event) => {
  event.preventDefault();
  props.createQuestion(createQuestionDto.value);
  createQuestionDto.value = {
    content: '',
    filter: QuestionFilter.MIXED,
    type: props.type
  };
};
</script>

<template>
  <form
    class="card lg:w-8/12 w-10/12 flex flex-row bg-base-100 border-2 mt-4 p-4"
    @submit="handleOnCreateQuestion"
  >
    <input
      id="create-question-content"
      placeholder="Intitulé de la question"
      type="text"
      class="input input-bordered w-full"
      v-model="createQuestionDto.content"
      :disabled="props.isLoading"
      required
    />
    <select
      id="create-question-filter"
      class="select select-bordered max-w-xs mx-2"
      v-model="createQuestionDto.filter"
      :disabled="props.isLoading"
      required
    >
      <option
        v-for="filter in Object.values(QuestionFilter)"
        v-bind:key="`${filter}`"
        :value="filter"
      >
        {{ QuestionFilterDisplayName[filter] }}
      </option>
    </select>
    <button type="submit" class="btn btn-success" :disabled="props.isLoading">
      Ajouter une question
    </button>
  </form>
</template>
