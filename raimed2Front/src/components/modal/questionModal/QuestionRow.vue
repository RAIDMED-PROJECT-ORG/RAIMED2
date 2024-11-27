<script setup lang="ts">
import { Color } from '@/models/new-patient/color.model';
import { faPenToSquare, faTrashCan } from '@fortawesome/free-regular-svg-icons';
import ActionButton from '@/components/actionButton/ActionButton.vue';

defineProps<{
  question: string;
  answer: string;
}>();
</script>

<template>
  <div class="flex justify-between gap-10 items-center border-b border-1 border-[#E7E7E7]">
    <div>
      <span>{{ question }}</span>
      <div class="flex items-center gap-2">
        <hr class="border-[#9CA3AF] w-[7%]" />
        {{ answer }}
      </div>
    </div>
    <div class="flex gap-2">
      <ActionButton :color="Color.Grey" :icon="faPenToSquare" size="small" />
      <ActionButton :color="Color.Grey" :icon="faTrashCan" size="small" />
    </div>
  </div>
</template>

<style scoped></style>
