<script setup lang="ts">
import AuthenticatedPageLayout from '@/layouts/AuthenticatedViewLayout.vue';
</script>
<template>
  <AuthenticatedPageLayout>
    <div class="w-full h-full flex flex-col justify-center items-center">
      <h1 class="title">Projet RAIMED-2</h1>
      <p class="text">
        RAIMED est un <strong>outil pédagogique</strong> permettant aux étudiants d’interagir avec
        des patients virtuels, pour s’entrainer à identifier les éléments clés de la démarche
        <strong>diagnostique</strong>, de les regrouper en syndromes, d’émettre puis tester des
        hypothèses pour aboutir au diagnostic. Il permet aussi aux enseignants d’analyser le
        raisonnement des étudiants afin d’identifier les difficultés qu’ils ont pu avoir pour
        aboutir au diagnostic dans une situation clinique donnée.<br />
      </p>
      <p class="w-full text-center">A vous de jouer !</p>
      <h1 class="title mt-6">Comment fonctionne le site ?</h1>
      <p class="text">
        Sur votre gauche, vous pouvez retrouvez un menu en cliquant sur l’icône. Vous pouvez ensuite
        choisir un patient et faire un diagnostic.
      </p>
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
