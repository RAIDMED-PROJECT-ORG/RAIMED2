<script setup lang="ts">
import { Color } from '@/models/new-patient/color.model';
import { faPenToSquare, faTrashCan } from '@fortawesome/free-solid-svg-icons';
import ActionButton from '@/components/actionButton/ActionButton.vue';

defineProps<{
  name: string;
  result: string;
}>();

defineEmits<{
  (e: 'deletePrescription'): void;
  (e: 'updatePrescription'): void;
}>();
</script>

<template>
  <div class="flex justify-between items-center border-b border-1 border-light-grey-border py-2">
    <span class="w-[30%] truncate name">{{ name }}</span>
    <div class="w-[50%] result">{{ result }}</div>
    <div class="w-[20%] flex gap-2">
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
