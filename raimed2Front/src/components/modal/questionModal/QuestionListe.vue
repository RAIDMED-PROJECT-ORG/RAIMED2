<script setup lang="ts">
import QuestionRow from '@/components/modal/questionModal/QuestionRow.vue';
import type { Question } from '@/models/question/question.model';
import { Color } from '@/models/new-patient/color.model';
import { faPenToSquare, faTrashCan } from '@fortawesome/free-regular-svg-icons';
import ActionButton from '@/components/actionButton/ActionButton.vue';

defineProps<{
  questions: Question[];
}>();

defineEmits<{
  (e: 'deleteQuestion', id: string): void;
  (e: 'updateQuestion', id: string): void;
}>();
</script>

<template>
  <div class="flex flex-col border-r border-1 pr-5 w-full">
    <h3 class="self-center text-black font-bold text-xl mb-3">Liste des questions</h3>
    <div class="pr-5 overflow-scroll">
      <div class="flex justify-between border-b border-1 border-[#9CA3AF] text-black font-bold">
        <h4>Questions</h4>
        <h4>Actions</h4>
      </div>
      <QuestionRow
        v-for="(question, index) in questions"
        :key="index"
        :id="question.content"
        :question="question.content"
        :answer="question.answer ?? ''"
      >
        <div class="w-4/12 flex justify-end gap-2">
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
    </div>
  </div>
</template>

<style scoped></style>
