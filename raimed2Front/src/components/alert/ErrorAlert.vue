<script setup lang="ts">
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faCircleXmark } from '@fortawesome/free-solid-svg-icons';
defineProps<{ message: string }>();
</script>

<template>
  <div role="alert" class="alert">
    <FontAwesomeIcon :icon="faCircleXmark" class="w-6 h-6 text-error" />
    <span>{{ message }}</span>
  </div>
</template>
