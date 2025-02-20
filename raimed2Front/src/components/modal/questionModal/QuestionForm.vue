<script setup lang="ts">
import { faSliders } from '@fortawesome/free-solid-svg-icons';
import { faBookmark, faCircleQuestion, faMessage } from '@fortawesome/free-regular-svg-icons';
import { computed, ref, watch } from 'vue';
import { QuestionType, QuestionTypeDisplayNames } from '@/models/question/questionType.enum';
import { QuestionFilter } from '@/models/question/questionFilter.enum';
import {
  ClosedQuestionAnswer,
  ClosedQuestionAnswerDisplayNames,
  type Question
} from '@/models/question/question.model';
import { v4 as uuidv4 } from 'uuid';
import { useAuthStore } from '@/stores/auth.store';
import { Role } from '@/models/auth/role.enum';
import QuestionListModal from '@/components/modal/questionModal/QuestionListModal.vue';
import GenericForm from '@/components/modal/genericModal/GenericForm.vue';
import IconLabel from '@/components/iconLabel/IconLabel.vue';
import ClassicSelector from '@/components/classicSelector/ClassicSelector.vue';
import type { Gender } from '@/models/virtual-patient/gender.enum';
import SingleValueSelector from '@/components/singleValueSelector/SingleValueSelector.vue';

const props = defineProps<{
  questionToUpdate?: Question | null;
  questions: Question[];
  patientGender: Gender;
}>();

const singleSelector = ref<InstanceType<typeof SingleValueSelector> | null>(null);
const typeValue = ref<QuestionType>(QuestionType.OPENED);
const genderValue = ref<QuestionFilter>(QuestionFilter.FEMALE);
const questionValue = ref<string>('');
const answerValue = ref<string>('');
const isModalOpen = ref<boolean>(false);
const primaryElementValue = ref<string | undefined>(undefined);

const primaryElements = computed(() =>
  Array.from(new Set(
    props.questions
      .filter((question) => question.primaryElement != null)
      .map((question) => question.primaryElement as string)
  ))
);

const authStore = useAuthStore();

const questionTypeOptions = Object.values(QuestionType).map((value) => ({
  value,
  label: QuestionTypeDisplayNames[value as QuestionType]
}));

const closedQuestionOptions = Object.values(ClosedQuestionAnswer).map((value) => ({
  value,
  label: ClosedQuestionAnswerDisplayNames[value as ClosedQuestionAnswer]
}));

const emits = defineEmits<{
  (e: 'addQuestion', question: Question): void;
  (e: 'addQuestions', question: Question[]): void;
}>();

const submitForm = () => {
  authStore.initialize();

  let teacherId = authStore.getUserRole === Role.TEACHER ? authStore.getUserInfo.id : null;

  emits('addQuestion', {
    id: props.questionToUpdate ? props.questionToUpdate.id : uuidv4(),
    content: questionValue.value,
    answer: answerValue.value,
    primaryElement: primaryElementValue.value,
    type: typeValue.value,
    teacherId: teacherId,
    filter: QuestionFilter[genderValue.value],
    isMutual: false
  });
  questionValue.value = '';
  answerValue.value = '';
  primaryElementValue.value = undefined;
  typeValue.value = QuestionType.OPENED;
  genderValue.value = QuestionFilter.MIXED;
  singleSelector.value?.clearInput();
};

const switchModalVisibility = () => {
  isModalOpen.value = !isModalOpen.value;
};

watch(
  () => props.questionToUpdate,
  (questionToUpdate) => {
    if (questionToUpdate) {
      questionValue.value = questionToUpdate.content;
      answerValue.value = questionToUpdate.answer ?? '';
      typeValue.value = questionToUpdate.type;
      genderValue.value = questionToUpdate.filter;
      primaryElementValue.value = questionToUpdate?.primaryElement || '';
    }
  }
);

watch(typeValue, (newType) => {
  if (newType === QuestionType.CLOSED) {
    answerValue.value = ClosedQuestionAnswer.YES;
  } else {
    answerValue.value = '';
  }
});

function handleSelectPrimaryElement(value: string) {
  primaryElementValue.value = value;
}
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
    <div class="form-group">
      <IconLabel for="type" :icon="faSliders" text="Type*" />
      <ClassicSelector id="type" :options="questionTypeOptions" v-model="typeValue" />
    </div>
    <div class="form-group">
      <IconLabel for="question" :icon="faCircleQuestion" text="Question*" />
      <input
        type="text"
        id="question"
        class="text-input"
        :class="{ 'text-input--filled': questionValue.length > 0 }"
        placeholder="Insérer la question à ajouter..."
        v-model="questionValue"
        aria-label="Texte de la question"
        required
      />
    </div>
    <div class="form-group">
      <IconLabel for="answer" :icon="faMessage" text="Réponse*" />
      <input
        v-if="typeValue === QuestionType.OPENED"
        type="text"
        id="answer"
        class="text-input"
        :class="{ 'text-input--filled': answerValue.length > 0 }"
        placeholder="Insérer la réponse..."
        v-model="answerValue"
        aria-label="Texte de la réponse"
        required
      />
      <ClassicSelector
        v-if="typeValue === QuestionType.CLOSED"
        id="answer"
        :options="closedQuestionOptions"
        v-model="answerValue"
      />
    </div>
    <div class="form-group">
      <IconLabel for="primaryElement" :icon="faBookmark" text="Élément primaire" />
      <SingleValueSelector ref="singleSelector" id="primaryElement" v-model="primaryElementValue" :options="primaryElements" @select="(value: string) => handleSelectPrimaryElement(value)" />
    </div>
    <p class="opacity-50">* champs requis</p>
  </GenericForm>
  <QuestionListModal
    v-if="isModalOpen"
    :selected-questions="questions"
    :patient-gender="patientGender"
    @switch-modal-visibility="switchModalVisibility"
    @add-questions="emits('addQuestions', $event)"
  />
</template>

<style scoped></style>
