<script setup lang="ts">
import { Color } from '@/models/new-patient/color.model';
import { faPenToSquare, faTrashCan } from '@fortawesome/free-regular-svg-icons';
import ActionButton from '@/components/actionButton/ActionButton.vue';
import GenericList from '@/components/modal/genericModal/GenericList.vue';
import ListenRow from '@/components/modal/listenModal/ListenRow.vue';
import type { Listen } from '@/models/listen/listen.model';

defineProps<{
  listens: Listen[];
}>();

defineEmits<{
  (e: 'deleteListen', id: string): void;
  (e: 'updateListen', id: string): void;
}>();

</script>

<template>
  <GenericList
    title="Liste des phrases"
    size="medium"
    :columns="{
      Phrases: 80,
      Actions: 20
    }"
  >
    <ListenRow v-for="(listen, index) in listens" :key="index" :listen="listen.content">
      <div class="w-[20%] flex justify-end gap-2">
        <ActionButton
          :color="Color.Grey"
          :icon="faPenToSquare"
          size="small"
          :on-click="() => $emit('updateListen', listen.id)"
        />
        <ActionButton
          :color="Color.Grey"
          :icon="faTrashCan"
          size="small"
          :on-click="() => $emit('deleteListen', listen.id)"
        />
      </div>
    </ListenRow>
  </GenericList>
</template>

<style scoped></style>
