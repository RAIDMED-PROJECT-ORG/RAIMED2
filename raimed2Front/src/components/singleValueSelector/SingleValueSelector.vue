<script setup lang="ts">
import { computed, ref, watch, onMounted, onUnmounted, defineExpose } from 'vue';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faChevronDown, faChevronUp, faPlus, faTimes } from '@fortawesome/free-solid-svg-icons';

const props = defineProps<{
  id: string;
  options: string[];
  modelValue: string | undefined;
}>();

const emits = defineEmits<{
  (event: 'update:modelValue', value: string | undefined): void;
}>();

const showDropdown = ref(false);
const input = ref(props.modelValue || '');
const inputField = ref<HTMLInputElement | null>(null);
const componentRoot = ref<HTMLElement | null>(null);
const highlightedIndex = ref(-1);
const optionRefs = ref<(HTMLElement | null)[]>([]);
const isSelected = ref(!!props.modelValue);
const isNewValue = ref(false);

const filteredOptions = computed(() => {
  const value = typeof input.value === 'string' ? input.value.toLowerCase() : '';
  return props.options.filter((option) => option.toLowerCase().includes(value));
});

watch(input, () => {
  if (typeof input.value === 'string') {
    isNewValue.value = input.value.trim() !== '' && !props.options.includes(input.value.trim());
  }
});

watch(
  () => props.modelValue,
  (newValue, oldValue) => {
    if (newValue === undefined || newValue === null) {
      input.value = '';
      isSelected.value = false;
    } else if (newValue !== oldValue && newValue !== input.value) {
      input.value = newValue;
      isSelected.value = true;
    }
  },
  { immediate: true }
);

watch(input, (oldValue, newValue) => {
  if (oldValue.length < newValue.length) {
    isSelected.value = false;
  }
});

function selectItem(option: string) {
  emits('update:modelValue', option);
  input.value = option;
  showDropdown.value = false;
  isSelected.value = true;
}

function createNewValue() {
  if (isNewValue.value && typeof input.value === 'string') {
    emits('update:modelValue', input.value.trim());
    showDropdown.value = false;
    isSelected.value = true;
  }
}

function clearInput() {
  input.value = '';
  isSelected.value = false;
  emits('update:modelValue', undefined);
}

function handleClickOutside(event: MouseEvent) {
  if (componentRoot.value && !componentRoot.value.contains(event.target as Node)) {
    showDropdown.value = false;
  }
}

function highlightNext() {
  showDropdown.value = true;
  if (highlightedIndex.value < filteredOptions.value.length) {
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
    highlightedIndex.value = filteredOptions.value.length;
  }
}

function focusInput() {
  inputField.value?.focus();
}

function toggleDropdown() {
  showDropdown.value = !showDropdown.value;
  if (showDropdown.value) {
    inputField.value?.focus();
  }
}

function handleEsc() {
  showDropdown.value = false;
  inputField.value?.blur();
}

watch(highlightedIndex, (newIndex) => {
  const el = optionRefs.value[newIndex];
  if (el) {
    el.scrollIntoView({ block: 'nearest', behavior: 'smooth' });
  }
});

function preventSelectEvent(event: Event) {
  event.stopPropagation();
}

onMounted(() => {
  document.addEventListener('click', handleClickOutside);
});

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside);
});

defineExpose({ clearInput });
</script>

<template>
  <div ref="componentRoot" class="relative w-full" @click="focusInput">
    <div
      class="flex items-center px-4 py-2 border border-gray-300 rounded-lg cursor-pointer focus-within:ring-2 focus-within:ring-gray-300 transition-all"
      :class="{ 'border-green-500 bg-green-50': isSelected }"
    >
      <input
        :id="props.id"
        ref="inputField"
        class="w-full outline-none text-gray-700 placeholder-gray-500 bg-transparent"
        type="text"
        v-model="input"
        @select.stop="preventSelectEvent"
        placeholder="Choisir ou ajouter une valeur"
        @focus="showDropdown = true"
        @keydown.esc.stop.prevent="handleEsc"
        @keydown.enter.stop.prevent="createNewValue"
        @keydown.down.prevent="highlightNext"
        @keydown.up.prevent="highlightPrevious"
      />

      <FontAwesomeIcon
        v-if="input.length > 0"
        class="ml-2 text-gray-500 cursor-pointer"
        :icon="faTimes"
        @click.stop="clearInput"
      />
      <FontAwesomeIcon
        class="ml-2 text-gray-500 cursor-pointer"
        :icon="showDropdown ? faChevronUp : faChevronDown"
        @click.stop="toggleDropdown"
      />
    </div>

    <ul
      v-if="showDropdown && (filteredOptions.length > 0 || input.trim() !== '')"
      class="absolute mt-2 w-full max-h-40 overflow-y-auto bg-white border border-gray-300 rounded-lg shadow-lg z-10"
    >
      <li
        v-for="(option, index) in filteredOptions"
        :key="option"
        class="px-4 py-2 hover:bg-gray-100 cursor-pointer"
        @click="selectItem(option)"
        :class="{ 'bg-gray-200': index === highlightedIndex }"
        ref="optionRefs"
      >
        {{ option }}
      </li>

      <li
        v-if="isNewValue"
        class="px-4 py-2 bg-blue-100 text-blue-600 italic hover:bg-blue-200 cursor-pointer flex items-center space-x-2"
        @click="createNewValue"
        :class="{ 'bg-blue-300': highlightedIndex === filteredOptions.length }"
        ref="optionRefs"
      >
        <FontAwesomeIcon :icon="faPlus" />
        <span>Ajouter "{{ input }}"</span>
      </li>
    </ul>
  </div>
</template>
