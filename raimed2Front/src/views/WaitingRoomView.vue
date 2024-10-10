<script setup lang="ts">
import AuthenticatedViewLayout from '@/layouts/AuthenticatedViewLayout.vue';
import { useMutation, useQuery } from '@tanstack/vue-query';
import axiosInstance from '@/service/httpClient/axios.config';
import type { Diagnostic } from '@/models/diagnostic/diagnostic.model';
import type { VirtualPatient } from '@/models/virtual-patient/virtualPatient.model';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faPlay, faCircleInfo } from '@fortawesome/free-solid-svg-icons';
import { useRouter } from 'vue-router';
import { formatDate } from '@/helpers/date.helper';
import { DiagnosticStatus } from '@/models/diagnostic/diagnosticStatus.enum';
import ErrorAlert from '@/components/alert/ErrorAlert.vue';

const router = useRouter();

/**
 * Query to fetch all virtual patients
 */
const queryAllVirtualPatient = useQuery({
  queryKey: ['getAllVirtualPatient'],
  queryFn: () => axiosInstance.get<VirtualPatient[]>('/virtual-patient').then((res) => res.data),
  refetchOnWindowFocus: false,
  retry: false
});

/**
 * Query to fetch all diagnostic of the authenticated user
 */
const queryAllDiagnosticOfUser = useQuery({
  queryKey: ['getAllDiagnosticOfUser'],
  queryFn: () => axiosInstance.get<Diagnostic[]>('/diagnostic/user').then((res) => res.data),
  refetchOnWindowFocus: false,
  retry: false
});

/**
 * Mutation to start a diagnostic
 */
const mutationStartDiagnostic = useMutation({
  mutationFn: (virtualPatientId: string) =>
    axiosInstance.post('/diagnostic/start', { virtualPatientId }).then((res) => res.data),
  onSuccess: (data: Diagnostic) =>
    router.push({
      name: 'diagnostic',
      params: { virtualPatientId: data.virtualPatientId, diagnosticId: data.id }
    })
});

const getUserDiagnosticByVirtualPatientId = (virtualPatientId: string): Diagnostic | undefined => {
  return queryAllDiagnosticOfUser.data.value?.find(
    (diagnostic) => diagnostic.virtualPatientId === virtualPatientId
  );
};

const isDiagnosticInProgress = (virtualPatientId: string): boolean => {
  const diagnostic = getUserDiagnosticByVirtualPatientId(virtualPatientId);
  return diagnostic?.status === DiagnosticStatus.IN_PROGRESS;
};

const isDiagnosticSucess = (virtualPatientId: string): boolean => {
  const diagnostic = getUserDiagnosticByVirtualPatientId(virtualPatientId);
  return diagnostic?.status === DiagnosticStatus.SUCCESS;
};

const isDiagnosticFailure = (virtualPatientId: string): boolean => {
  const diagnostic = getUserDiagnosticByVirtualPatientId(virtualPatientId);
  return diagnostic?.status === DiagnosticStatus.FAILURE;
};

/**
 * Handle on click start diagnostic to mutate and start a diagnostic for the virtual patient
 * @param virtualPatientId The virtual patient id
 */
const handleOnClickStartDiagnostic = (virtualPatientId: string): void => {
  mutationStartDiagnostic.mutate(virtualPatientId);
};

/**
 * Handle on click diagnostic in progress to redirect to the diagnostic view
 * @param virtualPatientId The virtual patient id
 */
const handleOnClickDiagnosticInProgress = (virtualPatientId: string): void => {
  const diagnostic = getUserDiagnosticByVirtualPatientId(virtualPatientId);
  if (diagnostic) {
    router.push({ name: 'diagnostic', params: { diagnosticId: diagnostic.id, virtualPatientId } });
  }
};
</script>

<template>
  <AuthenticatedViewLayout title="Liste des patients">
    <div class="w-full h-full flex md:flex-row flex-col justify-evenly items-center">
      <div class="card lg:w-8/12 w-10/12 max-h-[80dvh] bg-base-100 shadow-xl overflow-auto">
        <table class="table table-md table-pin-rows">
          <!-- head -->
          <thead>
            <tr class="z-0 bg-base-200 text-sm">
              <th class="font-bold">Identifiant du patient</th>
              <th class="font-bold">Enseignant</th>
              <th class="font-bold">Date de création</th>
              <th class="font-bold text-center">Action</th>
            </tr>
          </thead>
          <!-- body -->
          <tbody>
            <!-- Query is fetching virtual patients -->
            <tr v-if="queryAllVirtualPatient.isLoading.value">
              <td colspan="4" class="text-center align-middle">
                <span class="loading loading-dots loading-sm"></span>
              </td>
            </tr>
            <!-- Query encountered an error fetching virtual patients -->
            <tr v-else-if="queryAllDiagnosticOfUser.isError.value">
              <td colspan="4">
                <ErrorAlert
                  message="Une erreur est survenue lors de la récupération des patients virtuels"
                />
              </td>
            </tr>
            <!-- Query successfully fetched virtual patients but no virtual patient found -->
            <tr v-else-if="!queryAllVirtualPatient.data.value?.length">
              <td colspan="4" class="text-center align-middle">
                <div role="alert" class="alert">
                  <FontAwesomeIcon :icon="faCircleInfo" class="w-4 h-4" />
                  <span>Aucun patient n'a été trouvé</span>
                </div>
              </td>
            </tr>
            <!-- Query successfully fetched virtual patients -->
            <tr
              v-else
              v-for="virtualPatient in queryAllVirtualPatient.data.value"
              v-bind:key="virtualPatient.id"
            >
              <td>{{ `#${virtualPatient.id}` }}</td>
              <td>{{ virtualPatient.createdBy }}</td>
              <td>{{ formatDate(virtualPatient.createdAt) }}</td>
              <td class="text-center">
                <button
                  v-if="isDiagnosticInProgress(virtualPatient.id)"
                  class="btn"
                  @click="() => handleOnClickDiagnosticInProgress(virtualPatient.id)"
                >
                  Diagnostic en cours
                  <FontAwesomeIcon :icon="faPlay" class="w-4 h-4" />
                </button>
                <span v-else-if="isDiagnosticSucess(virtualPatient.id)" class="badge badge-success">
                  Diagnostic réussi
                </span>
                <span v-else-if="isDiagnosticFailure(virtualPatient.id)" class="badge badge-error">
                  Diagnostic non réussi
                </span>
                <button
                  v-else
                  class="btn"
                  @click="() => handleOnClickStartDiagnostic(virtualPatient.id)"
                >
                  Réaliser le diagnostic
                  <FontAwesomeIcon :icon="faPlay" class="w-4 h-4" />
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </AuthenticatedViewLayout>
</template>
