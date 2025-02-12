<template>
  <button
    class="btn m-1 flex-nowrap"
    :class="{
      'rounded-full': label === undefined,
      'w-7 h-7 min-h-5 p-0': size === 'small',
      'w-20 h-20 min-h-20': size === 'tall'
    }"
    @click="handleClick"
    :disabled="disabled"
    :style="buttonStyle"
    :type="type ?? 'button'"
    @mouseover="hover = true"
    @mouseleave="hover = false"
  >
    <FontAwesomeIcon
      v-if="icon"
      :icon="icon"
      :class="{ 'h-3 w-3': size === 'small', 'h-12 w-12': size === 'tall' }"
    />
    <span v-if="label !== undefined">{{ label }}</span>
  </button>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { Color, type ColorType } from '@/models/new-patient/color.model';
import type { IconDefinition } from '@fortawesome/fontawesome-svg-core';

const props = defineProps<{
  color: ColorType;
  type?: 'button' | 'submit' | 'reset';
  size?: 'small' | 'medium' | 'tall';
  icon?: IconDefinition;
  label?: string;
  onClick?: (event: MouseEvent) => void;
  disabled?: boolean;
}>();

const hover = ref(false);

const buttonStyle = computed(() => {
  if (props.disabled) {
    return {
      backgroundColor:
        props.color === Color.Blue
          ? '#b7d8ee'
          : props.color === Color.Orange
            ? '#eed0a1'
            : props.color === Color.Purple
              ? '#d5c3dd'
              : props.color === Color.Green
                ? '#8fd9b0'
                : props.color === Color.Grey
                  ? '#e6e6e6'
                  : '#f9c0c1' // Default for Red
    };
  } else if (hover.value) {
    return {
      backgroundColor:
        props.color === Color.Blue
          ? '#60aee2'
          : props.color === Color.Orange
            ? '#eeb354'
            : props.color === Color.Purple
              ? '#c694db'
              : props.color === Color.Green
                ? '#1e8e5b'
                : props.color === Color.Grey
                  ? '#bfbfbf'
                  : '#f26d6e' // Default for Red
    };
  }
  return { backgroundColor: props.color };
});

const handleClick = (event: MouseEvent) => {
  if (!props.disabled) {
    props.onClick?.(event);
  }
};
</script>

<style scoped>
.btn {
  transition: background-color 0.3s ease;
}

span {
  font-weight: 600;
}
</style>
