<script setup lang="ts">
import { Color } from '@/models/new-patient/color.model';
import { computed, onMounted, ref } from 'vue';
import GenericModal from '@/components/modal/genericModal/GenericModal.vue';
import { type Question } from '@/models/question/question.model';
import QuestionListModalHeader from '@/components/modal/questionModal/QuestionListModalHeader.vue';
import {
  getQuestionFilterByFirstLetter,
  QuestionFilter
} from '@/models/question/questionFilter.enum';
import QuestionRow from '@/components/modal/questionModal/QuestionRow.vue';
import { Role } from '@/models/auth/role.enum';
import { useAuthStore } from '@/stores/auth.store';
import { useQuestionStore } from '@/stores/questions.store';

const props = defineProps<{
  selectedQuestions: Question[];
}>();

const emits = defineEmits<{
  (e: 'addQuestions', questions: Question[]): void;
  (e: 'switchModalVisibility', visibility: Boolean): void;
}>();

const allQuestions = ref<Question[]>([]);
const questionsToAdd = ref<Question[]>([]);
const authStore = useAuthStore();
const questionStore = useQuestionStore();

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
  return allQuestions.value.filter((question) => {
    const nameMatch = question.content
      .toLowerCase()
      .includes(filters.value.nameFilter.toLowerCase());
    const genderMatch =
      !filters.value.genderFilter || question.filter === filters.value.genderFilter;
    return (
      nameMatch &&
      genderMatch &&
      !props.selectedQuestions.some((selectedQuestions) => selectedQuestions.id === question.id)
    );
  });
});

const switchIsSelected = (question: Question, isSelected: boolean) => {
  if (isSelected) {
    questionsToAdd.value.push(question);
  } else {
    questionsToAdd.value = questionsToAdd.value.filter((q) => q.id !== question.id);
  }
};

const addQuestions = () => {
  emits('addQuestions', questionsToAdd.value);
  emits('switchModalVisibility', false);
};

onMounted(async () => {
  const teacherId = authStore.getUserRole === Role.TEACHER ? authStore.getUserInfo.id : null;
  allQuestions.value = await questionStore.fetchExistingQuestions(
    teacherId,
    null,
    QuestionFilter.MIXED
  );
});
</script>

<template>
  <GenericModal
    title="Liste des questions"
    :validationLabel="validationLabel"
    :hide-validation="questionsToAdd.length === 0"
    :headerColor="Color.Blue"
    :onValidation="addQuestions"
    :onBack="() => emits('switchModalVisibility', false)"
  >
    <div class="w-[70vw] h-[60vh] flex flex-col justify-start px-10">
      <QuestionListModalHeader v-model="filters" />
      <div class="my-5 border-t border-light-grey"></div>
      <div class="pr-5 overflow-scroll">
        <div class="flex justify-between border-b border-1 border-[#9CA3AF] text-black font-bold">
          <h4 class="w-8/12">Questions</h4>
          <h4 class="w-2/12 flex justify-center">Type</h4>
          <h4 class="w-2/12 flex justify-center">Sélectionner</h4>
        </div>
        <QuestionRow
          v-for="question in questionsToDisplay"
          :key="question.id"
          :question="question.content"
          :answer="question.answer ?? ''"
          :is-already-selected="selectedQuestions.includes(question)"
        >
          <div class="w-2/12 flex justify-center">
            {{ getQuestionFilterByFirstLetter(question.filter) }}
          </div>
          <div class="w-2/12 flex justify-center">
            <input
              type="checkbox"
              :checked="questionsToAdd.includes(question)"
              @change="
                (event) => switchIsSelected(question, (event.target as HTMLInputElement).checked)
              "
            />
          </div>
        </QuestionRow>
      </div>
    </div>
  </GenericModal>
</template>

<style scoped></style>
