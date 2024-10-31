<script setup lang="ts">
import { TypeAction, getTypeActionDisplayName } from '@/models/virtual-patient/typeAction.enum';
import { useDiagnosticStore } from '@/stores/diagnostic.store';
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
import ActionButton, { Color } from '@/components/actionButton/ActionButton.vue';

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
        <ActionButton
          :color="Color.Blue"
          :icon="faEarListen"
          label="Écouter"
          :onClick="() => handleOnClickActionButton(TypeAction.SPONTANEOUS_PATIENT_SPEECH)"
          :disabled="isActionDisabled(TypeAction.SPONTANEOUS_PATIENT_SPEECH)"
        />

        <!-- Closed question action button -->
        <ActionButton
          :color="Color.Blue"
          :icon="faQuestion"
          :label="getTypeActionDisplayName(TypeAction.CLOSED_QUESTION)"
          :onClick="() => handleOnClickActionButton(TypeAction.CLOSED_QUESTION)"
          :disabled="isActionDisabled(TypeAction.CLOSED_QUESTION)"
        />

        <!-- Opened question action button -->
        <ActionButton
          :color="Color.Blue"
          :icon="faPersonCircleQuestion"
          :label="getTypeActionDisplayName(TypeAction.OPENED_QUESTION)"
          :onClick="() => handleOnClickActionButton(TypeAction.OPENED_QUESTION)"
          :disabled="isActionDisabled(TypeAction.OPENED_QUESTION)"
        />

        <!-- Specify symptom action button -->
        <ActionButton
          :color="Color.Blue"
          :icon="faSquarePlus"
          :label="getTypeActionDisplayName(TypeAction.SPECIFY_SYMPTOM)"
          :disabled="isActionDisabled(TypeAction.SPECIFY_SYMPTOM)"
        />
      </div>
      <div class="w-1/3 flex flex-col">
        <!-- Inspection action button -->
        <ActionButton
          :color="Color.Orange"
          :icon="faMagnifyingGlass"
          :label="getTypeActionDisplayName(TypeAction.INSPECTION)"
          :disabled="isActionDisabled(TypeAction.INSPECTION)"
        />

        <!-- Palpation action button -->
        <ActionButton
          :color="Color.Orange"
          :icon="faHandHoldingMedical"
          :label="getTypeActionDisplayName(TypeAction.PALPATATION)"
          :disabled="isActionDisabled(TypeAction.PALPATATION)"
        />

        <!-- Percussion action button -->
        <ActionButton
          :color="Color.Orange"
          :icon="faGavel"
          :label="getTypeActionDisplayName(TypeAction.PERCUSSION)"
          :disabled="isActionDisabled(TypeAction.PERCUSSION)"
        />

        <!-- Auscultation action button -->
        <ActionButton
          :color="Color.Orange"
          :icon="faStethoscope"
          :label="getTypeActionDisplayName(TypeAction.AUSCULTATION)"
          :disabled="isActionDisabled(TypeAction.AUSCULTATION)"
        />
      </div>
      <div class="w-1/3 flex flex-col">
        <!-- Biology & Microbiology prescription action button -->
        <ActionButton
          :color="Color.Purple"
          :icon="faFileMedical"
          :label="getTypeActionDisplayName(TypeAction.BIOLOGY_MICROBIOLOGY_PRESCRIPTION)"
          :disabled="isActionDisabled(TypeAction.BIOLOGY_MICROBIOLOGY_PRESCRIPTION)"
        />

        <!-- Imaging prescription action button -->
        <ActionButton
          :color="Color.Purple"
          :icon="faPersonRays"
          :label="getTypeActionDisplayName(TypeAction.IMAGING_PRESCRIPTION)"
          :disabled="isActionDisabled(TypeAction.IMAGING_PRESCRIPTION)"
        />

        <!-- Biopsies prescription action button -->
        <ActionButton
          :color="Color.Purple"
          :icon="faSyringe"
          :label="getTypeActionDisplayName(TypeAction.BIOPSIES_PRESCRIPTION)"
          :disabled="isActionDisabled(TypeAction.BIOPSIES_PRESCRIPTION)"
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
</style>
