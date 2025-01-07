<script setup lang="ts">
import { ref } from 'vue';
import GenericModal from '@/components/modal/genericModal/GenericModal.vue';
import MultipleSelector from '@/components/multipleSelector/MultipleSelector.vue';
import ActionButton from '@/components/actionButton/ActionButton.vue';
import { faPen, faTrashCan } from '@fortawesome/free-solid-svg-icons';
import { Color } from '@/models/new-patient/color.model';

type InspectionSign = string;

interface InspectionResult {
  zone: string;
  signs: InspectionSign[];
}

const props = defineProps<{
  onValidation: (inspectionResults: InspectionResult[]) => void;
  onBack: () => void;
}>();

const inspectionResults = ref<InspectionResult[]>([]);

const availableZones = [
  'Visage',
  'Bras droit',
  'Bras gauche',
  'Jambes',
  'Tronc',
  'Dos',
];

const allPossibleSigns = [
  'Angiome plan',
  'Anhydrose',
  'Lésion cutanée',
  'Cicatrice',
  'Œdème',
  // ...
];

const selectedZone = ref('Visage');
const selectedSigns = ref<InspectionSign[]>([]);

const iconEdit = faPen;
const iconDelete = faTrashCan;

function handleAddInspection() {
  if (!selectedZone.value || selectedSigns.value.length === 0) {
    return;
  }

  if (inspectionResults.value.some((item) => item.zone === selectedZone.value)) {
    alert('Cette zone est déjà renseignée !');
    return;
  }

  inspectionResults.value.push({
    zone: selectedZone.value,
    signs: [...selectedSigns.value],
  });

  // Reset
  selectedZone.value = 'Visage';
  selectedSigns.value = [];
}

function handleDeleteInspection(index: number) {
  inspectionResults.value.splice(index, 1);
}

function handleEditInspection(index: number) {
  const row = inspectionResults.value[index];
  selectedZone.value = row.zone;
  selectedSigns.value = [...row.signs];
  inspectionResults.value.splice(index, 1);
}

function handleValidation() {
  props.onValidation(inspectionResults.value);
}
</script>

<template>
  <GenericModal
    title="Inspection"
    validationLabel="Valider"
    :headerColor="Color.Orange"
    :onValidation="handleValidation"
    :onBack="props.onBack"
    width="800px"
  >
    <div class="flex w-full h-full gap-6">
      <div class="w-1/2 flex flex-col items-center">
        <h2 class="text-lg font-semibold mb-4">Sélectionner une zone</h2>

        <div class="flex justify-center mb-6">
          <img
            src="@/assets/human-body.jpg"
            alt="Silhouette"
            class="h-64 object-contain"
          />
        </div>

        <div class="bg-white border border-gray-200 rounded-md p-4 shadow-sm w-[300px]">
          <div class="mb-4">
            <label class="block font-semibold text-sm mb-1"
            >Zone sélectionnée : </label
            >
            <select
              v-model="selectedZone"
              class="border border-gray-300 rounded-md px-2 py-1 w-full"
            >
              <option
                v-for="(zone, idx) in availableZones"
                :key="idx"
                :value="zone"
              >
                {{ zone }}
              </option>
            </select>
          </div>

          <div>
            <label class="block font-semibold text-sm mb-1"
            >Signes d'inspections :</label
            >
            <MultipleSelector
              v-model="selectedSigns"
              :options="allPossibleSigns"
            />
          </div>

          <div class="mt-4">
            <ActionButton
              label="OK"
              :onClick="handleAddInspection"
              :color="Color.Green"
            />
          </div>
        </div>
      </div>

      <div class="w-1/2 flex flex-col">
        <h2 class="text-lg font-semibold mb-4">Récapitulatif des résultats</h2>
        <table class="w-[350px] border-collapse">
          <thead>
          <tr class="border-b border-gray-300">
            <th class="text-left py-2 w-1/4">Zone</th>
            <th class="text-left py-2 w-1/2">Signes d’inspections</th>
            <th class="text-center py-2 w-1/4">Actions</th>
          </tr>
          </thead>
          <tbody>
          <tr
            v-for="(item, index) in inspectionResults"
            :key="index"
            class="border-b border-gray-100"
          >
            <td class="py-3 px-2">{{ item.zone }}</td>
            <td class="py-3 px-2">{{ item.signs.join(', ') }}</td>
            <td class="py-3 px-2 flex justify-center gap-2">
              <ActionButton
                :icon="iconEdit"
                :color="Color.Grey"
                iconOnly
                size="small"
                @click="handleEditInspection(index)"
              />
              <ActionButton
                :icon="iconDelete"
                :color="Color.Grey"
                iconOnly
                size="small"
                @click="handleDeleteInspection(index)"
              />
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </GenericModal>
</template>

<style scoped>
</style>