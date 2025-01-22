<script setup lang="ts">
import ActionButton from '@/components/actionButton/ActionButton.vue';
import { Color, type ColorType } from '@/models/new-patient/color.model';
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { onMounted, onUnmounted } from 'vue';

const props = defineProps<{
  title: string;
  headerColor: ColorType;
  validationLabel: string;
  onValidation: () => void;
  onBack: () => void;
  width?: string;
  height?: string;
}>();

function handleKeydown(event: KeyboardEvent) {
  if (event.key === 'Escape') {
    props.onBack();
    return;
  }
  if (event.key === 'Enter') {
    props.onValidation();
    return;
  }
}


onMounted(() => {
  window.addEventListener('keydown', handleKeydown);
});

onUnmounted(() => {
  window.removeEventListener('keydown', handleKeydown);
});



</script>

<template>
  <div class="fixed inset-0 flex items-center justify-center z-50">
    <div class="mx-auto bg-white rounded-2xl shadow-lg"
         :style="{ width: width || 'auto', height: height || 'auto' }"
    >
      <div class="flex flex-col items-center">
        <div
          class="w-full h-[50px] flex items-center justify-center py-3 rounded-t-2xl mb-6 font-semibold relative"
          :style="{ backgroundColor: headerColor }"
        >
          <FontAwesomeIcon
            :icon="faArrowLeft"
            class="h-4 w-4 absolute left-4 cursor-pointer"
            @click="onBack"
          />
          <h3 class="absolute left-1/2 transform -translate-x-1/2">{{ title }}</h3>
        </div>
        <div>
          <slot></slot>
        </div>
      </div>
      <footer class="flex justify-center pt-4 pb-3">
        <ActionButton
          :color="Color.Grey"
          size="medium"
          label="Retour"
          :onClick="onBack"
        />
        <ActionButton
          :color="Color.Green"
          size="medium"
          :label="validationLabel"
          :onClick="onValidation"
        />
      </footer>
    </div>
  </div>
</template>

<style scoped></style>
