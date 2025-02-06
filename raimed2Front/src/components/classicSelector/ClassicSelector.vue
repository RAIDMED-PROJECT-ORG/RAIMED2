<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted, nextTick } from 'vue';
import { faChevronDown, faChevronUp } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

const props = defineProps<{
  id: string;
  options: { value: string; label: string }[];
  modelValue: string;
}>();

const emits = defineEmits<{
  (event: 'update:modelValue', value: string): void;
}>();

const showDropdown = ref(false);
const dropdownWidth = ref<number>(0);
const container = ref<HTMLElement | null>(null);
const dropdownList = ref<HTMLElement | null>(null);
const highlightedIndex = ref<number>(-1);
const optionRefs = ref<(HTMLElement | null)[]>([]);

const selectedOption = computed(() =>
  props.options.find((opt) => opt.value === props.modelValue) || props.options[0]
);

function toggleDropdown() {
  showDropdown.value = !showDropdown.value;
  if (showDropdown.value) {
    highlightedIndex.value = props.options.findIndex((opt) => opt.value === props.modelValue);
    nextTick(() => scrollToHighlighted());
  }
}

function selectOption(option: { value: string; label: string }) {
  emits('update:modelValue', option.value);
  showDropdown.value = false;
}

function calculateDropdownWidth() {
  if (container.value) {
    const options = props.options.map((option) => {
      const span = document.createElement('span');
      span.style.visibility = 'hidden';
      span.style.position = 'absolute';
      span.style.whiteSpace = 'nowrap';
      span.textContent = option.label;
      document.body.appendChild(span);
      const width = span.offsetWidth;
      document.body.removeChild(span);
      return width;
    });
    dropdownWidth.value = Math.max(...options) + 60;
  }
}

function handleClickOutside(event: MouseEvent) {
  if (container.value && !container.value.contains(event.target as Node)) {
    showDropdown.value = false;
  }
}

function handleKeyDown(event: KeyboardEvent) {
  if (!showDropdown.value) return;

  if (event.key === 'ArrowDown') {
    event.preventDefault();
    highlightedIndex.value = (highlightedIndex.value + 1) % props.options.length;
    scrollToHighlighted();
  } else if (event.key === 'ArrowUp') {
    event.preventDefault();
    highlightedIndex.value =
      highlightedIndex.value <= 0 ? props.options.length - 1 : highlightedIndex.value - 1;
    scrollToHighlighted();
  } else if (event.key === 'Enter' && highlightedIndex.value >= 0) {
    event.preventDefault();
    selectOption(props.options[highlightedIndex.value]);
  } else if (event.key === 'Escape') {
    showDropdown.value = false;
  }
}

function scrollToHighlighted() {
  nextTick(() => {
    const highlightedElement = optionRefs.value[highlightedIndex.value];
    if (highlightedElement && dropdownList.value) {
      highlightedElement.scrollIntoView({ block: 'nearest', behavior: 'smooth' });
    }
  });
}

onMounted(() => {
  nextTick(calculateDropdownWidth);
  document.addEventListener('click', handleClickOutside);
  document.addEventListener('keydown', handleKeyDown);
});

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside);
  document.removeEventListener('keydown', handleKeyDown);
});
</script>

<template>
  <div :id="props.id" ref="container" class="relative inline-block">
    <div
      class="flex items-center border border-gray-300 rounded-md px-4 py-2 cursor-pointer"
      :style="{ width: dropdownWidth + 'px' }"
      @click="toggleDropdown"
    >
      <span class="truncate">{{ selectedOption?.label || 'Sélectionnez une option' }}</span>
      <FontAwesomeIcon
        class="absolute right-[17px] top-[13px] text-gray-500"
        :icon="showDropdown ? faChevronUp : faChevronDown"
      />
    </div>

    <ul
      v-if="showDropdown"
      ref="dropdownList"
      class="absolute left-0 mt-2 bg-white border border-gray-300 rounded-md shadow-lg z-10 max-h-40 overflow-y-auto"
      :style="{ width: dropdownWidth + 'px' }"
    >
      <li
        v-for="(option, idx) in props.options"
        :key="idx"
        ref="optionRefs"
        class="px-4 py-2 hover:bg-gray-100 cursor-pointer"
        :class="{ 'bg-gray-200': idx === highlightedIndex }"
        @click="selectOption(option)"
      >
        {{ option.label }}
      </li>
    </ul>
  </div>
</template>