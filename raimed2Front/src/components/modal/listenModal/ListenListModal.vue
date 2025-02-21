<script setup lang="ts">
import { Color } from '@/models/new-patient/color.model';
import { computed, ref } from 'vue';
import GenericModal from '@/components/modal/genericModal/GenericModal.vue';
import { getFakeListens, type Listen } from '@/models/listen/listen.model';
import ListenRow from '@/components/modal/listenModal/ListenRow.vue';
import ListenListModalHeader from '@/components/modal/listenModal/ListenListModalHeader.vue';

const props = defineProps<{
  selectedListens: Listen[];
}>();

const emits = defineEmits<{
  (e: 'addListens', listens: Listen[]): void;
  (e: 'switchModalVisibility', visibility: Boolean): void;
}>();

const allListens = ref<Listen[]>(getFakeListens());
const listensToAdd = ref<Listen[]>([]);
const filter = ref({
  nameFilter: ''
});

const validationLabel = computed(() => {
  const count = listensToAdd.value.length;
  if (count === 0) {
    return 'Fermer';
  }
  return `Ajouter ${count} phrase${count > 1 ? 's' : ''}`;
});

const listensToDisplay = computed(() => {
  return allListens.value.filter((listen) => {
    const nameMatch = listen.content
      .toLowerCase()
      .includes(filter.value.nameFilter.toLowerCase());
    return (
      nameMatch &&
      !props.selectedListens.some((selectedListen) => selectedListen.id === listen.id)
    );
  });
});

const switchIsSelected = (listen: Listen, isSelected: boolean) => {
  if (isSelected) {
    listensToAdd.value.push(listen);
  } else {
    listensToAdd.value = listensToAdd.value.filter((l) => l.id !== listen.id);
  }
};

const addListens = () => {
  emits('addListens', listensToAdd.value);
  emits('switchModalVisibility', false);
};
</script>

<template>
  <GenericModal
    title="Liste des phrases"
    :validationLabel="validationLabel"
    :headerColor="Color.Blue"
    :onValidation="addListens"
    :onBack="() => emits('switchModalVisibility', false)"
  >
    <div class="w-[70vw] h-[60vh] flex flex-col justify-start px-10">
      <ListenListModalHeader v-model="filter" />
      <div class="my-5 border-t border-light-grey"></div>
      <div class="pr-5 overflow-scroll">
        <div class="flex justify-between border-b border-1 border-[#9CA3AF] text-black font-bold">
          <h4 class="w-8/12">Phrase</h4>
          <h4 class="w-2/12 flex justify-center">Sélectionner</h4>
        </div>
        <ListenRow
          v-for="listen in listensToDisplay"
          :key="listen.id"
          :content="listen.content"
          :primary-element="listen.primaryElement || ''"
        >
          <div class="w-2/12 flex justify-center">
            <input
              type="checkbox"
              :checked="listensToAdd.includes(listen)"
              @change="
                (event) => switchIsSelected(listen, (event.target as HTMLInputElement).checked)
              "
            />
          </div>
        </ListenRow>
      </div>
    </div>
  </GenericModal>
</template>

<style scoped></style>
