<script setup lang="ts">
import { faMessage } from '@fortawesome/free-regular-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

const model = defineModel<{
  nameFilter: string;
}>({
  default: {
    nameFilter: ''
  }
});
</script>

<template>
  <div class="flex justify-around gap-5">
    <span class="flex items-center gap-3 w-3/5">
      <label for="listen-search" class="whitespace-nowrap font-bold">
        <FontAwesomeIcon :icon="faMessage" class="icon" />
        Recherche par nom
      </label>
      <input
        type="text"
        id="listen-search"
        class="text-input"
        placeholder="Insérer le nom..."
        aria-label="Nom de la phrase à chercher"
        v-model="model.nameFilter"
      />
    </span>
  </div>
</template>

<style scoped></style>
