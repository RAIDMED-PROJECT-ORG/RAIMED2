<script setup lang="ts">
import ErrorAlert from '@/components/alert/ErrorAlert.vue';
import AuthenticatedPageLayout from '@/layouts/AuthenticatedViewLayout.vue';
import type { Diagnostic } from '@/models/diagnostic/diagnostic.model';
import { DiagnosticStatus } from '@/models/diagnostic/diagnosticStatus.enum';
import axiosInstance from '@/service/httpClient/axios.config';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faCircleCheck, faCircleXmark } from '@fortawesome/free-solid-svg-icons';
import { useQuery } from '@tanstack/vue-query';
import { useRouter } from 'vue-router';
import { RouterLink } from 'vue-router';

const router = useRouter();

const diagnosticId = router.currentRoute.value.params.diagnosticId as string;

const queryDiagnostic = useQuery({
  queryKey: ['getDiagnostic'],
  queryFn: () =>
    axiosInstance.get<Diagnostic>(`/diagnostic/${diagnosticId}`).then((res) => res.data),
  refetchOnWindowFocus: false,
  retry: false
});

const queryDiagnosticResult = useQuery({
  queryKey: ['getDiagnosticResult'],
  queryFn: () =>
    axiosInstance
      .get<DiagnosticStatus>(`/diagnostic/${diagnosticId}/result`)
      .then((res) => res.data),
  refetchOnWindowFocus: false,
  retry: false
});
</script>

<template>
  <AuthenticatedPageLayout>
    <div class="w-full h-full flex flex-col justify-around items-center">
      <template v-if="queryDiagnostic.isLoading.value">
        <span class="loading loading-dots loading-lg"></span>
      </template>

      <template v-else-if="queryDiagnostic.isError.value">
        <div class="w-1/2">
          <ErrorAlert
            :message="`Une erreur est survenue lors de la récupération du résultat diagnostic #${diagnosticId}`"
          />
        </div>
      </template>

      <template v-else>
        <h1 class="text-3xl" v-if="queryDiagnostic.data.value?.status === DiagnosticStatus.SUCCESS">
          Bravo, vous avez posé le bon diagnostic !
        </h1>
        <h1
          class="text-3xl"
          v-else-if="queryDiagnostic.data.value?.status === DiagnosticStatus.FAILURE"
        >
          Dommage, vous n'avez pas posé le bon diagnostic.
        </h1>
        <div class="stats shadow">
          <div class="stat">
            <div class="stat-title">Résultat attendu</div>
            <div class="stat-value flex justify-center items-center">
              <FontAwesomeIcon
                v-if="queryDiagnostic.data.value?.status === DiagnosticStatus.SUCCESS"
                :icon="faCircleCheck"
                class="w-6 h-6 mr-4 text-success"
              />
              <FontAwesomeIcon
                v-else-if="queryDiagnostic.data.value?.status === DiagnosticStatus.FAILURE"
                :icon="faCircleXmark"
                class="w-6 h-6 mr-4 text-error"
              />
              {{ queryDiagnosticResult.data.value }}
            </div>
          </div>
        </div>
        <RouterLink class="btn btn-lg btn-primary text-white" :to="{ name: 'waitingRoom' }"
          >Retour à la liste des d'attente</RouterLink
        >
      </template>
    </div>
  </AuthenticatedPageLayout>
</template>
