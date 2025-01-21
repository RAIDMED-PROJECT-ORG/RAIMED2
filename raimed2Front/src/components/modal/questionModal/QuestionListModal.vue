<script setup lang="ts">
import { Color } from '@/models/new-patient/color.model';
import { computed, ref } from 'vue';
import GenericModal from '@/components/modal/genericModal/GenericModal.vue';
import { getFakeQuestions, type Question } from '@/models/question/question.model';
import QuestionListModalHeader from '@/components/modal/questionModal/QuestionListModalHeader.vue';
import QuestionListModalRow from '@/components/modal/questionModal/QuestionListModalRow.vue';
import { QuestionFilter } from '@/models/question/questionFilter.enum';

const props = defineProps<{
  selectedQuestions: Question[];
}>();

const emits = defineEmits<{
  (e: 'addQuestions', questions: Question[]): void;
  (e: 'switchModalVisibility', visibility: Boolean): void;
}>();

const allQuestions = ref<Question[]>(getFakeQuestions());
const questionsToAdd = ref<Question[]>([]);

const filters = ref({
  nameFilter: '',
  genderFilter: null as QuestionFilter | null
});

const validationLabel = computed(() => {
  const count = questionsToAdd.value.length;
  if (count === 0) {
    return 'Fermer';
  }
  return `Ajouter ${count} question${count > 1 ? 's' : ''}`;
});

const questionsToDisplay = computed(() => {
  console.log(props.selectedQuestions)
  return allQuestions.value.filter((question) => {
    const nameMatch = question.content
      .toLowerCase()
      .includes(filters.value.nameFilter.toLowerCase());
    const genderMatch =
      !filters.value.genderFilter || question.filter === filters.value.genderFilter;
    return nameMatch && genderMatch && !props.selectedQuestions.some(selectedQuestions => selectedQuestions.id === question.id);
  });
});

const addQuestion = (question: Question) => {
  questionsToAdd.value.push(question);
};

const removeQuestion = (question: Question) => {
  questionsToAdd.value = questionsToAdd.value.filter((q) => q.id !== question.id);
};

const addQuestions = () => {
  emits('addQuestions', questionsToAdd.value);
  emits('switchModalVisibility', false);
};
</script>

<template>
  <GenericModal
    title="Liste des questions"
    :validationLabel="validationLabel"
    :headerColor="Color.Blue"
    :onValidation="addQuestions"
    :onBack="() => emits('switchModalVisibility', false)"
  >
    <div class="max-h-[60vh] flex flex-col py-5 px-10">
      <QuestionListModalHeader v-model="filters" />
      <div class="my-5 flex-grow border-t border-light-grey"></div>
      <div class="pr-5 overflow-scroll">
        <div class="flex justify-between border-b border-1 border-[#9CA3AF] text-black font-bold">
          <h4 class="w-8/12">Questions</h4>
          <h4 class="w-2/12 flex justify-center">Type</h4>
          <h4 class="w-2/12 flex justify-center">Sélectionner</h4>
        </div>
        <QuestionListModalRow
          v-for="question in questionsToDisplay"
          :key="question.id"
          :question="question"
          :is-already-selected="selectedQuestions.includes(question)"
          @add-question="addQuestion(question)"
          @remove-question="removeQuestion(question)"
        />
      </div>
    </div>
  </GenericModal>
</template>

<style scoped></style>
