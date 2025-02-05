<script setup lang="ts">

defineProps<{
  title: string;
  columns: string[];
}>();
</script>
<template>
  <div class="flex flex-col border-r border-1 pr-5 w-3/6">
    <h3 class="self-center text-black font-bold text-xl mb-3">{{ title }}</h3>
    <div class="pr-5 overflow-scroll">
      <div class="flex justify-between border-b border-1 border-[#9CA3AF] text-black font-bold">
        <h4 v-for="column in columns" class="text-[1rem] font-bold" :key="column">{{ column }}</h4>
      </div>
      <slot />
    </div>
  </div>
</template>

<style scoped></style>
