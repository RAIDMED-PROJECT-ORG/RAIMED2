<script setup lang="ts">
import { getRoleDisplayName } from '@/models/auth/role.enum';
import { useAuthStore } from '@/stores/auth.store';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faArrowRightFromBracket, faChevronDown, faUser } from '@fortawesome/free-solid-svg-icons';
import { useRouter } from 'vue-router';

const authStore = useAuthStore();
const router = useRouter();

// Destructure user info from auth store
const { role, firstname, lastname } = authStore.getUserInfo;

// Get role display name from role enum value
const displayedRole = getRoleDisplayName(role);

// Logout user and redirect to login page
const handleLogout = () => authStore.logout().finally(() => router.push({ name: 'login' }));
</script>

<template>
  <div class="dropdown dropdown-bottom dropdown-end">
    <div tabindex="0" role="button" class="btn m-1 btn-primary text-white">
      <span>{{ (firstname ?? '') + ' ' + (lastname ?? '') }}</span>
      <span v-if="displayedRole"> - </span>
      <span v-if="displayedRole">{{ displayedRole }}</span>
      <FontAwesomeIcon :icon="faChevronDown" class="w-2 h-2" />
    </div>
    <ul
      tabindex="0"
      class="p-2 mx-1 shadow menu dropdown-content z-[5] bg-base-100 rounded-box w-52"
    >
      <li class="disabled">
        <a>
          <FontAwesomeIcon :icon="faUser" class="w-4 h-4" />
          Profil
        </a>
      </li>
      <li @click="handleLogout">
        <a>
          <FontAwesomeIcon :icon="faArrowRightFromBracket" class="w-4 h-4" />
          Déconnexion
        </a>
      </li>
    </ul>
  </div>
</template>
@/models/auth/role.enum
