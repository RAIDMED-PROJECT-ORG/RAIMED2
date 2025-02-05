<script setup lang="ts">
import { ref } from 'vue';
import AuthenticatedPageLayout from '@/layouts/AuthenticatedViewLayout.vue';
import ActionButton from '@/components/actionButton/ActionButton.vue';
import {
  faEarListen,
  faFileMedical,
  faGavel,
  faHandHoldingMedical,
  faMagnifyingGlass,
  faPerson,
  faPersonCircleQuestion,
  faPersonRays,
  faStethoscope,
  faSyringe
} from '@fortawesome/free-solid-svg-icons';
import { faSquarePlus } from '@fortawesome/free-regular-svg-icons';
import { getTypeActionDisplayName, TypeAction } from '@/models/virtual-patient/typeAction.enum';
import router from '@/router';
import { Color } from '@/models/new-patient/color.model';
import CharacteristicModal from '@/components/modal/characteristicModal/CharacteristicModal.vue';
import { initializeNewPatient, type NewPatient } from '@/models/new-patient/newPatient.model';
import type { Characteristics } from '@/models/new-patient/characteristics.model';
import WarningModal from '@/components/modal/warningModal/WarningModal.vue';
import {
  AuscultationSigns,
  type ExamResults,
  InspectionSigns,
  PalpationSigns,
  PercussionSigns
} from '@/models/diagnostic/exam.model';
import ExamenModal from '@/components/modal/examModal/ExamModal.vue';
import type { Question } from '@/models/question/question.model';
import QuestionModal from '@/components/modal/questionModal/QuestionModal.vue';

const isCharacteristicModalOpen = ref(false);
const isQuestionModalOpen = ref(false);
const isWarningModalOpen = ref(false);
const errors = ref<string[]>([]);
const newPatient = ref<NewPatient>(initializeNewPatient());
const isInspectionModalOpen = ref(false);
const isAuscultationModalOpen = ref(false);
const isPalpationModalOpen = ref(false);
const isPercussionModalOpen = ref(false);

function handleSubmit() {
  errors.value = [];
  if (!newPatient.value.characteristic) {
    errors.value.push('Caractéristiques du patient');
  }

  if (!newPatient.value.questions) {
    errors.value.push('Questions ouvertes ou fermées');
  }

  if (errors.value.length > 0) {
    switchWarningModalVisibility();
    return;
  }

  router.back();
}

function switchWarningModalVisibility() {
  isWarningModalOpen.value = !isWarningModalOpen.value;
}

function onCharacteristicValidation(data: Characteristics) {
  newPatient.value.characteristic = data;
  console.log('Patient :', newPatient);
  switchCharacteristicModalVisibility();
}

function switchCharacteristicModalVisibility() {
  isCharacteristicModalOpen.value = !isCharacteristicModalOpen.value;
}

function onInspectionValidation(data: ExamResults[]) {
  console.log('Inspection results: ', data);
  isInspectionModalOpen.value = false;
  newPatient.value.inspection = data;
}

function switchInspectionModalVisibility() {
  isInspectionModalOpen.value = !isInspectionModalOpen.value;
}

function switchPercussionModalVisibility() {
  isPercussionModalOpen.value = !isPercussionModalOpen.value;
}

function onPercussionValidation(data: ExamResults[]) {
  console.log('Percussion results: ', data);
  isPercussionModalOpen.value = false;
  newPatient.value.percussion = data;
}

function switchPalpationModalVisibility() {
  isPalpationModalOpen.value = !isPalpationModalOpen.value;
}

function onPalpationValidation(data: ExamResults[]) {
  console.log('Palpation results: ', data);
  isPalpationModalOpen.value = false;
  newPatient.value.palpation = data;
}

function switchAuscultationModalVisibility() {
  isAuscultationModalOpen.value = !isAuscultationModalOpen.value;
}

function onAuscultationValidation(data: ExamResults[]) {
  console.log('Auscultation results: ', data);
  isAuscultationModalOpen.value = false;
  newPatient.value.auscultation = data;
}

function onQuestionValidation(data: Question[]) {
  newPatient.value.questions = data;
  switchQuestionModalVisibility();
}

function switchQuestionModalVisibility() {
  isQuestionModalOpen.value = !isQuestionModalOpen.value;
}
</script>

<template>
  <AuthenticatedPageLayout>
    <WarningModal
      v-if="isWarningModalOpen"
      :onBack="switchWarningModalVisibility"
      :errors="errors"
    />
    <CharacteristicModal
      v-if="isCharacteristicModalOpen"
      :onValidation="onCharacteristicValidation"
      :onBack="switchCharacteristicModalVisibility"
    />
    <ExamenModal
      v-if="isInspectionModalOpen"
      :onValidation="onInspectionValidation"
      :onBack="switchInspectionModalVisibility"
      :possibleExams="InspectionSigns"
      :modalTitle="'Inspection'"
      :currentExamResults="newPatient.inspection"
    />
    <ExamenModal
      v-if="isPalpationModalOpen"
      :onValidation="onPalpationValidation"
      :onBack="switchPalpationModalVisibility"
      :possibleExams="PalpationSigns"
      :modalTitle="'Palpation et manoeuvre'"
      :currentExamResults="newPatient.palpation"
    />
    <ExamenModal
      v-if="isPercussionModalOpen"
      :onValidation="onPercussionValidation"
      :onBack="switchPercussionModalVisibility"
      :possibleExams="PercussionSigns"
      :modalTitle="'Percussion'"
      :currentExamResults="newPatient.percussion"
    />
    <ExamenModal
      v-if="isAuscultationModalOpen"
      :onValidation="onAuscultationValidation"
      :onBack="switchAuscultationModalVisibility"
      :possibleExams="AuscultationSigns"
      :modalTitle="'Auscultation'"
      :currentExamResults="newPatient.auscultation"
    />

    <QuestionModal
      v-if="isQuestionModalOpen"
      :questions="newPatient.questions ?? []"
      :onValidation="onQuestionValidation"
      :onBack="switchQuestionModalVisibility"
    />
    <div class="w-full h-full flex flex-col justify-center items-center">
      <h1 class="text-2xl text-primary font-bold">Nouveau patient</h1>
      <p class="text-center pt-3 w-1/2">
        Pour créer un nouveau cas de patient, cliquer sur chacune des catégories et remplir les
        champs demandés. Ensuite, valider la création du patient en cliquant sur le bouton “Créer le
        patient”. Il sera ensuite visible dans votre liste des patients.
      </p>
      <div class="flex gap-8 my-8">
        <div class="flex flex-col w-1/3">
          <ActionButton
            label="Caractéristiques du patient*"
            :color="Color.Red"
            :icon="faPerson"
            :onClick="switchCharacteristicModalVisibility"
          />
          <ActionButton label="Écouter" :color="Color.Blue" :icon="faEarListen" />
          <ActionButton
            label="Question"
            :color="Color.Blue"
            :icon="faPersonCircleQuestion"
            :on-click="switchQuestionModalVisibility"
          />
          <ActionButton
            :label="getTypeActionDisplayName(TypeAction.SPECIFY_SYMPTOM)"
            :color="Color.Blue"
            :icon="faSquarePlus"
          />
        </div>
        <div class="flex flex-col w-1/3">
          <ActionButton
            :label="getTypeActionDisplayName(TypeAction.INSPECTION)"
            :color="Color.Orange"
            :icon="faMagnifyingGlass"
            :onClick="switchInspectionModalVisibility"
          />
          <ActionButton
            :label="getTypeActionDisplayName(TypeAction.PALPATION)"
            :color="Color.Orange"
            :icon="faHandHoldingMedical"
            :onClick="switchPalpationModalVisibility"
          />
          <ActionButton
            :label="getTypeActionDisplayName(TypeAction.PERCUSSION)"
            :color="Color.Orange"
            :icon="faGavel"
            :onClick="switchPercussionModalVisibility"
          />
          <ActionButton
            :label="getTypeActionDisplayName(TypeAction.AUSCULTATION)"
            :color="Color.Orange"
            :icon="faStethoscope"
            :onClick="switchAuscultationModalVisibility"
          />
        </div>
        <div class="flex flex-col w-1/3">
          <ActionButton
            :label="getTypeActionDisplayName(TypeAction.BIOLOGY_MICROBIOLOGY_PRESCRIPTION)"
            :color="Color.Purple"
            :icon="faFileMedical"
          />
          <ActionButton
            :label="getTypeActionDisplayName(TypeAction.IMAGING_PRESCRIPTION)"
            :color="Color.Purple"
            :icon="faPersonRays"
          />
          <ActionButton
            :label="getTypeActionDisplayName(TypeAction.BIOPSIES_PRESCRIPTION)"
            :color="Color.Purple"
            :icon="faSyringe"
          />
        </div>
      </div>
      <p>* Champs requis</p>
      <div>
        <ActionButton
          :disabled="newPatient.characteristic === null"
          class="mt-8"
          :onClick="handleSubmit"
          label="Créer le patient"
          :color="Color.Green"
        />
        <ActionButton
          class="mt-8"
          :onClick="() => router.back()"
          label="Annuler"
          :color="Color.Grey"
        />
      </div>
    </div>
  </AuthenticatedPageLayout>
</template>

<style scoped></style>
