<script setup lang="ts">
import AuthenticatedPageLayout from '@/layouts/AuthenticatedViewLayout.vue';

interface Patient {
  id: number;
  sex: 'MALE' | 'FEMALE';
  age: number;
  diagnosis: string;
}

const patients: Patient[] = [
  { id: 1, sex: 'MALE', age: 15, diagnosis: 'Schizophrenia' },
  { id: 2, sex: 'MALE', age: 25, diagnosis: 'Dépression' },
  { id: 3, sex: 'FEMALE', age: 35, diagnosis: 'Cancer' }
];

const getSexLabel = (sex: 'MALE' | 'FEMALE'): string => (sex === 'MALE' ? 'Homme' : 'Femme');
</script>

<template>
  <AuthenticatedPageLayout>
    <div class="ml-10">
      <h1 class="font-extrabold text-xl mb-2">Liste des patients</h1>
      <div class="bg-white w-2/4 rounded-2xl">
        <table class="w-full">
          <thead class="border-b-[1px] border-dark-gray-border">
            <tr>
              <th class="font-bold py-4">Id</th>
              <th class="font-bold py-4">Sexe</th>
              <th class="font-bold py-4">Âge</th>
              <th class="font-bold py-4">Diagnostic final</th>
              <th class="font-bold py-4">Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="patient in patients" :key="patient.id" class="border-b-[1px] border-light-gray-border">
              <td class="text-center py-4">{{ patient.id }}</td>
              <td class="text-center py-4">{{ getSexLabel(patient.sex) }}</td>
              <td class="text-center py-4">{{ patient.age }}</td>
              <td class="text-center py-4">{{ patient.diagnosis }}</td>
              <td class="py-4 text-center">
                <button
                  class="bg-blue-500 text-white px-4 py-2 rounded-2xl hover:bg-blue-600 focus:outline-none"
                >
                  Voir
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </AuthenticatedPageLayout>
</template>

<style scoped>
/* Ajoutez ici des styles si nécessaire */
</style>
