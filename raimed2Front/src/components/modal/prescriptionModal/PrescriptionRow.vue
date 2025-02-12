<script setup lang="ts">
import { Color } from '@/models/new-patient/color.model';
import {
  faPenToSquare,
  faSyringe,
  faTrashCan,
  faVial,
  faXRay
} from '@fortawesome/free-solid-svg-icons';
import ActionButton from '@/components/actionButton/ActionButton.vue';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { PrescriptionType } from '@/models/prescription/prescriptionType.enum';

defineProps<{
  type: PrescriptionType;
  name: string;
  result: string;
}>();

defineEmits<{
  (e: 'deletePrescription'): void;
  (e: 'updatePrescription'): void;
}>();

const getIcon = (type: PrescriptionType) => {
  switch (type) {
    case PrescriptionType.BIOLOGY:
      return faVial;
    case PrescriptionType.IMAGERY:
      return faXRay;
    case PrescriptionType.BIOPSY:
      return faSyringe;
    default:
      return faVial;
  }
};
</script>

<template>
  <div class="flex justify-between items-center border-b border-1 border-light-grey-border py-2">
    <div class="w-[15%] flex items-center justify-center">
      <div class="flex items-center justify-center w-8 h-8 rounded-full bg-gray-200">
        <FontAwesomeIcon :icon="getIcon(type)" class="text-gray-600" />
      </div>
    </div>
    <span class="w-[25%] truncate name">{{ name }}</span>
    <div class="w-[45%] result">{{ result }}</div>
    <div class="w-[15%] flex gap-2">
      <ActionButton
        :color="Color.Grey"
        :icon="faPenToSquare"
        size="small"
        :on-click="() => $emit('updatePrescription')"
      />
      <ActionButton
        :color="Color.Grey"
        :icon="faTrashCan"
        size="small"
        :on-click="() => $emit('deletePrescription')"
      />
    </div>
  </div>
</template>

<style scoped></style>
