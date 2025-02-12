<script setup lang="ts">
import { computed, ref, watch, onMounted, onUnmounted } from 'vue';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import {
  faChevronDown,
  faChevronUp,
  faXmark,
} from '@fortawesome/free-solid-svg-icons';

const props = defineProps<{
  options: string[];
  modelValue: string[];
}>();

const emits = defineEmits<{
  (event: 'update:modelValue', newValue: string[]): void;
}>();

const showDropdown = ref(false);
const input = ref('');
const inputField = ref<HTMLInputElement | null>(null);
const componentRoot = ref<HTMLElement | null>(null);

const highlightedIndex = ref(-1);
const optionRefs = ref<(HTMLElement | null)[]>([]);

const visibleLimit = 3;

const visibleOptions = computed(() => {
  return props.modelValue.slice(0, visibleLimit);
});

const remainingOptions = computed(() => {
  return props.modelValue.length - visibleLimit;
});

function toggleDropdown() {
  showDropdown.value = !showDropdown.value;
  if (showDropdown.value) {
    inputField.value?.focus();
  }
}

const filteredOptions = computed(() => {
  const lowerInput = input.value.toLowerCase();
  return props.options.filter(
    (option) =>
      option.toLowerCase().includes(lowerInput) &&
      !props.modelValue.includes(option)
  );
});

function handleOnInput() {
  showDropdown.value = true;
  highlightedIndex.value = 0;
}

function selectItem(option: string) {
  if (option && !props.modelValue.includes(option)) {
    const newArray = [...props.modelValue, option];
    emits('update:modelValue', newArray);
    input.value = '';
  }
  showDropdown.value = false;
  highlightedIndex.value = -1;
}

function unselectOption(option: string) {
  const newArray = props.modelValue.filter((i) => i !== option);
  emits('update:modelValue', newArray);
}

function removeLastTag(event: KeyboardEvent) {
  if (input.value === '') {
    if (props.modelValue.length > 0) {
      const newArray = [...props.modelValue];
      newArray.pop();
      emits('update:modelValue', newArray);
      event.preventDefault();
    }
  }
}

function focusInput() {
  inputField.value?.focus();
}

function highlightNext() {
  showDropdown.value = true;
  if (highlightedIndex.value < filteredOptions.value.length - 1) {
    highlightedIndex.value += 1;
  } else {
    highlightedIndex.value = 0;
  }
}

function highlightPrevious() {
  showDropdown.value = true;
  if (highlightedIndex.value > 0) {
    highlightedIndex.value -= 1;
  } else {
    highlightedIndex.value = filteredOptions.value.length - 1;
  }
}

watch(highlightedIndex, (newIndex) => {
  const el = optionRefs.value[newIndex];
  if (el) {
    el.scrollIntoView({
      block: 'nearest',
      behavior: 'smooth',
    });
  }
});

function handleClickOutside(event: MouseEvent) {
  if (
    componentRoot.value &&
    !componentRoot.value.contains(event.target as Node)
  ) {
    showDropdown.value = false;
    inputField.value?.blur();
  }
}
function handleEsc() {
  showDropdown.value = false;
  inputField.value?.blur();
}

onMounted(() => {
  document.addEventListener('click', handleClickOutside);
});

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside);
});
</script>

<template>
  <div ref="componentRoot" class="relative">
    <div
      class="flex flex-wrap items-center gap-2 min-h-[50px] w-full px-4 py-2 pr-9 border border-gray-300 rounded-lg text-gray-500 placeholder-gray-500 focus-within:ring-2 focus-within:ring-gray-300"
      @click="focusInput"
    >
      <!-- Display currently selected items as tags -->
      <div
        v-for="(option, index) in visibleOptions"
        :key="index"
        class="bg-gray-200 text-gray-700 px-2 py-1 rounded-full flex items-center space-x-2 cursor-pointer"
        @click.stop="unselectOption(option)"
      >
        <span>{{ option }}</span>
        <FontAwesomeIcon :icon="faXmark" class="w-2 h-2" />
      </div>

      <!-- Indicate remaining options -->
      <span v-if="remainingOptions > 0" class="text-gray-500">
        +{{ remainingOptions }} autres
      </span>

      <!-- The text input -->
      <input
        class="flex-1 min-w-[50px] outline-none text-gray-500 placeholder-gray-500"
        type="text"
        :placeholder="props.modelValue.length === 0 ? 'Choisir une valeur' : ''"
        v-model="input"
        @focus="showDropdown = true"
        @input="handleOnInput"
        @keydown.enter.stop.prevent="selectItem(filteredOptions[highlightedIndex])"
        @keydown.tab.stop.prevent="selectItem(filteredOptions[highlightedIndex])"
        @keydown.esc.stop.prevent="handleEsc"
        @keydown.backspace="removeLastTag"
        @keydown.down.prevent="highlightNext"
        @keydown.up.prevent="highlightPrevious"
        ref="inputField"
      />

      <!-- Chevron -->
      <FontAwesomeIcon
        class="absolute right-[17px] top-[17px] text-gray-500 cursor-pointer"
        :icon="showDropdown ? faChevronUp : faChevronDown"
        @click.stop="toggleDropdown"
      />
    </div>

    <!-- Dropdown list -->
    <ul
      class="absolute right-0 mt-2 w-full max-h-40 overflow-y-auto bg-white border border-gray-300 rounded-lg shadow-lg z-10"
      v-if="showDropdown && filteredOptions.length > 0"
    >
      <li
        class="px-4 py-2 hover:bg-gray-100 cursor-pointer"
        v-for="(option, index) in filteredOptions"
        :key="option"
        @click="selectItem(option)"
        :class="{ 'bg-gray-200': index === highlightedIndex }"
        ref="optionRefs"
      >
        {{ option }}
      </li>
    </ul>
  </div>
</template>