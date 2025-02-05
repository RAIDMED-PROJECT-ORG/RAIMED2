<script setup lang="ts">
import ActionButton from '@/components/actionButton/ActionButton.vue';
import { Color, type ColorType } from '@/models/new-patient/color.model';
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { onMounted, onUnmounted, ref } from 'vue';

const props = defineProps<{
  title: string;
  headerColor: ColorType;
  validationLabel: string;
  onValidation: () => void;
  onBack: () => void;
  confirmGoBack?: boolean;
  width?: string;
  height?: string;
}>();

const showConfirmGoBackModal = ref<boolean>(false);

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

function handleSwitchConfirmGoBackModalVisibility() {
  showConfirmGoBackModal.value = !showConfirmGoBackModal.value;
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
    <!-- Backdrop -->
    <div
      v-if="showConfirmGoBackModal"
      class="fixed inset-0 bg-opacity-50 z-40"
      @click.stop
    ></div>

    <!-- Main modal -->
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
            :onClick="confirmGoBack ? handleSwitchConfirmGoBackModalVisibility : onBack"
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
          :onClick="confirmGoBack ? handleSwitchConfirmGoBackModalVisibility : onBack"
        />
        <ActionButton
          :color="Color.Green"
          size="medium"
          :label="validationLabel"
          :onClick="onValidation"
        />
      </footer>
    </div>

    <!-- Confirm go back modal -->
    <div v-if="showConfirmGoBackModal" class="absolute mx-auto bg-white rounded-2xl shadow-lg z-50">
      <div class="flex flex-col items-center">
        <div
          class="w-full h-[50px] flex items-center justify-center py-3 rounded-t-2xl mb-6 font-semibold relative"
          :style="{ backgroundColor: headerColor }"
        >
          <FontAwesomeIcon
            :icon="faArrowLeft"
            class="h-4 w-4 absolute left-4 cursor-pointer"
            :onClick="handleSwitchConfirmGoBackModalVisibility"
          />
          <h3 class="absolute left-1/2 transform -translate-x-1/2">Annulation</h3>
        </div>
        <div>
          <p class="mx-5 text-base">Des modifications n'ont pas été sauvegardées.</p>
          <p class="text-base text-center">Êtes-vous sûr de vouloir les supprimer ?</p>
        </div>
      </div>
      <footer class="flex justify-center pt-4 pb-3">
        <ActionButton
          :color="Color.Grey"
          size="medium"
          label="Continuer l'édition"
          :onClick="handleSwitchConfirmGoBackModalVisibility"
        />
        <ActionButton
          :color="Color.Red"
          size="medium"
          label="Oui, quitter"
          :onClick="onBack"
        />
      </footer>
    </div>
  </div>
</template>

<style scoped></style>
