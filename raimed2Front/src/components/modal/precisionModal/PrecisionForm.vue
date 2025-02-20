<script setup lang="ts">
import { faSliders } from '@fortawesome/free-solid-svg-icons';
import { faCircleQuestion, faMessage } from '@fortawesome/free-regular-svg-icons';
import { computed, ref, watch } from 'vue';
import { v4 as uuidv4 } from 'uuid';
import { useAuthStore } from '@/stores/auth.store';
import GenericForm from '@/components/modal/genericModal/GenericForm.vue';
import IconLabel from '@/components/iconLabel/IconLabel.vue';
import ClassicSelector from '@/components/classicSelector/ClassicSelector.vue';
import type { Precision } from '@/models/question/precision.model';

const props = defineProps<{
  precisionToUpdate?: Precision | null;
  primaryElements: string[];
}>();

const questionValue = ref<string>('');
const answerValue = ref<string>('');
const isModalOpen = ref<boolean>(false);
const primaryElementValue = ref<string>(props.primaryElements[0]);

const authStore = useAuthStore();

const primaryElementsOptions = Array.from(props.primaryElements).map((value: string) => ({
  value,
  label: value
}));

const emits = defineEmits<{
  (e: 'addPrecision', precision: Precision): void;
}>();

const submitForm = () => {
  authStore.initialize();

  emits('addPrecision', {
    id: props.precisionToUpdate ? props.precisionToUpdate.id : uuidv4(),
    question: questionValue.value,
    answer: answerValue.value,
    primaryElement: primaryElementValue.value,
  });
  questionValue.value = '';
  answerValue.value = '';
  primaryElementValue.value = props.primaryElements[0];
};

const switchModalVisibility = () => {
  isModalOpen.value = !isModalOpen.value;
};

watch(
  () => props.precisionToUpdate,
  (questionToUpdate) => {
    if (questionToUpdate) {
      questionValue.value = questionToUpdate.question;
      answerValue.value = questionToUpdate.answer ?? '';
      primaryElementValue.value = questionToUpdate?.primaryElement || '';
    }
  }
);
</script>

<template>
  <GenericForm
    title="Ajouter une précision"
    :button-label="precisionToUpdate ? 'Mettre à jour la précision' : 'Créer une précision'"
    :import-title="null"
    :import-button-label="null"
    @onsubmit="() => submitForm()"
    @open-modal="() => switchModalVisibility()"
  >
    <div class="form-group">
      <IconLabel for="primaryElement" :icon="faSliders" text="Elément primaire*" />
      <ClassicSelector required id="primaryElement" :options="primaryElementsOptions" v-model="primaryElementValue" />
    </div>
    <div class="form-group">
      <IconLabel for="question" :icon="faCircleQuestion" text="Question*" />
      <input
        type="text"
        id="question"
        class="text-input"
        :class="{ 'text-input--filled': questionValue.length > 0 }"
        placeholder="Insérer la question à ajouter..."
        v-model="questionValue"
        aria-label="Texte de la question"
        required
      />
    </div>
    <div class="form-group">
      <IconLabel for="answer" :icon="faMessage" text="Réponse*" />
      <input
        type="text"
        id="answer"
        class="text-input"
        :class="{ 'text-input--filled': answerValue.length > 0 }"
        placeholder="Insérer la réponse..."
        v-model="answerValue"
        aria-label="Texte de la réponse"
        required
      />
    </div>
    <p class="opacity-50">* champs requis</p>
  </GenericForm>
</template>

<style scoped></style>
