<script setup lang="ts">
import { faSliders, faVenusMars } from '@fortawesome/free-solid-svg-icons';
import { faCircleQuestion, faMessage } from '@fortawesome/free-regular-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { ref, watch } from 'vue';
import { QuestionType, QuestionTypeDisplayNames } from '@/models/question/questionType.enum';
import { QuestionFilter, QuestionFilterDisplayNames } from '@/models/question/questionFilter.enum';
import {
  ClosedQuestionAnswer,
  ClosedQuestionAnswerDisplayNames,
  type Question
} from '@/models/question/question.model';
import { v4 as uuidv4 } from 'uuid';
import { useAuthStore } from '@/stores/auth.store';
import { Role } from '@/models/auth/role.enum';
import { useQuestionStore } from '@/stores/questions.store';
import QuestionListModal from '@/components/modal/questionModal/QuestionListModal.vue';
import GenericForm from '@/components/modal/genericModal/GenericForm.vue';
import IconLabel from '@/components/iconLabel/IconLabel.vue';
import ClassicSelector from '@/components/classicSelector/ClassicSelector.vue';

const typeValue = ref<QuestionType>(QuestionType.OPENED);
const genderValue = ref<QuestionFilter>(QuestionFilter.FEMALE);
const questionValue = ref<string>('');
const answerValue = ref<string>('');
const isModalOpen = ref<boolean>(false);

const props = defineProps<{
  questionToUpdate?: Question | null;
  questions: Question[];
}>();

const authStore = useAuthStore();
const questionStore = useQuestionStore();

const questionTypeOptions = Object.values(QuestionType).map((value) => ({
  value,
  label: QuestionTypeDisplayNames[value as QuestionType]
}));

const genderOptions = Object.values(QuestionFilter).map((value) => ({
  value,
  label: QuestionFilterDisplayNames[value as QuestionFilter]
}));

const closedQuestionOptions = Object.values(ClosedQuestionAnswer).map((value) => ({
  value,
  label: ClosedQuestionAnswerDisplayNames[value as ClosedQuestionAnswer]
}));

const emits = defineEmits<{
  (e: 'addQuestion', question: Question): void;
  (e: 'addQuestions', question: Question[]): void;
}>();

const fetchExistingQuestions = async () => {
  //TODO
  //1 - Fetch les questions depuis le back
  const teacherId = authStore.getUserRole === Role.TEACHER ? authStore.getUserInfo.id : null;
  const questions: Question[] = await questionStore.fetchExistingQuestions(teacherId);
  for (const question of questions) {
    console.log(question);
  }

  //2 - Les affiches (FRONT A FAIRE)
  switchModalVisibility();
  //3 - Faire la méthode pour ajouter la question à partir de la liste dans la liste de questions du VueJS
};

const submitForm = () => {
  authStore.initialize();

  let teacherId = authStore.getUserRole === Role.TEACHER ? authStore.getUserInfo.id : null;

  emits('addQuestion', {
    id: props.questionToUpdate ? props.questionToUpdate.id : uuidv4(),
    content: questionValue.value,
    answer: answerValue.value,
    type: typeValue.value,
    teacherId: teacherId,
    filter: genderValue.value,
    isMutual: false
  });

  questionValue.value = '';
  answerValue.value = '';
  typeValue.value = QuestionType.OPENED;
  genderValue.value = QuestionFilter.MIXED;
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
    <div class="flex gap-2 flex-wrap">
      <div>
        <IconLabel for="type" :icon="faSliders" text="Type*" />
        <ClassicSelector id="type" :options="questionTypeOptions" v-model="typeValue" />
      </div>
      <div>
        <IconLabel for="gender" :icon="faVenusMars" text="Genre*" />
        <ClassicSelector id="gender" :options="genderOptions" v-model="genderValue" />
      </div>
    </div>
    <div class="form-group">
      <IconLabel for="question" :icon="faCircleQuestion" text="Question*" />
      <input
        type="text"
        id="question"
        class="text-input"
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

.select-input:focus {
  outline: none;
  border: 2px solid #d6d6d6;
}
</style>
