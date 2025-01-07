<script setup lang="ts">
import QuestionRow from '@/components/modal/questionModal/QuestionRow.vue';
import type { Question } from '@/models/question/question.model';

defineProps<{
  questions: Question[];
}>();

defineEmits<{
  (e: 'deleteQuestion', id: string): void;
  (e: 'updateQuestion', id: string): void;
}>();
</script>

<template>
  <div class="flex flex-col border-r border-1 pr-5">
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
        @delete-question="() => $emit('deleteQuestion', question.id)"
        @update-question="() => $emit('updateQuestion', question.id)"
      />
    </div>
  </div>
</template>

<style scoped></style>
