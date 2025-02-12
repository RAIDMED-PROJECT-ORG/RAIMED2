<script setup lang="ts">
import ActionButton from '@/components/actionButton/ActionButton.vue';
import { faCirclePlus, faSearch } from '@fortawesome/free-solid-svg-icons';
import { Color } from '@/models/new-patient/color.model';

defineProps<{
  title: string;
  buttonLabel: string;
  importTitle?: string | null;
  importButtonLabel?: string | null;
}>();

const emits = defineEmits<{
  (e: 'onsubmit'): void;
  (e: 'openModal'): void;
}>();

const submitForm = (event: SubmitEvent) => {
  event.preventDefault();
  emits('onsubmit');
};
</script>

<template>
  <div class="flex flex-col pl-10 overflow-y-auto w-3/6">
    <h3 class="self-center text-black font-bold text-xl mb-3">{{ title }}</h3>

    <form class="flex flex-col gap-3" @submit="submitForm">
      <slot />
      <ActionButton
        type="submit"
        :label="buttonLabel"
        :icon="faCirclePlus"
        :color="Color.Grey"
        class="w-4/5 self-center drop-shadow-sm"
      />
    </form>

    <div v-if="importTitle === null || importButtonLabel === null" class="flex items-center my-4">
      <div class="flex-grow border-t border-light-grey"></div>
      <span class="mx-2 text-light-grey">ou</span>
      <div class="flex-grow border-t border-light-grey"></div>
    </div>

    <div
      v-if="importTitle === null || importButtonLabel === null"
      class="flex flex-col items-center"
    >
      <h3 class="text-black font-bold text-xl mb-3">{{ importTitle }}</h3>
      <ActionButton
        :label="importButtonLabel ?? ''"
        :icon="faSearch"
        :color="Color.Grey"
        class="w-11/12 self-center drop-shadow-sm"
        :on-click="() => $emit('openModal')"
      />
    </div>
  </div>
</template>

<style scoped></style>
