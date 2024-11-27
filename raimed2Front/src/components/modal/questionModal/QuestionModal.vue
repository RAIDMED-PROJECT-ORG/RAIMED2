<script setup lang="ts">
import { Color } from '@/models/new-patient/color.model';
import GenericModal from '@/components/modal/genericModal/GenericModal.vue';
import type { Question } from '@/models/question/question.model';
import { faCirclePlus, faSearch, faSliders, faVenusMars } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import QuestionRow from '@/components/modal/questionModal/QuestionRow.vue';
import { ref } from 'vue';
import { faCircleQuestion, faMessage } from '@fortawesome/free-regular-svg-icons';
import ActionButton from '@/components/actionButton/ActionButton.vue';

defineProps<{
  onValidation: (data: Question[]) => void;
  onBack: () => void;
}>();

const questions = ref<{
  question: string;
  answer: string;
}[]>([
  {
    question: 'Quels sont vos antécédents familiaux ?',
    answer: 'De multiples cancers',
  },
  {
    question: 'Avez-vous des allergies connues ?',
    answer: 'Pollen et poussière',
  },
  {
    question: 'Suivez-vous un traitement médicamenteux ?',
    answer: 'Oui, traitement pour l’hypertension',
  },
]);
</script>

<template>
  <GenericModal
    title="Questions"
    validationLabel="Valider"
    :onValidation="() => {}"
    :headerColor="Color.Blue"
    :onBack="onBack"
  >
    <div class="max-h-[60vh] flex py-5 px-10">
      <div class="flex flex-col border-r border-1 pr-5">
        <h3 class="self-center text-black font-bold text-xl mb-3">Liste des questions</h3>
        <div class="pr-5 overflow-scroll">
          <div class="flex justify-between border-b border-1 border-[#9CA3AF] text-black font-bold">
            <h4>Questions</h4>
            <h4>Actions</h4>
          </div>
          <QuestionRow
            v-for="(question, index) in questions"
            :key="index"
            :question="question.question"
            :answer="question.answer"
          />
        </div>
      </div>
      <div class="flex flex-col pl-10 overflow-scroll">
        <h3 class="self-center text-black font-bold text-xl mb-3">Ajouter une question</h3>
        <div class="flex flex-col gap-3">
          <div class="flex justify-between">
            <div class="w-[45%]">
              <label for="type" class="font-bold">
                <FontAwesomeIcon :icon="faSliders" class="w-3 h-3 rotate-90" />
                Type
              </label>
              <select id="type" class="w-full border border-[#D6D6D6] rounded-[8px] p-2" aria-label="Type de question">
                <option value="1">Ouverte</option>
                <option value="2">Fermée</option>
              </select>
            </div>
            <div class="w-[45%]">
              <label for="genre" class="font-bold">
                <FontAwesomeIcon :icon="faVenusMars" class="w-3 h-3" />
                Genre
              </label>
              <select id="genre" class="w-full border border-[#D6D6D6] rounded-[8px] p-2" aria-label="Genre concerné">
                <option value="1">Homme</option>
                <option value="2">Femme</option>
                <option value="3">Autre</option>
              </select>
            </div>
          </div>

          <div>
            <label for="question" class="font-bold">
              <FontAwesomeIcon :icon="faCircleQuestion" />
              Question
            </label>
            <input
              type="text"
              id="question"
              class="w-full border border-[#D6D6D6] rounded-[8px] p-2"
              placeholder="Insérer la question à ajouter..."
            />
          </div>

          <div>
            <label for="answer" class="font-bold">
              <FontAwesomeIcon :icon="faMessage" />
              Réponse
            </label>
            <input
              type="text"
              id="answer"
              class="w-full border border-[#D6D6D6] rounded-[8px] p-2"
              placeholder="Insérer la réponse..."
            />
          </div>

          <ActionButton
            label="Créer une question"
            :icon="faCirclePlus"
            :color="Color.Grey"
            class="w-4/5 self-center drop-shadow-sm"
          />
        </div>

        <div class="flex items-center my-4">
          <div class="flex-grow border-t border-light-grey-border"></div>
          <span class="mx-2 text-light-grey-border">ou</span>
          <div class="flex-grow border-t border-light-grey-border"></div>
        </div>

        <div class="flex flex-col items-center">
          <h3 class="text-black font-bold text-xl mb-3">Importer une question</h3>
          <ActionButton
            label="Parcourir les questions existantes"
            :icon="faSearch"
            :color="Color.Grey"
            class="w-11/12 self-center drop-shadow-sm"
          />
        </div>
      </div>
    </div>
  </GenericModal>
</template>

<style scoped>
</style>
