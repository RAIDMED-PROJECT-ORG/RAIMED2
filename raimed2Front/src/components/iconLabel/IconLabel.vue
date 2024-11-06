<script setup lang="ts">
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import type { IconDefinition } from '@fortawesome/fontawesome-svg-core';

defineProps<{
  icon: IconDefinition;
  text: string;
}>();
</script>

<template>
  <div class="flex items-center mb-2">
    <FontAwesomeIcon v-if="icon" :icon="icon" class="h-4 w-4 mr-1" />
    <p class="font-bold text-base">
      {{ text }}
    </p>
  </div>
</template>

<style scoped></style>
