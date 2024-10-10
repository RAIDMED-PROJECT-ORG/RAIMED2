<script setup lang="ts">
import { useAuthStore } from './stores/auth.store';
import LoginView from '@/views/LoginView.vue';
import DrawerMenu from '@/components/drawerMenu/DrawerMenu.vue';
import RaimedLoader from '@/components/loader/RaimedLoader.vue';
const authStore = useAuthStore();
import { storeToRefs } from 'pinia';
const { isCurrentUserAuthenticated } = storeToRefs(authStore);
</script>

<template>
  <template v-if="!authStore.isInitialized"><RaimedLoader /></template>
  <template v-else-if="isCurrentUserAuthenticated">
    <DrawerMenu>
      <RouterView />
    </DrawerMenu>
  </template>
  <template v-else>
    <LoginView />
  </template>
</template>
