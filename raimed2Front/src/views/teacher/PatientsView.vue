<script setup lang="ts">
import AuthenticatedPageLayout from '@/layouts/AuthenticatedViewLayout.vue';
import ActionButton from '@/components/actionButton/ActionButton.vue';
import { faEye, faPen, faTrashCan } from '@fortawesome/free-solid-svg-icons';
import { usePatientStore } from '@/stores/patient.store';
import type { VirtualPatient } from '@/models/virtual-patient/virtualPatient.model';
import { onMounted, ref } from 'vue';
import router from '@/router';
import { Color } from '@/models/new-patient/color.model';

const patientStore = usePatientStore();
const patients = ref<VirtualPatient[]>([]);

onMounted(async () => {
  await refreshPatients();
});


const getSexLabel = (sex: 'MALE' | 'FEMALE'): string => (sex === 'MALE' ? 'Homme' : 'Femme');

const deletePatient = async (id: string | undefined) => {
  if (id === undefined) return;
  const success = await patientStore.deleteVirtualPatient(id);
  if (success) {
    await refreshPatients();
  }
}

const refreshPatients = async () => {
  await patientStore.init();
  patients.value = patientStore.getVirtualPatients;
}

</script>

<template>
  <AuthenticatedPageLayout>
    <div class="ml-10 min-w-2/4 max-w-[800px] mr-10 flex flex-col h-[90%]">
      <div class="flex justify-between items-center h-[5%] mb-5">
        <h1 class="font-extrabold text-xl">Visualisation des patients</h1>
        <ActionButton
          :color="Color.Green"
          :onClick="() => router.push({ name: 'newPatient' })"
          label="Créer un patient"
        />
      </div>
      <div class="bg-white rounded-2xl px-4 h-[90%] overflow-auto">
        <table class="w-full border-collapse">
          <thead class="border-b-[1px] border-dark-grey-border">
            <tr>
              <th class="font-bold py-3">Sexe</th>
              <th class="font-bold py-3">Âge</th>
              <th class="font-bold py-3">Diagnostic final</th>
              <th class="font-bold py-3">Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="(patient, index) in patients"
              :key="index"
              :class="{ 'border-b-[1px] border-light-grey-border': index !== patients.length - 1 }"
              class="text-center"
            >
              <td class="py-2">{{ patient.gender ? getSexLabel(patient.gender) : '' }}</td>
              <td class="py-2">{{ patient.age }}</td>
              <td class="py-2">{{ patient.result }}</td>
              <td class="py-2 flex justify-center gap-2">
                <ActionButton
                  v-if="!patient.hasDiagnostic"
                  :color="Color.Grey"
                  :icon="faPen"
                  :onClick="() => router.push({ name: 'editPatient', params: { id: patient.id } })"
                  :iconOnly="true"
                  :size="'small'"
                />
                <ActionButton
                  v-if="!patient.hasDiagnostic"
                  :color="Color.Grey"
                  :icon="faTrashCan"
                  :onClick="() => deletePatient(patient.id)"
                  :size="'small'"
                />
                <ActionButton
                  v-if="patient.hasDiagnostic"
                  :color="Color.Grey"
                  :icon="faEye"
                  :onClick="() => router.push({ name: 'editPatient', params: { id: patient.id } })"
                  :iconOnly="true"
                  :size="'small'"
                />
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </AuthenticatedPageLayout>
</template>

<style scoped>
</style>
