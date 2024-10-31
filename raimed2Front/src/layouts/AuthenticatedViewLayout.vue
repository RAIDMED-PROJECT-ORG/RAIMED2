<script setup lang="ts">
import UserStatusButton from '@/components/userStatusButton/UserStatusButton.vue';

defineProps<{
  title?: string;
}>();
</script>
<template>
  <div class="w-full h-screen flex flex-col">
    <header
      class="min-h-[3.5rem] h-[5dvh] flex items-center"
      :class="title ? 'justify-between' : 'justify-end'"
    >
      <h1 v-if="title" class="m-2 ml-10 text-2xl font-bold truncate">{{ title }}</h1>
      <UserStatusButton />
    </header>
    <slot />
  </div>
</template>
