<script setup lang="ts">
import { ref, computed } from 'vue';
import { PrescriptionType } from '@/models/prescription/prescriptionType.enum';
import type { Prescription } from '@/models/prescription/prescription.model';
import { Color } from '@/models/new-patient/color.model';
import GenericModal from '@/components/modal/genericModal/GenericModal.vue';
import ExistingPrescriptionRecherche
  from '@/components/modal/existingPrescriptionModal/ExistingPrescriptionRecherche.vue';
import ExistingPrescriptionListe from '@/components/modal/existingPrescriptionModal/ExistingPrescriptionListe.vue';

const props = defineProps<{
  existingPrescriptions: Prescription[];
  onAddPrescriptions: (prescriptions: Prescription[]) => void;
  onClose: () => void;
  patientPrescriptions: Prescription[];
}>();

const searchQuery = ref('');
const selectedType = ref<PrescriptionType | null>(null);
const selectedPrescriptions = ref<Set<string>>(new Set());

const filteredPrescriptions = computed(() => {
  const addedIds = new Set(props.patientPrescriptions.map(p => p.id));
  return props.existingPrescriptions.filter(prescription => {
    const matchesQuery = prescription.name.toLowerCase().includes(searchQuery.value.toLowerCase());
    const matchesType = selectedType.value ? prescription.type === selectedType.value : true;
    const isNotAdded = !addedIds.has(prescription.id);
    return matchesQuery && matchesType && isNotAdded;
  });
});

const toggleSelection = (id: string) => {
  if (selectedPrescriptions.value.has(id)) {
    selectedPrescriptions.value.delete(id);
  } else {
    selectedPrescriptions.value.add(id);
  }
};

const addSelectedPrescriptions = () => {
  const prescriptionsToAdd = props.existingPrescriptions.filter(prescription => selectedPrescriptions.value.has(prescription.id));
  props.onAddPrescriptions(prescriptionsToAdd);
  props.onClose();
};
</script>

<template>
  <GenericModal
      :title="`Liste des prescriptions existantes`"
      :headerColor="Color.Purple"
      validationLabel="Ajouter"
      :onValidation="addSelectedPrescriptions"
      :onBack="props.onClose"
  >
    <div class="p-2.5">
      <ExistingPrescriptionRecherche
          :searchQuery="searchQuery"
          :selectedType="selectedType"
          @updateSearchQuery="value => searchQuery = value"
          @updateSelectedType="value => selectedType = value"
      />
      <ExistingPrescriptionListe
          :prescriptions="filteredPrescriptions"
          :selectedPrescriptions="selectedPrescriptions"
          @toggleSelection="toggleSelection"
      />
    </div>
  </GenericModal>
</template>

<style scoped></style>