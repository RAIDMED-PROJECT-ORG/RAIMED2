<script setup lang="ts">
import { ref, watch } from 'vue';
import GenericForm from '@/components/modal/genericModal/GenericForm.vue';
import { faMessage } from '@fortawesome/free-regular-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import type { Listen } from '@/models/listen/listen.model';
import { v4 as uuidv4 } from 'uuid';
import ListenListModal from '@/components/modal/listenModal/ListenListModal.vue';

const listenValue = ref<string>('');
const isModalOpen = ref<boolean>(false);

const props = defineProps<{
  listenToUpdate?: Listen | null;
  listens: Listen[];
}>();

const emits = defineEmits<{
  (e: 'addListen', listen: Listen): void;
  (e: 'addListens', listens: Listen[]): void;
}>();

const submitForm = () => {
  emits('addListen', {
    id: props.listenToUpdate ? props.listenToUpdate.id : uuidv4(),
    content: listenValue.value
  });

  listenValue.value = '';
};

watch(
  () => props.listenToUpdate,
  (listen) => {
    if (listen) {
      listenValue.value = listen.content;
    }
  }
);

const switchModalVisibility = () => {
  isModalOpen.value = !isModalOpen.value;
};
</script>

<template>
  <GenericForm
    title="Ajouter une phrase"
    :button-label="listenToUpdate ? 'Mettre à jour la phrase' : 'Créer une phrase'"
    import-title="Importer une phrase"
    import-button-label="Importer une phrase"
    @onsubmit="() => submitForm()"
    @open-modal="() => switchModalVisibility()"
  >
    <div class="form-group">
      <label for="listen" class="font-bold">
        <FontAwesomeIcon :icon="faMessage" class="icon" />
        Phrase*
      </label>
      <input
        type="text"
        id="listen"
        class="text-input"
        placeholder="Insérer la phrase à ajouter..."
        v-model="listenValue"
        aria-label="Texte de la phrase"
        required
      />
    </div>
  </GenericForm>
  <ListenListModal
    v-if="isModalOpen"
    :selected-listens="listens"
    @add-listens="listensToAdd => emits('addListens', listensToAdd)"
    @switch-modal-visibility="switchModalVisibility"
  />
</template>

<style scoped></style>
