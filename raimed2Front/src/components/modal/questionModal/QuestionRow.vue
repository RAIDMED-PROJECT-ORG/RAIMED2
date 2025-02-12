<script setup lang="ts">

defineProps<{
  question: string;
  answer ?: string;
}>();
</script>

<template>
  <div
    class="flex justify-between gap-10 items-center border-b border-1 border-light-grey-border py-2"
  >
    <div class="w-[80%]">
      <span>{{ question }}</span>
      <div class="flex items-center gap-2" v-if="answer">
        <hr class="border-[#9CA3AF] w-[7%]" />
        {{ answer }}
      </div>
    </div>
    <slot></slot>
  </div>
</template>

<style scoped></style>
