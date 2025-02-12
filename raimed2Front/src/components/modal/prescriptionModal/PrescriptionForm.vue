<script setup lang="ts">
import { ref, watch } from 'vue';
import { v4 as uuidv4 } from 'uuid';
import { PrescriptionType } from '@/models/prescription/prescriptionType.enum';
import type { Prescription } from '@/models/prescription/prescription.model';
import GenericForm from '@/components/modal/genericModal/GenericForm.vue';
import IconLabel from '@/components/iconLabel/IconLabel.vue';
import { faCircleQuestion, faMessage } from '@fortawesome/free-regular-svg-icons';

const props = defineProps<{
  prescriptionToUpdate?: Prescription;
  prescriptionType: PrescriptionType;
}>();

const emits = defineEmits<{
  (e: 'addPrescription', prescription: Prescription): void;
}>();

const nameValue = ref<string>('');
const resultValue = ref<string>('');

const submitForm = () => {
  emits('addPrescription', {
    id: props.prescriptionToUpdate ? props.prescriptionToUpdate.id : uuidv4(),
    name: nameValue.value,
    result: resultValue.value
  });

  nameValue.value = '';
  resultValue.value = '';
};

watch(
  () => props.prescriptionToUpdate,
  (prescriptionToUpdate) => {
    if (prescriptionToUpdate) {
      nameValue.value = prescriptionToUpdate.name;
      resultValue.value = prescriptionToUpdate.result ?? '';
    }
  }
);
</script>

<template>
  <GenericForm
    title="Ajouter une prescription"
    :button-label="prescriptionToUpdate ? 'Mettre à jour la prescription' : 'Créer une prescription'"
    :import-title="null"
    :import-button-label="null"
    @onsubmit="() => submitForm()"
  >
    <div class="form-group">
      <IconLabel for="name" :icon="faCircleQuestion" text="Nom de la prescription*" />
      <input
        type="text"
        id="name"
        class="text-input"
        placeholder="Insérer le nom de la prescription..."
        v-model="nameValue"
        aria-label="Nom de la prescription"
        required
      />
    </div>

    <div class="form-group">
      <IconLabel for="result" :icon="faMessage" text="Résultats observés*" />
      <textarea
        type="text"
        id="result"
        class="text-input"
        placeholder="Insérer les résultats observés..."
        v-model="resultValue"
        aria-label="Résultats observés"
        required
      />
    </div>
  </GenericForm>
</template>

<style scoped></style>
