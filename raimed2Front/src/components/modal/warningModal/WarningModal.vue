<script setup lang="ts">
import { Color } from '@/models/new-patient/color.model';
import GenericModal from '@/components/modal/genericModal/GenericModal.vue';
import router from '@/router';

defineProps<{
  onBack: () => void;
  errors: string[];
}>();
</script>

<template>
  <GenericModal
    title="Attention"
    :header-color="Color.Red"
    validation-label="Confirmer la création"
    :on-validation="() => router.back()"
    :onBack="onBack"
  >
    <div class="flex gap-4 flex-col ml-8 mr-8">
      <div class="mt-4">
        <p>Les champs suivants n'ont pas été renseignés et n'auront que les valeurs par défaut :</p>
        <ul class="list-disc ml-6">
          <li v-for="(error, index) in errors" :key="index">{{ error }}</li>
        </ul>
        <p class="mt-2">Voulez-vous continuer ?</p>
      </div>
    </div>
  </GenericModal>
</template>

<style scoped></style>
