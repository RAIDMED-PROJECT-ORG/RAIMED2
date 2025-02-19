<script setup lang="ts">

import NewPatientView from '@/views/teacher/NewPatientView.vue';
import { onMounted, ref } from 'vue';
import axiosInstance from '@/service/httpClient/axios.config';
import { useRoute } from 'vue-router';
import { HttpStatusCode } from 'axios';
import { useApiToLocalPatientMapper } from '@/composable/useApiToLocalPatientMapper';
import type { NewPatient } from '@/models/new-patient/newPatient.model';

const route = useRoute();
const patientMapper = useApiToLocalPatientMapper();

const patient = ref<NewPatient | null>(null)

onMounted(async () => {
  const res = await axiosInstance.get('/virtual-patient/' + route.params.id);
  if (res.status === HttpStatusCode.Ok) {
    patient.value = patientMapper.mapApiToLocal(res.data);
  } else {
    console.error('Error while fetching patient');
  }
});
</script>

<template>
  <NewPatientView v-if="patient !== null" :patient="patient"/>
</template>
<style scoped></style>
