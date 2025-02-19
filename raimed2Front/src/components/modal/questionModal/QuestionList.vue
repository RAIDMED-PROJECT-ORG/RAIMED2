<script setup lang="ts">
import QuestionRow from '@/components/modal/questionModal/QuestionRow.vue';
import type { Question } from '@/models/question/question.model';
import { Color } from '@/models/new-patient/color.model';
import { faPenToSquare, faTrashCan } from '@fortawesome/free-regular-svg-icons';
import ActionButton from '@/components/actionButton/ActionButton.vue';
import GenericList from '@/components/modal/genericModal/GenericList.vue';

defineProps<{
  questions: Question[];
}>();

defineEmits<{
  (e: 'deleteQuestion', id: string): void;
  (e: 'updateQuestion', id: string): void;
}>();
</script>

<template>
  <GenericList
    title="Liste des questions"
    size="medium"
    :columns="{
      Questions: 40,
      'Eléments primaires': 40,
      Actions: 20
    }"
  >
    <QuestionRow
      v-for="(question, index) in questions"
      :key="index"
      :id="question.content"
      :question="question.content"
      :answer="question.answer ?? ''"
      :primaryElement="question.primaryElement || ''"
    >
      <div class="w-[20%] flex gap-2">
        <ActionButton
          :color="Color.Grey"
          :icon="faPenToSquare"
          size="small"
          :on-click="() => $emit('updateQuestion', question.id)"
        />
        <ActionButton
          :color="Color.Grey"
          :icon="faTrashCan"
          size="small"
          :on-click="() => $emit('deleteQuestion', question.id)"
        />
      </div>
    </QuestionRow>
  </GenericList>
</template>

<style scoped></style>
