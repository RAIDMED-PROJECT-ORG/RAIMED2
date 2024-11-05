<script setup lang="ts">
import ActionButton from '@/components/actionButton/ActionButton.vue';
import router from '@/router';
import { Color, type ColorType } from '@/models/new-patient/color.model';

defineProps<{
  title: string;
  headerColor: ColorType;
  validationLabel: string;
  onValidation: () => void;
}>();

const onBack = () => {
  router.back();
};

</script>

<template>
  <div>
    <header class="flex items-center justify-left relative w-full max-w-3xl p-4">
      <button @click="onBack" class="text-gray-600 hover:text-gray-800 mr-4 font-semibold">
        ← Retour
      </button>
      <h2 class="text-lg font-semibold absolute left-1/2 transform -translate-x-1/2">
        Nouveau patient
      </h2>
    </header>

    <div class="w-full max-w-3xl mx-auto bg-white rounded-2xl shadow-lg">
      <main class="flex flex-col items-center">
        <div
          class="w-full text-center py-3 rounded-t-2xl mb-6 font-semibold"
          :style="{ backgroundColor: headerColor }"
        >
          <h3>{{ title }}</h3>
        </div>
        <div class="p-6">
          <slot></slot>
        </div>
      </main>
      <footer class="flex justify-center pt-4 pb-3">
        <ActionButton
          :color="Color.Green"
          size="medium"
          :label="validationLabel"
          :onClick="
            () => {
              onValidation();
              router.back();
            }
          "
        />
      </footer>
    </div>
  </div>
</template>

<style scoped></style>
