<script setup lang="ts">
import { faCirclePlus, faSearch, faSliders, faVenusMars } from '@fortawesome/free-solid-svg-icons';
import { faCircleQuestion, faMessage } from '@fortawesome/free-regular-svg-icons';
import { Color } from '@/models/new-patient/color.model';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import ActionButton from '@/components/actionButton/ActionButton.vue';
import { ref, watch } from 'vue';
import { getQuestionTypeDisplayName, QuestionType } from '@/models/question/questionType.enum';
import {
  getQuestionFilterDisplayName,
  QuestionFilter
} from '@/models/question/questionFilter.enum';
import type { Question } from '@/models/question/question.model';
import { v4 as uuidv4 } from 'uuid';
import { useAuthStore } from '@/stores/auth.store';
import { Role } from '@/models/auth/role.enum';
import {useQuestionStore} from '@/stores/questions.store';
import QuestionListModal from '@/components/modal/questionModal/QuestionListModal.vue';

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

    <div class="flex flex-col items-center">
      <h3 class="text-black font-bold text-xl mb-3">Importer une question</h3>
      <ActionButton
        label="Parcourir les questions existantes"
        :icon="faSearch"
        :color="Color.Grey"
        @click="fetchExistingQuestions"
        class="w-11/12 self-center drop-shadow-sm"
        :on-click="switchModalVisibility"
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
