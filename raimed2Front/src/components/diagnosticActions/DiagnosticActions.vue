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
import ActionButton from '@/components/actionButton/ActionButton.vue';
import { Color } from '@/models/new-patient/color.model';

defineProps<{
  handleOnClickActionButton: (typeAction: TypeAction) => void;
}>();

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
        />

        <!-- Closed question action button -->
        <ActionButton
          :color="Color.Blue"
          :icon="faQuestion"
          :label="getTypeActionDisplayName(TypeAction.CLOSED_QUESTION)"
          :onClick="() => handleOnClickActionButton(TypeAction.CLOSED_QUESTION)"
        />

        <!-- Opened question action button -->
        <ActionButton
          :color="Color.Blue"
          :icon="faPersonCircleQuestion"
          :label="getTypeActionDisplayName(TypeAction.OPENED_QUESTION)"
          :onClick="() => handleOnClickActionButton(TypeAction.OPENED_QUESTION)"
        />

        <!-- Precision action button -->
        <ActionButton
          :color="Color.Blue"
          :icon="faSquarePlus"
          :label="getTypeActionDisplayName(TypeAction.PRECISION)"
          :onClick="() => handleOnClickActionButton(TypeAction.PRECISION)"
        />
      </div>
      <div class="w-1/3 flex flex-col">
        <!-- Inspection action button -->
        <ActionButton
          :color="Color.Orange"
          :icon="faMagnifyingGlass"
          :label="getTypeActionDisplayName(TypeAction.INSPECTION)"
          :onClick="() => handleOnClickActionButton(TypeAction.INSPECTION)"
        />

        <!-- Palpation action button -->
        <ActionButton
          :color="Color.Orange"
          :icon="faHandHoldingMedical"
          :label="getTypeActionDisplayName(TypeAction.PALPATION)"
          :onClick="() => handleOnClickActionButton(TypeAction.PALPATION)"
        />

        <!-- Percussion action button -->
        <ActionButton
          :color="Color.Orange"
          :icon="faGavel"
          :label="getTypeActionDisplayName(TypeAction.PERCUSSION)"
          :onClick="() => handleOnClickActionButton(TypeAction.PERCUSSION)"
        />

        <!-- Auscultation action button -->
        <ActionButton
          :color="Color.Orange"
          :icon="faStethoscope"
          :label="getTypeActionDisplayName(TypeAction.AUSCULTATION)"
          :onClick="() => handleOnClickActionButton(TypeAction.AUSCULTATION)"
        />
      </div>
      <div class="w-1/3 flex flex-col">
        <!-- Biology & Microbiology prescription action button -->
        <ActionButton
          :color="Color.Purple"
          :icon="faFileMedical"
          :label="getTypeActionDisplayName(TypeAction.BIOLOGY)"
          :onClick="() => handleOnClickActionButton(TypeAction.BIOLOGY)"
        />

        <!-- Imaging prescription action button -->
        <ActionButton
          :color="Color.Purple"
          :icon="faPersonRays"
          :label="getTypeActionDisplayName(TypeAction.IMAGERY)"
          :onClick="() => handleOnClickActionButton(TypeAction.IMAGERY)"
        />

        <!-- Biopsies prescription action button -->
        <ActionButton
          :color="Color.Purple"
          :icon="faSyringe"
          :label="getTypeActionDisplayName(TypeAction.BIOPSY)"
          :onClick="() => handleOnClickActionButton(TypeAction.BIOPSY)"
        />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
