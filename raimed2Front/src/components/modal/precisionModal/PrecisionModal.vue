<script setup lang="ts">
import { Color } from '@/models/new-patient/color.model';
import GenericModal from '@/components/modal/genericModal/GenericModal.vue';
import { ref, watch } from 'vue';
import PrecisionForm from '@/components/modal/precisionModal/PrecisionForm.vue';
import type { Precision } from '@/models/question/precision.model';
import PrecisionList from '@/components/modal/precisionModal/PrecisionList.vue';

const props = defineProps<{
  precisions: Precision[];
  primaryElements: string[];
  onValidation: (data: Precision[]) => void;
  onBack: () => void;
}>();

const precisions = ref<Precision[]>([...props.precisions]);
const precisionToUpdate = ref<Precision | null>(null);
const confirmGoBack = ref<boolean>(false);

watch(
  precisions,
  (newPrecisions) => {
    confirmGoBack.value = JSON.stringify(props.precisions) !== JSON.stringify(newPrecisions);
  },
  { deep: true }
);

const deletePrecision = (id: string) => {
  precisions.value = precisions.value.filter((precision) => precision.id !== id);
};

const updatePrecision = (id: string) => {
  precisionToUpdate.value = precisions.value.find((precision) => precision.id === id) ?? null;
};

const insertOrUpdatePrecision = (precision: Precision) => {
  const index = precisions.value.findIndex((q) => q.id === precision.id);
  if (index === -1) {
    precisions.value.push(precision);
  } else {
    precisions.value[index] = precision;
    precisionToUpdate.value = null;
  }
};

const handleValidation = () => {
  props.onValidation(precisions.value);
};
</script>

<template>
  <GenericModal
    title="Préciser un symptôme"
    validationLabel="Valider"
    :onValidation="handleValidation"
    :headerColor="Color.Blue"
    :onBack="onBack"
    :confirmGoBack="confirmGoBack"
  >
    <div class="w-[70vw] h-[60vh] flex px-10">
      <PrecisionList
        :precisions="precisions"
        @delete-precision="deletePrecision"
        @update-precision="updatePrecision"
      />
      <PrecisionForm
        @add-precision="insertOrUpdatePrecision"
        :precision-to-update="precisionToUpdate"
        :primary-elements="props.primaryElements"
      />
    </div>
  </GenericModal>
</template>

<style scoped></style>
