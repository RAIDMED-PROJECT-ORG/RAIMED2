<script setup lang="ts">
import { defineProps } from 'vue';
import ActionButton from '@/components/actionButton/ActionButton.vue';
import { type ColorType } from '@/models/new-patient/color.model';
import type { IconDefinition } from '@fortawesome/fontawesome-svg-core';

const props = defineProps<{
  label: string;
  color: ColorType;
  icon: IconDefinition;
  completed: boolean;
  onClick: () => void;
  disabled?: boolean;
}>();
</script>

<template>
  <div class="relative">
    <ActionButton
      class="w-full"
      :label="props.label"
      :color="props.color"
      :icon="props.icon"
      @click="onClick"
      :disabled="props.disabled"
    />

    <Transition
      enter-active-class="transition-opacity duration-300 ease-in-out transform"
      enter-from-class="opacity-0 -translate-y-1"
      enter-to-class="opacity-100 translate-y-0"
      leave-active-class="transition-opacity duration-300 ease-in-out transform"
      leave-from-class="opacity-100 translate-y-0"
      leave-to-class="opacity-0 -translate-y-1"
    >
      <span v-if="props.completed" class="absolute -top-1 -right-5 bg-[#4CAF50] text-white text-xs font-bold px-2 py-1 rounded-md shadow-md">
        ✔ Remplie
      </span>
    </Transition>
  </div>
</template>