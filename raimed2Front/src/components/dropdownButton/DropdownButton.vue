<template>
  <div class="relative inline-block w-auto">
    <button
      @click="toggleDropdown"
      class="w-full min-w-[150px] flex items-center justify-between px-4 py-2 border border-gray-300 rounded-lg text-black focus:outline-none"
    >
      <span class="mr-2">{{ selectedOption }}</span>
      <svg
        :class="'w-4 h-4 text-gray-400' + (isOpen ? ' transform rotate-180' : '')"
        fill="none"
        stroke="currentColor"
        viewBox="0 0 24 24"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
      </svg>
    </button>
    <div
      v-if="isOpen"
      class="absolute right-0 mt-2 w-full bg-white border border-gray-300 rounded-lg shadow-lg z-10"
    >
      <ul>
        <li
          v-for="option in options"
          :key="option"
          @click="selectOption(option)"
          class="px-4 py-2 hover:bg-gray-100 cursor-pointer"
        >
          {{ option }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue';
import { defineProps } from 'vue';

const props = defineProps({
  options: {
    type: Array as () => string[],
    required: true
  },
  modelValue: {
    type: String,
    required: true
  }
});

const emit = defineEmits(['update:modelValue']);

const isOpen = ref(false);

const selectedOption = computed(() => props.modelValue);

function toggleDropdown() {
  isOpen.value = !isOpen.value;
}

function selectOption(option: string) {
  emit('update:modelValue', option);
  isOpen.value = false;
}
</script>
