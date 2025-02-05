<script setup lang="ts">
import { faCirclePlus, faSearch, faSliders, faVenusMars } from '@fortawesome/free-solid-svg-icons';
import { faCircleQuestion, faMessage } from '@fortawesome/free-regular-svg-icons';
import { Color } from '@/models/new-patient/color.model';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import ActionButton from '@/components/actionButton/ActionButton.vue';
import { ref, watch } from 'vue';
import { QuestionType, QuestionTypeDisplayNames } from '@/models/question/questionType.enum';
import {
  QuestionFilter, QuestionFilterDisplayNames
} from '@/models/question/questionFilter.enum';
import type { Question } from '@/models/question/question.model';
import { v4 as uuidv4 } from 'uuid';
import { useAuthStore } from '@/stores/auth.store';
import { Role } from '@/models/auth/role.enum';
import {useQuestionStore} from '@/stores/questions.store';
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

const questionTypeOptions = Object.values(QuestionType).map(value => ({
  value,
  label: QuestionTypeDisplayNames[value as QuestionType]
}));

const genderOptions = Object.values(QuestionFilter).map(value => ({
  value,
  label: QuestionFilterDisplayNames[value as QuestionFilter]
}));

const emits = defineEmits<{
  (e: 'addQuestion', question: Question): void;
  (e: 'addQuestions', question: Question[]): void;
}>();

const fetchExistingQuestions = async () => {
  //TODO
  //1 - Fetch les questions depuis le back
  const teacherId = (authStore.getUserRole === Role.TEACHER) ? authStore.getUserInfo.id : null;
  const questions : Question[]  = await questionStore.fetchExistingQuestions(teacherId);
  for (const question of questions) {
    console.log(question);
  }

  //2 - Les affiches (FRONT A FAIRE)
  switchModalVisibility();
  //3 - Faire la méthode pour ajouter la question à partir de la liste dans la liste de questions du VueJS
};

const submitForm = (event: SubmitEvent) => {
  event.preventDefault();
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
        <IconLabel for="type" :icon="faSliders" text="Type*" />
        <ClassicSelector
          :options="questionTypeOptions"
          v-model="typeValue"
        />
      </div>

      <div class="w-[45%]">
        <IconLabel for="gender" :icon="faVenusMars" text="Genre*" />
        <ClassicSelector id="gender" :options="genderOptions" v-model="genderValue" />
      </div>
    </div>

    <div class="flex flex-col items-center">
      <h3 class="text-black font-bold text-xl mb-3">Importer une question</h3>
      <ActionButton
        label="Parcourir les questions existantes"
        :icon="faSearch"
        :color="Color.Grey"
        @click="fetchExistingQuestions"
        class="w-11/12 self-center drop-shadow-sm"
      />
    </div>

    <div class="form-group">
      <IconLabel for="answer" :icon="faMessage" text="Réponse*" />
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

.select-input:focus {
  outline: none;
  border: 2px solid #d6d6d6;
}
</style>
