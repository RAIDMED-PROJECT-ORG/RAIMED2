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

type Characteristics = {
  sex: string;
  age: number;
  diagnostic: string;
};

const isCharacteristicModalOpen = ref(false);
const characteristicData = ref<Characteristics | null>(null);

function onCharacteristicValidation(data: Characteristics) {
  characteristicData.value = data;
  console.log('Characteristics :', data);
  switchCharacteristicModalVisibility();
}

function switchCharacteristicModalVisibility() {
  isCharacteristicModalOpen.value = !isCharacteristicModalOpen.value;
}
</script>

<template>
  <AuthenticatedPageLayout>
    <CharacteristicModal
      v-if="isCharacteristicModalOpen"
      :onValidation="onCharacteristicValidation"
      :onBack="switchCharacteristicModalVisibility"
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
          <ActionButton label="Question" :color="Color.Blue" :icon="faPersonCircleQuestion" />
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
          class="mt-8"
          :onClick="() => router.back()"
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
