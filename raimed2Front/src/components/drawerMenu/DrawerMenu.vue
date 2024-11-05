<script setup lang="ts">
import { ref } from 'vue';
import { RouterLink } from 'vue-router';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import {
  faBars,
  faCircleQuestion,
  faHospitalUser,
  faHouse, faListDots,
  faScrewdriverWrench,
  faSquarePollVertical,
  faXmark
} from '@fortawesome/free-solid-svg-icons';
import { useAuthStore } from '@/stores/auth.store';
import { Role } from '@/models/auth/role.enum';
import { storeToRefs } from 'pinia';

// declare a ref to hold the drawer toggle value that controls the drawer menu
const isDrawerOpened = ref(false);

const { getUserRole } = storeToRefs(useAuthStore());

// allow to close the drawer menu by the ref previously declared
const closeDrawerMenu = () => (isDrawerOpened.value = false);
</script>
<template>
  <div class="drawer">
    <!-- Drawer toggle which is an hidden checkbox that controls the drawer menu -->
    <input id="raimed-drawer" type="checkbox" class="drawer-toggle" v-model="isDrawerOpened" />
    <div class="drawer-content flex flex-row min-h-screen">
      <!-- Sidebar that is displayed when the drawer is closed -->
      <aside class="w-14 h-full bg-base-100 shadow-lg p-1 flex justify-center">
        <label for="raimed-drawer" class="btn btn-circle btn-primary drawer-button w-12 h-12">
          <FontAwesomeIcon :icon="faBars" class="w-5 h-5" />
        </label>
      </aside>
      <!-- Page content goes here, represented by a slot element -->
      <slot />
    </div>
    <div class="drawer-side">
      <label for="raimed-drawer" aria-label="close sidebar" class="drawer-overlay" />
      <div class="menu w-80 min-h-full bg-base-200 px-4 text-base-content">
        <!-- Drawer content goes here -->
        <div class="flex justify-end">
          <button class="btn btn-ghost btn-circle" @click="closeDrawerMenu">
            <FontAwesomeIcon :icon="faXmark" class="w-5 h-5" />
          </button>
        </div>
        <div class="flex flex-col justify-center items-center">
          <img src="@/assets/logo-univ-nantes.png" alt="univ-nantes-logo" class="w-10/12" />
          <h1 class="text-2xl font-bold text-primary text-center">Projet RAIMED-2</h1>
        </div>
        <ul class="menu mt-5">
          <li>
            <RouterLink :to="{ name: 'home' }" @click="closeDrawerMenu">
              <FontAwesomeIcon :icon="faHouse" class="w-4 h-4" />
              Accueil
            </RouterLink>
          </li>
          <li v-if="getUserRole && [Role.STUDENT].includes(getUserRole)">
            <RouterLink :to="{ name: 'waitingRoom' }" @click="closeDrawerMenu">
              <FontAwesomeIcon :icon="faHospitalUser" class="w-4 h-4" />
              Patients
            </RouterLink>
          </li>
          <li v-if="getUserRole && [Role.STUDENT].includes(getUserRole)">
            <RouterLink :to="{ name: 'studentResults' }" @click="closeDrawerMenu">
              <FontAwesomeIcon :icon="faSquarePollVertical" class="w-4 h-4" />
              Vos résultats
            </RouterLink>
          </li>
          <li v-if="getUserRole && [Role.TEACHER].includes(getUserRole)">
            <RouterLink :to="{ name: 'teacherPatients' }" @click="closeDrawerMenu">
              <FontAwesomeIcon :icon="faListDots" class="w-4 h-4" />
              Visualisation des patients
            </RouterLink>
          </li>
          <li v-if="getUserRole && [Role.ADMIN].includes(getUserRole)">
            <details>
              <summary>
                <FontAwesomeIcon :icon="faScrewdriverWrench" class="w-4 h-4" />
                Administration
              </summary>
              <ul>
                <li>
                  <RouterLink :to="{ name: 'questions' }" @click="closeDrawerMenu">
                    <FontAwesomeIcon :icon="faCircleQuestion" class="w-4 h-4" />
                    Catalogue de questions
                  </RouterLink>
                </li>
              </ul>
            </details>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>
@/models/auth/role.enum
