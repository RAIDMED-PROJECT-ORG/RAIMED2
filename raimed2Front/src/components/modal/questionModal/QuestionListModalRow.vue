<script setup lang="ts">
import { ref } from 'vue';
import type { Question } from '@/models/question/question.model';
import { QuestionFilter } from '@/models/question/questionFilter.enum';

const props = defineProps<{
  question: Question;
  isAlreadySelected: boolean;
}>();

const emits = defineEmits<{
  (e: 'addQuestion'): void;
  (e: 'removeQuestion'): void;
}>();

const isSelected = ref<boolean>(props.isAlreadySelected);

const switchIsSelected = () => {
  isSelected.value = !isSelected.value;
  if (isSelected.value) {
    emits('addQuestion');
  } else {
    emits('removeQuestion');
  }
};

const getFilter = (filter: string) => {
  switch (filter) {
    case QuestionFilter.MALE:
      return 'H';
    case QuestionFilter.FEMALE:
      return 'F';
    case QuestionFilter.MIXED:
      return 'M';
  }
}
</script>

<template>
  <div
    class="flex justify-between items-center border-b border-1 border-light-grey-border cursor-pointer"
    @click="switchIsSelected"
  >
    <div class="w-8/12">
      <span>{{ question.content }}</span>
      <div class="flex items-center gap-2">
        <hr class="border-[#9CA3AF] w-[7%]" />
        {{ question.answer ?? '' }}
      </div>
    </div>
    <div class="w-2/12 flex justify-center">{{ getFilter(question.filter) }}</div>
    <div class="w-2/12 flex justify-center">
      <input type="checkbox" v-model="isSelected" />
    </div>
  </div>
</template>

<style scoped></style>
