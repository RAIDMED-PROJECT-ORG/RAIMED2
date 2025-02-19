<script setup lang="ts">

defineProps<{
  content: string;
  primaryElement: string;
}>();
</script>

<template>
  <div
    class="flex justify-between gap-10 items-center border-b border-1 border-light-grey-border py-2"
  >
    <div class="w-[30%]">
      <span>{{ content }}</span>
    </div>
    <div class="w-[40%]">
      <span>{{ primaryElement }}</span>
    </div>
    <slot></slot>
  </div>
</template>

<style scoped></style>
