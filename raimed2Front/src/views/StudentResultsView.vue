<script setup lang="ts">
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faPersonDigging } from '@fortawesome/free-solid-svg-icons';
</script>

<template>
  <div class="w-full h-full flex justify-center items-center">
    <div role="alert" class="w-1/2 alert text-blue-500">
      <FontAwesomeIcon :icon="faPersonDigging" class="w-4 h-4" />
      <span>En cours de construction</span>
    </div>
  </div>
</template>
