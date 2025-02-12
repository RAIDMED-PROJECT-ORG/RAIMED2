<script setup lang="ts">

defineProps<{
  listen: string;
}>();
</script>

<template>
  <div
    class="flex justify-between gap-10 items-center border-b border-1 border-light-grey-border py-2"
  >
    <div class="w-[80%]">
      <span>{{ listen }}</span>
    </div>
    <slot></slot>
  </div>
</template>

<style scoped></style>
