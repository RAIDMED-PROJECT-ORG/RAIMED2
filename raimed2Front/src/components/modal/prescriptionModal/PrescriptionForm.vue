<script setup lang="ts">
import { ref, watch } from 'vue';
import { v4 as uuidv4 } from 'uuid';
import { Color } from '@/models/new-patient/color.model';
import { faCirclePlus, faSearch, faSliders } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import ActionButton from '@/components/actionButton/ActionButton.vue';
import { getPrescriptionTypeDisplayName, PrescriptionType } from '@/models/prescription/prescriptionType.enum';
import type { Prescription } from '@/models/prescription/prescription.model';
import ExistingPrescriptionModal from '@/components/modal/existingPrescriptionModal/ExistingPrescriptionModal.vue';

const props = defineProps<{
  prescriptionToUpdate?: Prescription;
  initialType: PrescriptionType;
  existingPrescriptions: Prescription[];
  prescriptions: Prescription[];
}>();

const emits = defineEmits<{
  (e: 'addPrescription', prescription: Prescription): void;
}>();

const typeValue = ref<PrescriptionType>(props.initialType);
const nameValue = ref<string>('');
const resultValue = ref<string>('');
const isExistingPrescriptionsModalOpen = ref(false);

const submitForm = (event: SubmitEvent) => {
  event.preventDefault();

  emits('addPrescription', {
    id: props.prescriptionToUpdate ? props.prescriptionToUpdate.id : uuidv4(),
    type: typeValue.value,
    name: nameValue.value,
    result: resultValue.value,
  });

  typeValue.value = props.initialType;
  nameValue.value = '';
  resultValue.value = '';
};

const addExistingPrescriptions = (prescriptions: Prescription[]) => {
  prescriptions.forEach(prescription => emits('addPrescription', prescription));
};

watch(
    () => props.prescriptionToUpdate,
    (prescriptionToUpdate) => {
      if (prescriptionToUpdate) {
        typeValue.value = prescriptionToUpdate.type;
        nameValue.value = prescriptionToUpdate.name;
        resultValue.value = prescriptionToUpdate.result ?? '';
      }
    }
);
</script>

<template>
  <div class="flex flex-col pl-10 overflow-y-auto">
    <h3 class="self-center text-black font-bold text-xl mb-3">Ajouter une prescription</h3>

    <form class="flex flex-col gap-3" @submit="submitForm">
      <div class="w-full">
        <label for="type" class="font-bold">
          <FontAwesomeIcon :icon="faSliders" class="icon-rotate" />
          Type*
        </label>
        <select
            id="type"
            class="w-full border border-[#D6D6D6] rounded-[8px] p-2"
            v-model="typeValue"
            aria-label="Type de prescription"
            required
        >
          <option :value="PrescriptionType.BIOLOGY">
            {{ getPrescriptionTypeDisplayName(PrescriptionType.BIOLOGY) }}
          </option>
          <option :value="PrescriptionType.IMAGERY">
            {{ getPrescriptionTypeDisplayName(PrescriptionType.IMAGERY) }}
          </option>
          <option :value="PrescriptionType.BIOPSY">
            {{ getPrescriptionTypeDisplayName(PrescriptionType.BIOPSY) }}
          </option>
        </select>
      </div>

      <div class="form-group">
        <label for="name" class="font-bold">
          Nom de la prescription*
        </label>
        <input
            type="text"
            id="name"
            class="select-input"
            placeholder="Insérer le nom de la prescription..."
            v-model="nameValue"
            aria-label="Nom de la prescription"
            required
        />
      </div>

      <div class="form-group">
        <label for="result" class="font-bold">
          Résultats observés
        </label>
        <textarea
            type="text"
            id="result"
            class="select-input"
            placeholder="Insérer les résultats observés..."
            v-model="resultValue"
            aria-label="Résultats observés"
        />
      </div>

      <ActionButton
          type="submit"
          :label="prescriptionToUpdate ? 'Mettre à jour la prescription' : 'Créer une prescription'"
          :icon="faCirclePlus"
          :color="Color.Grey"
          class="w-4/5 self-center drop-shadow-sm"
      />
    </form>

    <div class="flex items-center my-4">
      <div class="flex-grow border-t border-light-grey"></div>
      <span class="mx-2 text-light-grey">ou</span>
      <div class="flex-grow border-t border-light-grey"></div>
    </div>

    <div class="flex flex-col items-center">
      <h3 class="text-black font-bold text-xl mb-3">Importer une prescription</h3>
      <ActionButton
          label="Parcourir les prescriptions existantes"
          :icon="faSearch"
          :color="Color.Grey"
          class="w-11/12 self-center drop-shadow-sm"
          @click="isExistingPrescriptionsModalOpen = true"
      />
    </div>

    <ExistingPrescriptionModal
        v-if="isExistingPrescriptionsModalOpen"
        :existing-prescriptions="props.existingPrescriptions"
        :patientPrescriptions="prescriptions"
        @addPrescriptions="addExistingPrescriptions"
        @close="isExistingPrescriptionsModalOpen = false"
    />
  </div>
</template>

<style scoped>
.select-input {
  width: 100%;
  border: 1px solid #d6d6d6;
  border-radius: 8px;
  padding: 0.5rem;
}
</style>