<script setup lang="ts">
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import type { IconDefinition } from '@fortawesome/fontawesome-svg-core';

const props = defineProps<{
  icon: IconDefinition;
  text: string;
  for: string;
}>();
</script>

<template>
  <label :for="props.for" class="flex items-center mb-2 font-bold text-base">
    <FontAwesomeIcon v-if="icon" :icon="icon" class="h-4 w-4 mr-1" />
    {{ text }}
  </label>
</template>

<style scoped></style>
