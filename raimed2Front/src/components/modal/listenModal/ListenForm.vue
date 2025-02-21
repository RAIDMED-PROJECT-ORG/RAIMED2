<script setup lang="ts">
import { computed, nextTick, ref, watch } from 'vue';
import GenericForm from '@/components/modal/genericModal/GenericForm.vue';
import { faBookmark, faMessage } from '@fortawesome/free-regular-svg-icons';
import { v4 as uuidv4 } from 'uuid';
import ListenListModal from '@/components/modal/listenModal/ListenListModal.vue';
import IconLabel from '@/components/iconLabel/IconLabel.vue';
import SingleValueSelector from '@/components/singleValueSelector/SingleValueSelector.vue';
import type { Listen } from '@/models/listen/listen.model';

const listenValue = ref<string>('');
const isModalOpen = ref<boolean>(false);
const primaryElementValue = ref<string | undefined>(undefined);
const singleSelector = ref<InstanceType<typeof SingleValueSelector> | null>(null);

const props = defineProps<{
  listenToUpdate?: Listen | null;
  listens: Listen[];
}>();

const primaryElements = computed(() =>
  Array.from(
    new Set(
      props.listens
        .filter((listen) => listen.primaryElement != null)
        .map((listen) => listen.primaryElement as string)
    )
  )
);

const emits = defineEmits<{
  (e: 'addListen', listen: Listen): void;
  (e: 'addListens', listens: Listen[]): void;
}>();

const submitForm = () => {
  emits('addListen', {
    id: props.listenToUpdate ? props.listenToUpdate.id : uuidv4(),
    content: listenValue.value,
    primaryElement: primaryElementValue.value
  });

  listenValue.value = '';
  singleSelector.value?.clearInput();
};

watch(
  () => props.listenToUpdate,
  (listen) => {
    if (listen) {
      listenValue.value = listen.content;

      // This is a workaround to update the SingleValueSelector value
      primaryElementValue.value = undefined;
      nextTick(() => {
        primaryElementValue.value = props.listenToUpdate?.primaryElement || '';
      });
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
      <IconLabel :icon="faMessage" text="Phrase*" for="listen" />
      <input
        type="text"
        id="listen"
        class="text-input"
        :class="{ 'text-input--filled': listenValue !== '' }"
        placeholder="Insérer la phrase à ajouter..."
        v-model="listenValue"
        aria-label="Texte de la phrase"
        required
      />
    </div>
    <div class="form-group">
      <IconLabel for="primaryElement" :icon="faBookmark" text="Élément primaire" />
      <SingleValueSelector
        ref="singleSelector"
        id="primaryElement"
        v-model="primaryElementValue"
        :options="primaryElements"
      />
    </div>
  </GenericForm>
  <ListenListModal
    v-if="isModalOpen"
    :selected-listens="listens"
    @add-listens="(listensToAdd) => emits('addListens', listensToAdd)"
    @switch-modal-visibility="switchModalVisibility"
  />
</template>

<style scoped></style>
