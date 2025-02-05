<script setup lang="ts">
import { faSliders, faVenusMars } from '@fortawesome/free-solid-svg-icons';
import { faCircleQuestion, faMessage } from '@fortawesome/free-regular-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { ref, watch } from 'vue';
import { getQuestionTypeDisplayName, QuestionType } from '@/models/question/questionType.enum';
import {
  getQuestionFilterDisplayName,
  QuestionFilter
} from '@/models/question/questionFilter.enum';
import type { Question } from '@/models/question/question.model';
import { v4 as uuidv4 } from 'uuid';
import QuestionListModal from '@/components/modal/questionModal/QuestionListModal.vue';
import GenericForm from '@/components/modal/genericModal/GenericForm.vue';

const typeValue = ref<QuestionType>(QuestionType.OPENED);
const genderValue = ref<QuestionFilter>(QuestionFilter.FEMALE);
const questionValue = ref<string>('');
const answerValue = ref<string>('');
const isModalOpen = ref<boolean>(false);

const props = defineProps<{
  questionToUpdate?: Question | null;
  questions: Question[];
}>();

const emits = defineEmits<{
  (e: 'addQuestion', question: Question): void;
  (e: 'addQuestions', question: Question[]): void;
}>();

const submitForm = () => {
  emits('addQuestion', {
    id: props.questionToUpdate ? props.questionToUpdate.id : uuidv4(),
    content: questionValue.value,
    answer: answerValue.value,
    type: typeValue.value,
    filter: genderValue.value,
    isMutual: false
  });

  questionValue.value = '';
  answerValue.value = '';
  typeValue.value = QuestionType.OPENED;
  genderValue.value = QuestionFilter.MIXED;
};

watch(
  () => props.questionToUpdate,
  (questionToUpdate) => {
    if (questionToUpdate) {
      questionValue.value = questionToUpdate.content;
      answerValue.value = questionToUpdate.answer ?? '';
      typeValue.value = questionToUpdate.type;
      genderValue.value = questionToUpdate.filter;
    }
  }
);

const switchModalVisibility = () => {
  isModalOpen.value = !isModalOpen.value;
};
</script>

<template>
  <GenericForm
    title="Ajouter une question"
    :button-label="questionToUpdate ? 'Mettre à jour la question' : 'Créer une question'"
    import-title="Importer une question"
    import-button-label="Importer une question"
    @onsubmit="() => submitForm()"
    @open-modal="() => switchModalVisibility()"
  >
    <div class="flex justify-between">
      <div class="w-[45%]">
        <label for="type" class="font-bold">
          <FontAwesomeIcon :icon="faSliders" class="icon-rotate" />
          Type*
        </label>
        <select
          id="type"
          class="w-full border border-[#D6D6D6] rounded-[8px] p-2"
          v-model="typeValue"
          aria-label="Type de question"
          required
        >
          <option :value="QuestionType.OPENED">
            {{ getQuestionTypeDisplayName(QuestionType.OPENED) }}
          </option>
          <option :value="QuestionType.CLOSED">
            {{ getQuestionTypeDisplayName(QuestionType.CLOSED) }}
          </option>
        </select>
      </div>

      <div class="w-[45%]">
        <label for="gender" class="font-bold">
          <FontAwesomeIcon :icon="faVenusMars" class="icon" />
          Genre*
        </label>
        <select
          id="gender"
          class="w-full border border-[#D6D6D6] rounded-[8px] p-2"
          v-model="genderValue"
          aria-label="Genre concerné"
          required
        >
          <option :value="QuestionFilter.MIXED">
            {{ getQuestionFilterDisplayName(QuestionFilter.MIXED) }}
          </option>
          <option :value="QuestionFilter.FEMALE">
            {{ getQuestionFilterDisplayName(QuestionFilter.FEMALE) }}
          </option>
          <option :value="QuestionFilter.MALE">
            {{ getQuestionFilterDisplayName(QuestionFilter.MIXED) }}
          </option>
        </select>
      </div>
    </div>

    <div class="form-group">
      <label for="question" class="font-bold">
        <FontAwesomeIcon :icon="faCircleQuestion" class="icon" />
        Question*
      </label>
      <input
        type="text"
        id="question"
        class="select-input"
        placeholder="Insérer la question à ajouter..."
        v-model="questionValue"
        aria-label="Texte de la question"
        required
      />
    </div>

    <div class="form-group">
      <label for="answer" class="font-bold">
        <FontAwesomeIcon :icon="faMessage" class="icon" />
        Réponse*
      </label>
      <input
        v-if="typeValue === QuestionType.OPENED"
        type="text"
        id="answer"
        class="select-input"
        placeholder="Insérer la réponse..."
        v-model="answerValue"
        aria-label="Texte de la réponse"
        required
      />
      <select
        v-else
        id="answer"
        class="w-full border border-[#D6D6D6] rounded-[8px] p-2"
        v-model="answerValue"
        required
      >
        <option value="Oui">Oui</option>
        <option value="Non">Non</option>
      </select>
    </div>
  </GenericForm>
  <QuestionListModal
    v-if="isModalOpen"
    :selected-questions="questions"
    @switch-modal-visibility="switchModalVisibility"
    @add-questions="emits('addQuestions', $event)"
  />
</template>

<style scoped>
.select-input {
  width: 100%;
  border: 1px solid #d6d6d6;
  border-radius: 8px;
  padding: 0.5rem;
}
</style>
