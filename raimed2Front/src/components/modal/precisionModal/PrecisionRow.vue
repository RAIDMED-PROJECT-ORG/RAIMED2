<script setup lang="ts">

defineProps<{
  question: string;
  answer: string;
  primaryElement: string;
}>();
</script>

<template>
  <div
    class="flex justify-between items-center border-b border-1 border-light-grey-border py-2"
  >
    <div class="w-[40%] pr-2">
      <span>{{ primaryElement }}</span>
    </div>
    <div class="w-[40%] pr-2">
      <span>{{ question }}</span>
      <div class="flex items-center gap-2" v-if="answer">
        <hr class="border-[#9CA3AF] w-[7%]" />
        {{ answer }}
      </div>
    </div>
    <slot class="w-[20%]"></slot>
  </div>
</template>

<style scoped></style>
