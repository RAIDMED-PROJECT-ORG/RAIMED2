<script setup>
import { computed, defineProps } from 'vue';

const props = defineProps({
  percentage: {
    type: Number,
    required: true,
    validator: (value) => value >= 0 && value <= 100
  }
});

const progressColor = computed(() => {
  if (props.percentage < 30) return 'bg-red-500';
  if (props.percentage < 60) return 'bg-yellow-500';
  return 'bg-green-500';
});
</script>

<template>
  <div class="relative w-full max-w-md mt-2 h-8 bg-gray-300 rounded-lg overflow-hidden shadow-md">
    <span class="absolute left-2 top-1/2 -translate-y-1/2 text-xs font-semibold text-gray-600">0%</span>
    <span class="absolute right-2 top-1/2 -translate-y-1/2 text-xs font-semibold text-gray-600">100%</span>

    <div
      class="progress-bar absolute h-full"
      :class="progressColor"
      :style="{ width: props.percentage + '%' }"
    >
      <span v-if="props.percentage > 0" class="absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2 text-xs font-bold text-white">
        {{ Math.round(percentage) }}%
      </span>
    </div>
  </div>
</template>

<style scoped>
.progress-bar {
  transition: width 0.5s ease-in-out;
}
</style>