<script setup lang="ts">
import { Color } from '@/models/new-patient/color.model';
import { faPenToSquare, faTrashCan, faVial, faXRay, faSyringe } from '@fortawesome/free-solid-svg-icons';
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
  <div class="flex justify-between gap-10 items-center border-b border-1 border-light-grey-border">
    <div class="flex items-center gap-2">
      <div class="flex items-center justify-center w-8 h-8 rounded-full bg-gray-200">
        <FontAwesomeIcon :icon="getIcon(type)" class="text-gray-600" />
      </div>
      <span class="truncate ml-3 name">{{ name }}</span>
    </div>
    <div class="flex-1 truncate result">{{ result }}</div>
    <div class="flex gap-2">
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

<style scoped>
.truncate {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.result{
  max-width: 150px;
}
.name{
  width: 100px;
}
</style>