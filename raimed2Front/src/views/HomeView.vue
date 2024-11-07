<script setup lang="ts">
import AuthenticatedPageLayout from '@/layouts/AuthenticatedViewLayout.vue';
import { useAuthStore } from '@/stores/auth.store';
import { useRouter } from 'vue-router';
import { Role } from '@/models/auth/role.enum';
import ActionButton from '@/components/actionButton/ActionButton.vue';
import { Color } from '@/models/new-patient/color.model';

const authStore = useAuthStore();
const router = useRouter();

// Destructure user info from auth store
const { role, firstname, lastname } = authStore.getUserInfo;
</script>
<template>
  <AuthenticatedPageLayout>
    <div class="w-full h-full flex flex-col justify-center items-center">
      <h1 class="title">Projet RAIMED-2</h1>
      <p class="text-center m-2" v-if="role == Role.TEACHER">
        Bonjour <strong>{{ firstname }} {{ lastname }} !</strong><br />
        En tant que professeur, vous pouvez gérer les cas de patients ou analyser les résultats de
        vos étudiants.
      </p>
      <p class="text-center m-2" v-else-if="role == Role.STUDENT">
        Bonjour <strong>{{ firstname }} {{ lastname }} ! </strong><br />
      </p>
      <p class="text">
        RAIMED est un <strong>outil pédagogique</strong> permettant aux étudiants d’interagir avec
        des patients virtuels, pour s’entrainer à identifier les éléments clés de la démarche
        <strong>diagnostique</strong>, de les regrouper en syndromes, d’émettre puis tester des
        hypothèses pour aboutir au diagnostic. Il permet aussi aux enseignants d’analyser le
        raisonnement des étudiants afin d’identifier les difficultés qu’ils ont pu avoir pour
        aboutir au diagnostic dans une situation clinique donnée.<br />
      </p>
      <p class="w-full text-center">A vous de jouer !</p>
      <div class="flex flex-col m-4 space-y-4" v-if="role == Role.TEACHER">
        <ActionButton :onClick="() => router.push({ name: 'teacherPatients' })" label="Visualisation des patients / Création de nouveaux patients" :color="Color.Orange"></ActionButton>
        <ActionButton :onClick="() => console.log('Not implemented yet')" label="Analyse des résultats des étudiants" :color="Color.Orange"></ActionButton>
      </div>
    </div>
  </AuthenticatedPageLayout>
</template>

<style>
.title {
  @apply text-2xl text-primary font-bold;
}
.text {
  @apply w-2/4 text-justify pt-3;
}
</style>
