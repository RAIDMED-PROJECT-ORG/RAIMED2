<script setup lang="ts">
import { ref } from 'vue';
import { useAuthStore } from '@/stores/auth.store';
import { useRouter } from 'vue-router';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faArrowRightLong } from '@fortawesome/free-solid-svg-icons';
import ErrorAlert from '../alert/ErrorAlert.vue';

const authStore = useAuthStore();

const router = useRouter();

const credentials = ref({
  username: '',
  password: ''
});

const isLoadingSignIn = ref(false);
const isErrorSignIn = ref(false);

const handleOnSubmit = (event: Event) => {
  event.preventDefault();
  isLoadingSignIn.value = true;
  isErrorSignIn.value = false;
  authStore
    .login(credentials.value.username, credentials.value.password)
    // Redirect to home page if login success
    .then(() => router.push({ name: 'home' }))
    // Display error message if login failed
    .catch(() => {
      isErrorSignIn.value = true;
    })
    // Reset loading state and credentials in all cases
    .finally(() => {
      isLoadingSignIn.value = false;
      credentials.value = { username: '', password: '' };
    });
};
</script>

<template>
  <div class="md:w-1/4 w-1/2">
    <h1 class="text-2xl text-black font-bold">Se connecter</h1>
    <form @submit="handleOnSubmit" class="w-full mt-4">
      <ErrorAlert
        v-if="isErrorSignIn"
        :message="'Identifiants incorrects. Veuillez vérifier et réessayer.'"
      />
      <div class="form-control w-full">
        <label class="label" for="username">
          <span class="label-text">Nom d'utilisateur</span>
        </label>
        <input
          id="username"
          type="text"
          autocomplete="on"
          placeholder="E123456K"
          class="input input-bordered w-full max-w-sm"
          required
          :disabled="isLoadingSignIn"
          v-model="credentials.username"
        />
      </div>
      <div class="form-control w-full mt-2">
        <label class="label" for="password">
          <span class="label-text">Mot de passe</span>
        </label>
        <input
          id="password"
          type="password"
          autocomplete="on"
          class="input input-bordered w-full max-w-sm"
          required
          :disabled="isLoadingSignIn"
          v-model="credentials.password"
        />
      </div>
      <button
        type="submit"
        class="xl:w-1/2 lg:w-8/12 md:w-10/12 w-full btn btn-primary mt-6 text-white"
        :disabled="isLoadingSignIn"
      >
        <template v-if="isLoadingSignIn"><span class="loading loading-spinner"></span></template>
        <template v-else
          >Connexion <FontAwesomeIcon :icon="faArrowRightLong" class="w-4 h-4"
        /></template>
      </button>
    </form>
  </div>
</template>
