<script setup lang="ts">
import AuthenticatedPageLayout from '@/layouts/AuthenticatedViewLayout.vue';
import ActionButton, { Color } from '@/components/actionButton/ActionButton.vue';
import { faPen, faTrashCan } from '@fortawesome/free-solid-svg-icons';

interface Patient {
  id: number;
  sex: 'MALE' | 'FEMALE';
  age: number;
  diagnosis: string;
}

const patients: Patient[] = [
  { id: 1, sex: 'MALE', age: 15, diagnosis: 'Schizophrenia' },
  { id: 2, sex: 'MALE', age: 25, diagnosis: 'Dépression' },
  { id: 3, sex: 'FEMALE', age: 35, diagnosis: 'Cancer' },
  { id: 1, sex: 'MALE', age: 15, diagnosis: 'Schizophrenia' },
  { id: 2, sex: 'MALE', age: 25, diagnosis: 'Dépression' },
  { id: 3, sex: 'FEMALE', age: 35, diagnosis: 'Cancer' },
  { id: 1, sex: 'MALE', age: 15, diagnosis: 'Schizophrenia' },
  { id: 2, sex: 'MALE', age: 25, diagnosis: 'Dépression' },
  { id: 3, sex: 'FEMALE', age: 35, diagnosis: 'Cancer' },
  { id: 1, sex: 'MALE', age: 15, diagnosis: 'Schizophrenia' },
  { id: 2, sex: 'MALE', age: 25, diagnosis: 'Dépression' },
  { id: 3, sex: 'FEMALE', age: 35, diagnosis: 'Cancer' },
  { id: 1, sex: 'MALE', age: 15, diagnosis: 'Schizophrenia' },
  { id: 2, sex: 'MALE', age: 25, diagnosis: 'Dépression' },
  { id: 3, sex: 'FEMALE', age: 35, diagnosis: 'Cancer' }
];

const getSexLabel = (sex: 'MALE' | 'FEMALE'): string => (sex === 'MALE' ? 'Homme' : 'Femme');
</script>

<template>
  <AuthenticatedPageLayout>
    <div class="ml-10 w-2/4 flex flex-col h-[90%]">
      <div class="flex justify-between items-center h-[5%] mb-5">
        <h1 class="font-extrabold text-xl">Visualisation des patients</h1>
        <ActionButton
          :color="Color.Green"
          :onClick="() => console.log('click')"
          label="Créer un patient"
        />
      </div>
      <div class="bg-white rounded-2xl px-4 h-[90%] overflow-auto">
        <table class="w-full border-collapse">
          <thead class="border-b-[1px] border-dark-grey-border">
            <tr>
              <th class="font-bold py-3">Id</th>
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
              <td class="py-2">{{ patient.id }}</td>
              <td class="py-2">{{ getSexLabel(patient.sex) }}</td>
              <td class="py-2">{{ patient.age }}</td>
              <td class="py-2">{{ patient.diagnosis }}</td>
              <td class="py-2 flex justify-center gap-2">
                <ActionButton
                  :color="Color.Grey"
                  :icon="faPen"
                  :onClick="() => console.log('click')"
                  :iconOnly="true"
                  :size="'small'"
                />
                <ActionButton
                  :color="Color.Grey"
                  :icon="faTrashCan"
                  :onClick="() => console.log('click')"
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
/* Ajoutez ici des styles si nécessaire */
</style>
