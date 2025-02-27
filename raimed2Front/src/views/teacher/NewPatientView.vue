<script setup lang="ts">
import { computed, onMounted, onUnmounted, ref, watch } from 'vue';
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
import PrescriptionModal from '@/components/modal/prescriptionModal/PrescriptionModal.vue';
import { usePatientStore } from '@/stores/patient.store';
import ListenModal from '@/components/modal/listenModal/ListenModal.vue';
import type { Listen } from '@/models/listen/listen.model';
import ProgressBar from '@/components/progressBar/ProgressBar.vue';
import GenericModal from '@/components/modal/genericModal/GenericModal.vue';
import CategoryButton from '@/components/categoryButton/CategoryButton.vue';
import { Gender } from '@/models/virtual-patient/gender.enum';
import type { Prescription } from '@/models/prescription/prescription.model';
import { PrescriptionType } from '@/models/prescription/prescriptionType.enum';
import PrecisionModal from '@/components/modal/precisionModal/PrecisionModal.vue';
import type { Precision } from '@/models/question/precision.model';

const STORAGE_KEY = 'newPatientData';
const SESSION_KEY = 'pageActive';

const props = defineProps<{
  patient?: NewPatient;
}>();

const errors = ref<string[]>([]);
const newPatient = ref<NewPatient>(props.patient ?? initializeNewPatient());
const progress = ref<number>(calculateProgress());

const isGoBackModalOpen = ref(false);
const isCharacteristicModalOpen = ref(false);
const isQuestionModalOpen = ref(false);
const isPrecisionModalOpen = ref(false);
const isWarningModalOpen = ref(false);
const isInspectionModalOpen = ref(false);
const isAuscultationModalOpen = ref(false);
const isPalpationModalOpen = ref(false);
const isPercussionModalOpen = ref(false);
const patientStore = usePatientStore();
const isListenModalOpen = ref(false);
const isBiologyModalOpen = ref(false);
const isImageryModalOpen = ref(false);
const isBiopsyModalOpen = ref(false);

const saveToLocalStorage = () => {
  localStorage.setItem(STORAGE_KEY, JSON.stringify(newPatient.value));
};

const allPrimaryElements = computed(() => {
  return getPrimaryElements(newPatient.value);
});

watch(
  newPatient,
  () => {
    saveToLocalStorage();
    progress.value = calculateProgress();
  },
  { deep: true }
);

watch(allPrimaryElements, (newPrimary, oldPrimary) => {
  if (newPrimary.length === 0) {
    newPatient.value.precisions = [];
    return;
  }

  const deleted = oldPrimary.filter((oldElem) => !newPrimary.includes(oldElem));

  const filtered = newPatient.value.precisions.filter(
    (p) => p.primaryElement && !deleted.includes(p.primaryElement)
  );

  if (filtered.length !== newPatient.value.precisions.length) {
    newPatient.value.precisions = filtered;
  }
});

onMounted(() => {
  const savedPatient = localStorage.getItem(STORAGE_KEY);
  if (savedPatient) {
    newPatient.value = JSON.parse(savedPatient);
    progress.value = calculateProgress();
  }
  sessionStorage.setItem(SESSION_KEY, 'true');
});

const handleUnload = () => {
  if (!sessionStorage.getItem(SESSION_KEY)) {
    localStorage.removeItem(STORAGE_KEY);
  }
};

onUnmounted(() => {
  localStorage.removeItem(STORAGE_KEY);
  sessionStorage.removeItem(SESSION_KEY);
});

window.addEventListener('beforeunload', handleUnload);

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

  patientStore.saveNewPatient(newPatient.value).then(() => {
    localStorage.removeItem(STORAGE_KEY);

    router.back();
  });
}

function getPrimaryElements(value: NewPatient) {
  const primaryElements = new Set<string>();
  value.questions.forEach((question) => {
    if (question.primaryElement) {
      primaryElements.add(question.primaryElement);
    }
  });
  value.listen.forEach((listen) => {
    if (listen.primaryElement) {
      primaryElements.add(listen.primaryElement);
    }
  });
  return Array.from(primaryElements);
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
  console.log('Questions results: ', data);
  newPatient.value.questions = data;
  switchQuestionModalVisibility();
}

function onPrecisionValidation(data: Precision[]) {
  newPatient.value.precisions = data;
  switchPrecisionModalVisibility();
}

function switchPrecisionModalVisibility() {
  isPrecisionModalOpen.value = !isPrecisionModalOpen.value;
}

function switchQuestionModalVisibility() {
  isQuestionModalOpen.value = !isQuestionModalOpen.value;
}

function switchGoBackModalVisibility() {
  isGoBackModalOpen.value = !isGoBackModalOpen.value;
}

function onListenValidation(data: Listen[]) {
  isListenModalOpen.value = false;
  newPatient.value.listen = data;
}

function switchListenModalVisibility() {
  isListenModalOpen.value = !isListenModalOpen.value;
}

function onBiologyValidation(data: Prescription[]) {
  newPatient.value.biology = data;
  switchBiologyModalVisibility();
}

function switchBiologyModalVisibility() {
  isBiologyModalOpen.value = !isBiologyModalOpen.value;
}

function onImageryValidation(data: Prescription[]) {
  newPatient.value.imagery = data;
  switchImageryModalVisibility();
}

function switchImageryModalVisibility() {
  isImageryModalOpen.value = !isImageryModalOpen.value;
}

function onBiopsyValidation(data: Prescription[]) {
  newPatient.value.biopsy = data;
  switchBiopsyModalVisibility();
}

function switchBiopsyModalVisibility() {
  isBiopsyModalOpen.value = !isBiopsyModalOpen.value;
}

function calculateProgress() {
  const total = 10;
  let progressCount = 0;
  if (newPatient.value.characteristic) {
    progressCount++;
  }
  if (newPatient.value.questions.length) {
    progressCount++;
  }
  if (newPatient.value.precisions.length) {
    progressCount++;
  }
  if (newPatient.value.inspection.length) {
    progressCount++;
  }
  if (newPatient.value.palpation.length) {
    progressCount++;
  }
  if (newPatient.value.percussion.length) {
    progressCount++;
  }
  if (newPatient.value.auscultation.length) {
    progressCount++;
  }
  if (newPatient.value.listen.length) {
    progressCount++;
  }
  if (newPatient.value.biology.length) {
    progressCount++;
  }
  if (newPatient.value.imagery.length) {
    progressCount++;
  }
  if (newPatient.value.biopsy.length) {
    progressCount++;
  }
  return (progressCount / total) * 100;
}

function handleOnBack() {
  if (progress.value === 0) {
    handleConfirmGoBack();
    return;
  }
  isGoBackModalOpen.value = true;
}

function handleConfirmGoBack() {
  localStorage.removeItem(STORAGE_KEY);
  router.back();
}

const getPageName = computed(() => {
  if (props.patient) {
    if (props.patient.hasDiagnostic) {
      return 'Visualisation d\'un patient';
    }

    return 'Mise à jour d\'un patient';
  }
  return 'Nouveau patient';
});

</script>

<template>
  <AuthenticatedPageLayout>
    <GenericModal
      v-if="isGoBackModalOpen"
      title="Annuler la création du patient"
      :headerColor="Color.Grey"
      validationLabel="Confirmer"
      :onValidation="handleConfirmGoBack"
      :onBack="switchGoBackModalVisibility"
    >
      <div class="text-center ml-9 mr-9">
        <p>Êtes-vous sûr de vouloir annuler la création du patient ?</p>
        <p>Vous perdrez toutes vos modifications.</p>
      </div>
    </GenericModal>
    <WarningModal
      v-if="isWarningModalOpen"
      :onBack="switchWarningModalVisibility"
      :errors="errors"
    />
    <CharacteristicModal
      v-if="isCharacteristicModalOpen"
      :onValidation="onCharacteristicValidation"
      :onBack="switchCharacteristicModalVisibility"
      :currentCharacteristics="newPatient.characteristic"
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
      :patientGender="Gender.MALE"
    />
    <ListenModal
      v-if="isListenModalOpen"
      :listens="newPatient.listen ?? []"
      :onValidation="onListenValidation"
      :onBack="switchListenModalVisibility"
    />
    <PrecisionModal
      v-if="isPrecisionModalOpen"
      :precisions="newPatient.precisions ?? []"
      :primaryElements="allPrimaryElements"
      :onValidation="onPrecisionValidation"
      :onBack="switchPrecisionModalVisibility"
    />
    <PrescriptionModal
      v-if="isBiologyModalOpen"
      :prescriptionType="PrescriptionType.BIOLOGY"
      :currentPrescriptions="newPatient.biology ?? []"
      :onValidation="onBiologyValidation"
      :onBack="switchBiologyModalVisibility"
    />
    <PrescriptionModal
      v-if="isImageryModalOpen"
      :prescriptionType="PrescriptionType.IMAGERY"
      :currentPrescriptions="newPatient.imagery ?? []"
      :onValidation="onImageryValidation"
      :onBack="switchImageryModalVisibility"
    />
    <PrescriptionModal
      v-if="isBiopsyModalOpen"
      :prescriptionType="PrescriptionType.BIOPSY"
      :currentPrescriptions="newPatient.biopsy ?? []"
      :onValidation="onBiopsyValidation"
      :onBack="switchBiopsyModalVisibility"
    />
    <div class="w-full h-full flex flex-col justify-center items-center">
      <h1 class="text-3xl text-primary font-bold">
        {{ getPageName }}
      </h1>
      <p class="text-center pt-3 w-1/2">
        Pour créer un nouveau cas de patient, cliquer sur chacune des catégories et remplir les
        champs demandés. Ensuite, valider la création du patient en cliquant sur le bouton "Créer le
        patient". Il sera ensuite visible dans votre liste des patients.
      </p>
      <h2 class="mt-2 text-xl font-bold">Progression</h2>
      <ProgressBar :percentage="progress" />
      <div class="flex gap-8 my-8">
        <div class="flex flex-col w-1/3 gap-1">
          <CategoryButton
            label="Caractéristiques du patient"
            :color="Color.Red"
            :icon="faPerson"
            :completed="newPatient.characteristic !== null"
            :onClick="switchCharacteristicModalVisibility"
          />
          <CategoryButton
            label="Écouter"
            :color="Color.Blue"
            :icon="faEarListen"
            :completed="newPatient.listen.length > 0"
            :onClick="switchListenModalVisibility"
          />
          <CategoryButton
            label="Question"
            :color="Color.Blue"
            :icon="faPersonCircleQuestion"
            :onClick="switchQuestionModalVisibility"
            :completed="newPatient.questions.length > 0"
          />
          <CategoryButton
            :label="getTypeActionDisplayName(TypeAction.PRECISION)"
            :color="Color.Blue"
            :icon="faSquarePlus"
            :onClick="switchPrecisionModalVisibility"
            :completed="newPatient.precisions.length > 0"
            :disabled="allPrimaryElements.length === 0"
          />
        </div>
        <div class="flex flex-col w-1/3 gap-1">
          <CategoryButton
            :label="getTypeActionDisplayName(TypeAction.INSPECTION)"
            :color="Color.Orange"
            :icon="faMagnifyingGlass"
            :onClick="switchInspectionModalVisibility"
            :completed="newPatient.inspection.length > 0"
          />
          <CategoryButton
            :label="getTypeActionDisplayName(TypeAction.PALPATION)"
            :color="Color.Orange"
            :icon="faHandHoldingMedical"
            :onClick="switchPalpationModalVisibility"
            :completed="newPatient.palpation.length > 0"
          />
          <CategoryButton
            :label="getTypeActionDisplayName(TypeAction.PERCUSSION)"
            :color="Color.Orange"
            :icon="faGavel"
            :onClick="switchPercussionModalVisibility"
            :completed="newPatient.percussion.length > 0"
          />
          <CategoryButton
            :label="getTypeActionDisplayName(TypeAction.AUSCULTATION)"
            :color="Color.Orange"
            :icon="faStethoscope"
            :onClick="switchAuscultationModalVisibility"
            :completed="newPatient.auscultation.length > 0"
          />
        </div>
        <div class="flex flex-col w-1/3 gap-1">
          <CategoryButton
            :label="getTypeActionDisplayName(TypeAction.BIOLOGY)"
            :color="Color.Purple"
            :icon="faFileMedical"
            :on-click="switchBiologyModalVisibility"
            :completed="newPatient.biology.length > 0"
          />
          <CategoryButton
            :label="getTypeActionDisplayName(TypeAction.IMAGERY)"
            :color="Color.Purple"
            :icon="faPersonRays"
            :on-click="switchImageryModalVisibility"
            :completed="newPatient.imagery.length > 0"
          />
          <CategoryButton
            :label="getTypeActionDisplayName(TypeAction.BIOPSY)"
            :color="Color.Purple"
            :icon="faSyringe"
            :on-click="switchBiopsyModalVisibility"
            :completed="newPatient.biopsy.length > 0"
          />
        </div>
      </div>
      <p>* Champs requis</p>
      <div>
        <ActionButton class="mt-8" :onClick="handleOnBack" label="Annuler" :color="Color.Grey" />
        <ActionButton
          v-if="patient && !patient.hasDiagnostic"
          :disabled="newPatient.characteristic === null"
          class="mt-8"
          :onClick="handleSubmit"
          :label="patient ? 'Mettre à jour le patient' : 'Créer le patient'"
          :color="Color.Green"
        />
      </div>
    </div>
  </AuthenticatedPageLayout>
</template>

<style scoped></style>
