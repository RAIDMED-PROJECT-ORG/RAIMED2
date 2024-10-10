<script setup lang="ts">
import { TypeAction, getTypeActionDisplayName } from '@/models/virtual-patient/typeAction.enum';
import { useDiagnosticStore } from '@/stores/diagnostic.store';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import {
  faEarListen,
  faFileMedical,
  faGavel,
  faHandHoldingMedical,
  faMagnifyingGlass,
  faPersonCircleQuestion,
  faPersonRays,
  faQuestion,
  faStethoscope,
  faSyringe
} from '@fortawesome/free-solid-svg-icons';
import { faSquarePlus } from '@fortawesome/free-regular-svg-icons';

defineProps<{
  handleOnClickActionButton: (typeAction: TypeAction) => void;
}>();

const diagnosticStore = useDiagnosticStore();

const isActionDisabled = (typeAction: TypeAction): boolean => {
  switch (typeAction) {
    case TypeAction.SPONTANEOUS_PATIENT_SPEECH:
      return !diagnosticStore.isAvailableSpontaneousPatientSpeechAction;
    case TypeAction.SPECIFY_SYMPTOM:
      return true;
    case TypeAction.INSPECTION:
      return true;
    case TypeAction.PALPATATION:
      return true;
    case TypeAction.PERCUSSION:
      return true;
    case TypeAction.AUSCULTATION:
      return true;
    case TypeAction.BIOLOGY_MICROBIOLOGY_PRESCRIPTION:
      return true;
    case TypeAction.IMAGING_PRESCRIPTION:
      return true;
    case TypeAction.BIOPSIES_PRESCRIPTION:
      return true;
    default:
      return false;
  }
};
</script>

<template>
  <div class="w-full p-1 card flex-row bg-base-100 rounded-lg shadow-lg border">
    <div class="w-full h-full flex overflow-auto">
      <div class="w-1/3 flex flex-col">
        <!-- Spontaneous patient speech action button -->
        <button
          class="btn dialog-action m-1 flex-nowrap"
          @click="() => handleOnClickActionButton(TypeAction.SPONTANEOUS_PATIENT_SPEECH)"
          :disabled="isActionDisabled(TypeAction.SPONTANEOUS_PATIENT_SPEECH)"
        >
          <FontAwesomeIcon :icon="faEarListen" class="h-5 w-5" />
          Écouter
        </button>

        <!-- Closed question action button -->
        <button
          class="btn dialog-action m-1 flex-nowrap"
          @click="() => handleOnClickActionButton(TypeAction.CLOSED_QUESTION)"
          :disabled="isActionDisabled(TypeAction.CLOSED_QUESTION)"
        >
          <FontAwesomeIcon :icon="faQuestion" class="h-5 w-5" />
          {{ getTypeActionDisplayName(TypeAction.CLOSED_QUESTION) }}
        </button>

        <!-- Opened question action button -->
        <button
          class="btn dialog-action m-1 flex-nowrap"
          @click="() => handleOnClickActionButton(TypeAction.OPENED_QUESTION)"
          :disabled="isActionDisabled(TypeAction.OPENED_QUESTION)"
        >
          <FontAwesomeIcon :icon="faPersonCircleQuestion" class="h-5 w-5" />
          {{ getTypeActionDisplayName(TypeAction.OPENED_QUESTION) }}
        </button>

        <!-- Specify symptom action button -->
        <button
          class="btn dialog-action m-1 flex-nowrap"
          :disabled="isActionDisabled(TypeAction.SPECIFY_SYMPTOM)"
        >
          <FontAwesomeIcon :icon="faSquarePlus" class="h-5 w-5" />
          {{ getTypeActionDisplayName(TypeAction.SPECIFY_SYMPTOM) }}
        </button>
      </div>
      <div class="w-1/3 flex flex-col">
        <!-- Inspection action button -->
        <button
          class="btn exam-action m-1 flex-nowrap"
          :disabled="isActionDisabled(TypeAction.INSPECTION)"
        >
          <FontAwesomeIcon :icon="faMagnifyingGlass" class="h-5 w-5" />
          {{ getTypeActionDisplayName(TypeAction.INSPECTION) }}
        </button>

        <!-- Palpation action button -->
        <button
          class="btn exam-action m-1 flex-nowrap"
          :disabled="isActionDisabled(TypeAction.PALPATATION)"
        >
          <FontAwesomeIcon :icon="faHandHoldingMedical" class="h-5 w-5" />
          {{ getTypeActionDisplayName(TypeAction.PALPATATION) }}
        </button>

        <!-- Percussion action button -->
        <button
          class="btn exam-action m-1 flex-nowrap"
          :disabled="isActionDisabled(TypeAction.PERCUSSION)"
        >
          <FontAwesomeIcon :icon="faGavel" class="h-5 w-5" />
          {{ getTypeActionDisplayName(TypeAction.PERCUSSION) }}
        </button>

        <!-- Auscultation action button -->
        <button
          class="btn exam-action m-1 flex-nowrap"
          :disabled="isActionDisabled(TypeAction.AUSCULTATION)"
        >
          <FontAwesomeIcon :icon="faStethoscope" class="h-5 w-5" />
          {{ getTypeActionDisplayName(TypeAction.AUSCULTATION) }}
        </button>
      </div>
      <div class="w-1/3 flex flex-col">
        <!-- Biology & Microbiology prescription action button -->
        <button
          class="btn prescription-action m-1 flex-nowrap"
          :disabled="isActionDisabled(TypeAction.BIOLOGY_MICROBIOLOGY_PRESCRIPTION)"
        >
          <FontAwesomeIcon :icon="faFileMedical" class="h-5 w-5" />
          {{ getTypeActionDisplayName(TypeAction.BIOLOGY_MICROBIOLOGY_PRESCRIPTION) }}
        </button>

        <!-- Imaging prescription action button -->
        <button
          class="btn prescription-action m-1 flex-nowrap"
          :disabled="isActionDisabled(TypeAction.IMAGING_PRESCRIPTION)"
        >
          <FontAwesomeIcon :icon="faPersonRays" class="h-5 w-5" />
          {{ getTypeActionDisplayName(TypeAction.IMAGING_PRESCRIPTION) }}
        </button>

        <!-- Biopsies prescription action button -->
        <button
          class="btn prescription-action m-1 flex-nowrap"
          :disabled="isActionDisabled(TypeAction.BIOPSIES_PRESCRIPTION)"
        >
          <FontAwesomeIcon :icon="faSyringe" class="h-5 w-5" />
          {{ getTypeActionDisplayName(TypeAction.BIOPSIES_PRESCRIPTION) }}
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.dialog-action {
  background-color: #9ccded;
}
.dialog-action:disabled {
  background-color: #b7d8ee;
}
.dialog-action:hover {
  background-color: #60aee2;
}
.exam-action {
  background-color: #f1bf6d;
}
.exam-action:disabled {
  background-color: #eed0a1;
}
.exam-action:hover {
  background-color: #eeb354;
}
.prescription-action {
  background-color: #d0b1dd;
}
.prescription-action:disabled {
  background-color: #d5c3dd;
}
.prescription-action:hover {
  background-color: #c694db;
}
</style>
