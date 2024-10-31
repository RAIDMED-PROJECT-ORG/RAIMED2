<script setup lang="ts">
import AuthenticatedPageLayout from '@/layouts/AuthenticatedViewLayout.vue';
import ActionButton, { Color } from '@/components/actionButton/ActionButton.vue';
import {
  faEarListen, faFileMedical, faGavel,
  faHandHoldingMedical,
  faMagnifyingGlass, faPerson,
  faPersonCircleQuestion, faPersonRays, faStethoscope, faSyringe
} from '@fortawesome/free-solid-svg-icons';
import { faSquarePlus } from '@fortawesome/free-regular-svg-icons';
import { getTypeActionDisplayName, TypeAction } from '@/models/virtual-patient/typeAction.enum';
</script>
<template>
  <AuthenticatedPageLayout>
    <div class="w-full h-full flex flex-col justify-center items-center">
      <h1 class="title">Nouveau patient</h1>
      <p class="text">
        Pour créer un nouveau cas de patient, cliquer sur chacune des catégories et remplir les
        champs demandés. Ensuite, valider la création du patient en cliquant sur le bouton “Créer le
        patient”. Il sera ensuite visible dans votre liste des patients.
      </p>
      <div class="diagnostic-actions">
        <div class="column">
          <ActionButton
            label="Écouter"
            :color="Color.Blue"
            :icon="faEarListen"
          />
          <ActionButton
            label="Question"
            :color="Color.Blue"
            :icon="faPersonCircleQuestion"
          />
          <ActionButton
            :label="getTypeActionDisplayName(TypeAction.SPECIFY_SYMPTOM)"
            :color="Color.Blue"
            :icon="faSquarePlus"
          />
        </div>
        <div class="column">
          <ActionButton
            :label="getTypeActionDisplayName(TypeAction.INSPECTION)"
            :color="Color.Orange"
            :icon="faMagnifyingGlass"
          />
          <ActionButton
            :label="getTypeActionDisplayName(TypeAction.PALPATATION)"
            :color="Color.Orange"
            :icon="faHandHoldingMedical"
          />
          <ActionButton
            :label="getTypeActionDisplayName(TypeAction.PERCUSSION)"
            :color="Color.Orange"
            :icon="faGavel"
          />
          <ActionButton
            :label="getTypeActionDisplayName(TypeAction.AUSCULTATION)"
            :color="Color.Orange"
            :icon="faStethoscope"
          />
        </div>
        <div class="column">
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
          <ActionButton
            label="Caractéristiques du patient"
            :color="Color.Red"
            :icon="faPerson"
          />
        </div>
      </div>
      <ActionButton
        class="create-button"
        label="Créer le patient"
        :color="Color.Green"
      />
    </div>
  </AuthenticatedPageLayout>
</template>

<style>
.title {
  @apply text-2xl text-primary font-bold;
}

.text {
  @apply w-2/4 pt-3;
  text-align: center;
}

.diagnostic-actions {
  gap: 2rem;
  display: flex;
  margin: 2rem 0;
}

.column {
  flex-basis: 33.33%;
  display: flex;
  flex-direction: column;
}

.create-button {
  width: 25%;
  min-width: 200px;
}
</style>
