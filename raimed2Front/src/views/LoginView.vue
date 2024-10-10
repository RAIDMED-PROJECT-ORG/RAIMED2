<script setup lang="ts">
import SigninForm from '@/components/loginForm/LoginForm.vue';
</script>

<template>
  <div class="w-full h-screen flex md:flex-row flex-col justify-evenly items-center">
    <div class="w-1/3 flex flex-col justify-center items-center hide-small-viewport">
      <img src="@/assets/logo-univ-nantes.png" alt="univ-nantes-logo" class="w-1/2" />
      <h1 class="text-2xl text-primary font-bold">Projet RAIMED-2</h1>
      <p class="w-full mt-4 text-justify">
        RAIMED est un <strong>outil pédagogique</strong> permettant aux étudiants d’interargir avec
        des patients virtuels, pour s’entrainer à identifier les éléments clés de la démarche
        <strong>diagnostique</strong>, de les regrouper en syndromes, d’émettre puis tester des
        hypothèses pour aboutir au diagnostic. Il permet aussi aux enseignants d’analyser le
        raisonnement des étudiants afin d’identifier les difficultés qu’ils ont pu avoir pour
        aboutir au diagnostic dans une situation clinique donnée.<br />
      </p>
      <p class="w-full text-center">A vous de jouer !</p>
    </div>
    <div id="divider" class="hide-small-viewport" />
    <SigninForm />
  </div>
</template>

<style scoped>
#divider {
  @apply w-0.5 h-1/3 bg-gray-300 hidden md:block;
}
.hide-small-viewport {
  @apply hidden md:block;
}
</style>
