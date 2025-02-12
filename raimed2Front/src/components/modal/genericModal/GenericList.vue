<script setup lang="ts">
import { computed } from 'vue';

const props = defineProps<{
  title: string;
  columns: Record<string, number>;
}>();

const columnStyles = computed(() => {
  return Object.entries(props.columns).map(([column, width]) => ({
    column,
    style: { width: `${width}%` }
  }));
});
</script>

<template>
  <div class="flex flex-col border-r border-1 pr-5 w-3/6">
    <h3 class="self-center text-black font-bold text-xl mb-3">{{ title }}</h3>
    <div class="pr-5 overflow-scroll">
      <div class="flex justify-between border-b border-1 border-[#9CA3AF] text-black font-bold">
        <h4
          v-for="{ column, style } in columnStyles"
          :key="column"
          class="text-[1rem] font-bold truncate"
          :style="style"
        >
          {{ column }}
        </h4>
      </div>
      <slot />
    </div>
  </div>
</template>

<style scoped></style>
