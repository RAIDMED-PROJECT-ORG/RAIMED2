<script setup lang="ts">
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faUserCheck } from '@fortawesome/free-solid-svg-icons';
import type { Hypothesis } from '@/models/diagnostic/hypothesis.model';
import { useDiagnosticStore } from '@/stores/diagnostic.store';
import { ref } from 'vue';
import axiosInstance from '@/service/httpClient/axios.config';
import { useMutation } from '@tanstack/vue-query';
import ErrorAlert from '@/components/alert/ErrorAlert.vue';
import { useRouter } from 'vue-router';
import type { ValidateDiagnosticDto } from '@/models/diagnostic/validateDiagnosticDto';

const props = defineProps<{ diagnosticId: string }>();

const router = useRouter();

const diagnosticStore = useDiagnosticStore();

const showModal = ref(false);

const selectedHypothesis = ref<Hypothesis>();

const degreeOfCertainty = ref(50);

const openModal = () => {
  showModal.value = true;
};

const closeModal = () => {
  showModal.value = false;
  selectedHypothesis.value = undefined;
  degreeOfCertainty.value = 50;
  mutationValidateDiagnostic.reset();
};

// Add interpretation to diagnostic
const mutationValidateDiagnostic = useMutation({
  mutationFn: (validateDiagnosticDto: ValidateDiagnosticDto) =>
    axiosInstance
      .post(`/diagnostic/${props.diagnosticId}/validation`, validateDiagnosticDto)
      .then((res) => res.data),
  onSuccess: () =>
    router.push({ name: 'diagnosticResult', params: { diagnosticId: props.diagnosticId } })
});

const handleValidateDiagnostic = () => {
  if (selectedHypothesis.value) {
    mutationValidateDiagnostic.mutate({
      hypothesisId: selectedHypothesis.value.id,
      degreeOfCertainty: degreeOfCertainty.value
    });
  }
};
</script>

<template>
  <button
    class="btn btn-success text-white"
    :disabled="!diagnosticStore.getHypothesis.length"
    @click="openModal"
  >
    Valider le diagnostic
  </button>
  <dialog id="diagnostic-validation-modal" :class="`modal ${showModal ? 'modal-open' : ''}`">
    <div class="modal-box">
      <h3 class="font-bold text-lg">Validation de diagnostic</h3>

      <template v-if="mutationValidateDiagnostic.isError.value">
        <ErrorAlert
          class="my-4"
          message="Une erreur est survenue lors de la validation du diagnostic"
        />
      </template>

      <template v-else>
        <p class="py-4">
          Parmis vos hypothèses, quel diagnostic souhaitez-vous valider comme le bon diagnostic ?
        </p>
        <div class="flex justify-center flex-wrap">
          <button
            v-for="hypothesis in diagnosticStore.getHypothesis"
            v-bind:key="hypothesis.id"
            @click="selectedHypothesis = hypothesis"
            :class="`btn btn-info ${selectedHypothesis?.id === hypothesis.id && 'btn-active'} m-1`"
          >
            <FontAwesomeIcon
              v-if="selectedHypothesis?.id === hypothesis.id"
              :icon="faUserCheck"
              class="mr-2"
            />
            {{ hypothesis.value }}
          </button>
        </div>
        <p class="py-4">Entre 0 et 100, quel est votre degré de certitude pour ce diagnostic ?</p>
        <div class="flex flex-col justify-center items-center">
          <span class="py-4 text-4xl">{{ degreeOfCertainty }}</span>
          <input
            id="diagnostic-degree-of-certainty"
            type="range"
            min="0"
            max="100"
            v-model="degreeOfCertainty"
            class="range range-error range-sm w-3/4"
            :disabled="mutationValidateDiagnostic.isPending.value"
          />
        </div>
      </template>

      <div class="modal-action">
        <button
          v-if="!mutationValidateDiagnostic.isError.value"
          class="btn btn-success text-white"
          :disabled="!selectedHypothesis || mutationValidateDiagnostic.isPending.value"
          @click="handleValidateDiagnostic"
        >
          <span
            v-if="mutationValidateDiagnostic.isPending.value"
            class="loading loading-spinner"
          ></span>
          Valider
        </button>
        <button class="btn" @click="closeModal">Close</button>
      </div>
    </div>
  </dialog>
</template>
