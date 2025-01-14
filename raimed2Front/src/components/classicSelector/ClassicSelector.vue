<script setup lang="ts">
import { ref, watch, onMounted, onUnmounted, nextTick } from 'vue';

const props = defineProps<{
  options: string[];
  modelValue: string;
}>();

const emits = defineEmits<{
  (event: 'update:modelValue', value: string): void;
}>();

const showDropdown = ref(false);
const selectedOption = ref(props.modelValue);
const dropdownWidth = ref<number>(0);
const container = ref<HTMLElement | null>(null);

function toggleDropdown() {
  showDropdown.value = !showDropdown.value;
}

function selectOption(option: string) {
  selectedOption.value = option;
  emits('update:modelValue', option);
  showDropdown.value = false;
}

function calculateDropdownWidth() {
  if (container.value) {
    const options = props.options.map((option) => {
      const span = document.createElement('span');
      span.style.visibility = 'hidden';
      span.style.position = 'absolute';
      span.style.whiteSpace = 'nowrap';
      span.textContent = option;
      document.body.appendChild(span);
      const width = span.offsetWidth;
      document.body.removeChild(span);
      return width;
    });
    dropdownWidth.value = Math.max(...options) + 48;
  }
}

function handleClickOutside(event: MouseEvent) {
  if (container.value && !container.value.contains(event.target as Node)) {
    showDropdown.value = false;
  }
}

watch(
  () => props.modelValue,
  (newValue) => {
    selectedOption.value = newValue;
  }
);

watch(() => props.options, () => {
  nextTick(calculateDropdownWidth);
});

onMounted(() => {
  nextTick(calculateDropdownWidth);
  document.addEventListener('click', handleClickOutside);
});

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside);
});
</script>

<template>
  <div ref="container" class="relative inline-block">
    <div
      class="flex items-center border border-gray-300 rounded-md px-4 py-2 cursor-pointer"
      :style="{ width: dropdownWidth + 'px' }"
      @click="toggleDropdown"
    >
      <span class="truncate">{{ selectedOption || 'Sélectionnez une option' }}</span>
      <svg
        xmlns="http://www.w3.org/2000/svg"
        fill="none"
        viewBox="0 0 24 24"
        stroke-width="2"
        stroke="currentColor"
        class="w-5 h-5 absolute right-3 text-gray-500"
      >
        <path
          stroke-linecap="round"
          stroke-linejoin="round"
          d="M19 9l-7 7-7-7"
        />
      </svg>
    </div>

    <ul
      v-if="showDropdown"
      class="absolute left-0 mt-2 bg-white border border-gray-300 rounded-md shadow-lg z-10 max-h-40 overflow-y-auto"
      :style="{ width: dropdownWidth + 'px' }"
    >
      <li
        v-for="(option, idx) in props.options"
        :key="idx"
        class="px-4 py-2 hover:bg-gray-100 cursor-pointer"
        @click="selectOption(option)"
      >
        {{ option }}
      </li>
    </ul>
  </div>
</template>