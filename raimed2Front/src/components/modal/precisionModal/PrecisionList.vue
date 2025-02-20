<script setup lang="ts">
import { Color } from '@/models/new-patient/color.model';
import { faPenToSquare, faTrashCan } from '@fortawesome/free-regular-svg-icons';
import ActionButton from '@/components/actionButton/ActionButton.vue';
import GenericList from '@/components/modal/genericModal/GenericList.vue';
import type { Precision } from '@/models/question/precision.model';
import PrecisionRow from '@/components/modal/precisionModal/PrecisionRow.vue';

defineProps<{
  precisions: Precision[];
}>();

defineEmits<{
  (e: 'deletePrecision', id: string): void;
  (e: 'updatePrecision', id: string): void;
}>();
</script>

<template>
  <GenericList
    title="Liste des précisions"
    size="medium"
    :columns="{
      'Eléments primaires': 40,
      Questions: 40,
      Actions: 20
    }"
  >
    <PrecisionRow
      v-for="(precision, index) in precisions"
      :key="index"
      :id="precision.question"
      :question="precision.question"
      :answer="precision.answer ?? ''"
      :primaryElement="precision.primaryElement || ''"
    >
      <div class="w-[20%] flex gap-2">
        <ActionButton
          :color="Color.Grey"
          :icon="faPenToSquare"
          size="small"
          :on-click="() => $emit('updatePrecision', precision.id)"
        />
        <ActionButton
          :color="Color.Grey"
          :icon="faTrashCan"
          size="small"
          :on-click="() => $emit('deletePrecision', precision.id)"
        />
      </div>
    </PrecisionRow>
  </GenericList>
</template>

<style scoped></style>
