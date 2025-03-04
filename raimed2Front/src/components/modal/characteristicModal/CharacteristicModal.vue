<script setup lang="ts">
import { ref, watch } from 'vue';
import { Color } from '@/models/new-patient/color.model';
import GenericModal from '@/components/modal/genericModal/GenericModal.vue';
import { faCakeCandles, faMarsAndVenus, faQuestion } from '@fortawesome/free-solid-svg-icons';
import IconLabel from '@/components/iconLabel/IconLabel.vue';
import { type Characteristics } from '@/models/new-patient/characteristics.model';
import ClassicSelector from '@/components/classicSelector/ClassicSelector.vue';
import { Gender, GenderDisplayNames } from '@/models/virtual-patient/gender.enum';

const props = defineProps<{
  onValidation: (data: Characteristics) => void;
  onBack: () => void;
  currentCharacteristics: Characteristics | null;
}>();

const gender = ref<Gender>(props.currentCharacteristics?.gender || Gender.MALE);
const age = ref<number | null>(props.currentCharacteristics?.age || null);
const diagnostic = ref<string>(props.currentCharacteristics?.diagnostic || '');

const ageErrorMessage = ref<string | null>(null);
const diagnosticErrorMessage = ref<string | null>(null);
const confirmGoBack = ref<boolean>(false);

const sexOptions = Object.values(Gender).map((value) => ({
  value,
  label: GenderDisplayNames[value as Gender]
}));

watch(
  age,
  (newAge) => {
    confirmGoBack.value = newAge !== props.currentCharacteristics?.age;
  },
  { deep: true }
);

watch(
  diagnostic,
  (newDiagnostic) => {
    confirmGoBack.value = newDiagnostic !== props.currentCharacteristics?.diagnostic;
  },
  { deep: true }
);

function handleValidation() {
  if (diagnostic.value !== '') {
    diagnosticErrorMessage.value = null;
  } else {
    diagnosticErrorMessage.value = 'Champs requis.';
  }

  if (age.value !== null) {
    ageErrorMessage.value = null;

    if (age.value <= 0) {
      ageErrorMessage.value = 'L\'âge doit être supérieur à zéro.';
    }

    if (age.value > 120) {
      ageErrorMessage.value = 'L\'âge doit être inférieur à 120.';
    }
  } else {
    ageErrorMessage.value = 'Champs requis.';
  }

  if (diagnostic.value !== '' && age.value && ageErrorMessage.value === null) {
    props.onValidation({ gender: gender.value, age: age.value, diagnostic: diagnostic.value });
  }
}
</script>

<template>
  <GenericModal
    title="Inspection"
    validationLabel="Valider"
    :onValidation="handleValidation"
    :headerColor="Color.Red"
    :onBack="onBack"
    :confirmGoBack="confirmGoBack"
  >
    <div class="flex gap-4 flex-col ml-8 mr-8">
      <div>
        <IconLabel for="gender" :icon="faMarsAndVenus" text="Genre*" />
        <ClassicSelector id="gender" :options="sexOptions" v-model="gender" />
      </div>
      <div>
        <div>
          <IconLabel for="age" :icon="faCakeCandles" text="Âge*" />
          <input
            id="age"
            type="number"
            v-model="age"
            placeholder="Insérer l'âge"
            class="input w-[150px]"
            min=0
            max=120
          />
        </div>
        <span class="text-red-500" v-if="ageErrorMessage">{{ ageErrorMessage }}</span>
      </div>
      <div>
        <div>
          <IconLabel for="diagnostic" :icon="faQuestion" text="Diagnostic final*" />
          <input
            id="diagnostic"
            type="text"
            v-model="diagnostic"
            placeholder="Insérer le diagnostic final"
            class="input w-auto min-w-[350px]"
          />
        </div>
        <span class="text-red-500" v-if="diagnosticErrorMessage">{{ diagnosticErrorMessage }}</span>
      </div>
      <span>* Champs requis</span>
    </div>
  </GenericModal>
</template>

<style scoped>
.text-red-500 {
  color: #f87171;
}

.input {
  border: 1px solid #d6d6d6;
  border-radius: 8px;
  padding: 0.5rem;
}

.input:focus {
  outline: none;
  border: 2px solid #d6d6d6;
}
</style>
