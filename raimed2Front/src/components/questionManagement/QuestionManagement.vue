<script setup lang="ts">
import { ref } from 'vue';
import type { Question } from '@/models/question/question.model';
import type { QuestionType } from '@/models/question/questionType.enum';
import {
  QuestionFilter, QuestionFilterDisplayNames
} from '@/models/question/questionFilter.enum';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faCheck, faInfoCircle, faPen, faTrashCan } from '@fortawesome/free-solid-svg-icons';
import ErrorAlert from '@/components/alert/ErrorAlert.vue';

// Define props to use in the component
const props = defineProps<{
  type: QuestionType;
  isError: boolean;
  isLoading: boolean;
  questionList: Question[];
  updateQuestion: (question: Question) => void;
  deleteQuestion: (questionId: string) => void;
}>();

// Manage the current updated question state
const updatingQuestion = ref<Question | null>(null);

/**
 * Handle click on the update button by setting the updatingQuestion state to
 * display the update form instead of the question details
 * @param question The question to update
 */
const handleOnUpdatingQuestion = (question: Question) => {
  updatingQuestion.value = { ...question };
};

/**
 * Handle question update by calling the parent component's updateQuestion function
 */
const handleOnUpdateQuestion = () => {
  if (updatingQuestion.value) {
    props.updateQuestion(updatingQuestion.value);
    updatingQuestion.value = null;
  }
};
</script>

<template>
  <div class="card lg:w-8/12 w-10/12 max-h-[70dvh] bg-base-100 shadow-md overflow-auto">
    <table class="table">
      <!-- head -->
      <thead>
        <tr class="z-0 text-sm">
          <th class="font-bold"></th>
          <th class="font-bold">Question</th>
          <th class="font-bold">Filtre</th>
          <th class="font-bold text-center">Actions</th>
        </tr>
      </thead>
      <!-- body -->
      <tbody>
        <!-- Is fetching questions -->
        <tr v-if="isLoading">
          <td colspan="4" class="text-center align-middle">
            <span class="loading loading-dots loading-sm"></span>
          </td>
        </tr>
        <!-- Encountered an error fetching questions -->
        <tr v-else-if="isError">
          <td colspan="4" class="text-center align-middle">
            <ErrorAlert message="Une erreur est survenue lors du chargement des questions" />
          </td>
        </tr>
        <!-- Successfully fetched questions but no questions found -->
        <tr v-else-if="!questionList.length">
          <td colspan="4" class="text-center align-middle">
            <div role="alert" class="alert">
              <FontAwesomeIcon :icon="faInfoCircle" class="w-4 h-4" />
              <span>Aucunes questions n'a été trouvée</span>
            </div>
          </td>
        </tr>
        <!-- Query successfully fetched questions -->
        <tr v-else v-for="question in questionList" v-bind:key="question.id">
          <td>{{ question.id }}</td>
          <td>
            <template v-if="question.id === updatingQuestion?.id">
              <input
                id="update-question-content"
                type="text"
                class="input input-bordered w-full"
                v-model="updatingQuestion.content"
                required
              />
            </template>
            <template v-else>{{ question.content }}</template>
          </td>
          <td>
            <template v-if="question.id === updatingQuestion?.id">
              <select
                id="update-question-filter"
                class="select select-bordered w-full max-w-xs mx-2"
                v-model="updatingQuestion.filter"
                required
              >
                <option
                  v-for="filter in Object.values(QuestionFilter)"
                  v-bind:key="`${filter}`"
                  :value="filter"
                >
                  {{ QuestionFilterDisplayNames[filter] }}
                </option>
              </select>
            </template>
            <template v-else>{{ QuestionFilterDisplayNames[question.filter] }}</template>
          </td>
          <td class="text-center">
            <button
              v-if="question.id === updatingQuestion?.id"
              class="btn btn-circle btn-success no-animation mx-1"
              @click="handleOnUpdateQuestion"
            >
              <FontAwesomeIcon :icon="faCheck" class="w-4 h-4" />
            </button>
            <button
              v-else
              class="btn btn-circle no-animation mx-1"
              @click="() => handleOnUpdatingQuestion(question)"
            >
              <FontAwesomeIcon :icon="faPen" class="w-4 h-4" />
            </button>
            <button
              class="btn btn-circle no-animation mx-1"
              @click="() => deleteQuestion(question.id)"
            >
              <FontAwesomeIcon :icon="faTrashCan" class="w-4 h-4" />
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
