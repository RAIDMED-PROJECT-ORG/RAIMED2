<script setup lang="ts">
import { Color } from '@/models/new-patient/color.model';
import GenericModal from '@/components/modal/genericModal/GenericModal.vue';
import { ref } from 'vue';
import ListenList from '@/components/modal/listenModal/ListenList.vue';
import ListenForm from '@/components/modal/listenModal/ListenForm.vue';
import type { Listen } from '@/models/listen/listen.model';

const props = defineProps<{
  listens: Listen[];
  onValidation: (data: Listen[]) => void;
  onBack: () => void;
}>();

const listens = ref<Listen[]>(props.listens);

const listenToUpdate = ref<Listen | null>(null);

const deleteListen = (id: string) => {
  listens.value = listens.value.filter((listen) => listen.id !== id);
};

const updateListen = (id: string) => {
  listenToUpdate.value = listens.value.find((listen) => listen.id === id) ?? null;
};

const insertOrUpdateListen = (listen: Listen) => {
  const index = listens.value.findIndex((l) => l.id === listen.id);
  if (index === -1) {
    listens.value.push(listen);
  } else {
    listens.value[index] = listen;
    listenToUpdate.value = null;
  }
};

const insertListen = (newListens: Listen[]) => {
  console.log(newListens);
  listens.value.push(...newListens);
};

const handleValidation = () => {
  props.onValidation(listens.value);
};
</script>

<template>
  <GenericModal
    title="Phrases"
    validationLabel="Valider"
    :onValidation="handleValidation"
    :headerColor="Color.Blue"
    :onBack="onBack"
    :confirmGoBack="true"
  >
    <div class="w-[70vw] h-[60vh] flex px-10">
      <ListenList :listens="listens" @delete-listen="deleteListen" @update-listen="updateListen" />
      <ListenForm
        :listens="listens"
        :listenToUpdate="listenToUpdate"
        @add-listen="insertOrUpdateListen"
        @add-listens="insertListen"
      />
    </div>
  </GenericModal>
</template>

<style scoped></style>
