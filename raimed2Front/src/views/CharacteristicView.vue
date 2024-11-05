<script setup lang="ts">
import ActionModal from '@/components/actionModal/ActionModal.vue';
import AuthenticatedPageLayout from '@/layouts/AuthenticatedViewLayout.vue';
import { Color } from '@/models/new-patient/color.model';
</script>

<template>
  <AuthenticatedPageLayout>
    <div class="w-full h-full flex flex-col justify-center items-center">
      <ActionModal
        title="Inspection"
        validationLabel="Valider"
        :onValidation="() => console.log('Validation')"
        :headerColor="Color.Red"
      >
        <p>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt
          ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation
          ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in
          reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur
          sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id
          est laborum.
        </p>
      </ActionModal>
    </div>
  </AuthenticatedPageLayout>
</template>

<style scoped></style>
