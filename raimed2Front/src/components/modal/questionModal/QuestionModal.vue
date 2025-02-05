<script setup lang="ts">
import { Color } from '@/models/new-patient/color.model';
import GenericModal from '@/components/modal/genericModal/GenericModal.vue';
import QuestionListe from '@/components/modal/questionModal/QuestionListe.vue';
import QuestionForm from '@/components/modal/questionModal/QuestionForm.vue';
import { ref, watch } from 'vue';
import type { Question } from '@/models/question/question.model';
import {useQuestionStore} from '@/stores/questions.store';

const props = defineProps<{
  questions: Question[];
  onValidation: (data: Question[]) => void;
  onBack: () => void;
}>();

const questions = ref<Question[]>(props.questions);

const questionsStore = useQuestionStore();
const questionToUpdate = ref<Question | null>(null);

const deleteQuestion = (id: string) => {
  questions.value = questions.value.filter((question) => question.id !== id);
};

const updateQuestion = (id: string) => {
  questionToUpdate.value = questions.value.find((question) => question.id === id) ?? null;
};

const insertOrUpdateQuestion = (question: Question) => {
  const index = questions.value.findIndex((q) => q.id === question.id);
  if (index === -1) {
    questions.value.push(question);
  } else {
    questions.value[index] = question;
    questionToUpdate.value = null;
  }
};

const insertQuestions = (newQuestions: Question[]) => {
  questions.value.push(...newQuestions);
}

const handleValidation = () => {
  props.onValidation(questions.value);
};
</script>

<template>
  <GenericModal
    title="Questions"
    validationLabel="Valider"
    :onValidation="handleValidation"
    :headerColor="Color.Blue"
    :onBack="onBack"
    :confirmGoBack="confirmGoBack"
  >
    <div class="w-[70vw] h-[60vh] flex px-10">
      <QuestionListe
        :questions="questions"
        @delete-question="deleteQuestion"
        @update-question="updateQuestion"
      />
      <QuestionForm
        @add-question="insertOrUpdateQuestion"
        @add-questions="insertQuestions"
        :question-to-update="questionToUpdate"
        :questions="questions"
      />
    </div>
  </GenericModal>
</template>

<style scoped></style>
