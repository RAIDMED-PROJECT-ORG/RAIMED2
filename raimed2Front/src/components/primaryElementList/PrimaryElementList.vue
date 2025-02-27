<script setup lang="ts">
import type { PrimaryElement } from '@/models/diagnostic/primaryElement.model';
import { useDiagnosticStore } from '@/stores/diagnostic.store';

const diagnosticStore = useDiagnosticStore();

const props = defineProps<{
  // Creation of an interpretation
  displayCreateInterpretationPanel: boolean;
  selectedInterpretationPrimaryElement?: PrimaryElement;
  setSelectedInterpretationPrimaryElement: (primaryElement: PrimaryElement) => void;

  // Creation of a syndrom
  displayCreateSyndromPanel: boolean;
  selectedSyndromPrimaryElements: PrimaryElement[];
  setSelectedSyndromPrimaryElement: (primaryElement: PrimaryElement) => void;

  // Creation of an hypothesis
  displayCreateHypothesisPanel: boolean;
  selectedHypothesisPrimaryElement: PrimaryElement[];
  setSelectedHypothesisPrimaryElement: (primaryElement: PrimaryElement) => void;
}>();

/**
 * Allow to know if the primary element(s) is/are selectable or not
 */
const isSelectablePrimaryElement = () =>
  props.displayCreateInterpretationPanel ||
  props.displayCreateSyndromPanel ||
  props.displayCreateHypothesisPanel;

/**
 * Allow to know if the given primary element is selected or not
 * @param primaryElement
 */
const isSelectedPrimaryElement = (primaryElement: PrimaryElement) => {
  if (props.displayCreateInterpretationPanel) {
    return props.selectedInterpretationPrimaryElement?.value === primaryElement.value;
  } else if (props.displayCreateSyndromPanel) {
    return props.selectedSyndromPrimaryElements.some(
      (element) =>
        element.value === primaryElement.value && element.actionId === primaryElement.actionId
    );
  } else if (props.displayCreateHypothesisPanel) {
    return props.selectedHypothesisPrimaryElement.some(
      (element) =>
        element.value === primaryElement.value && element.actionId === primaryElement.actionId
    );
  }
  return false;
};

/**
 * Allow to handle the selection of a primary element and throw the appropriate handler
 * @param primaryElement
 */
const handleSelectPrimaryElement = (primaryElement: PrimaryElement) => {
  if (props.displayCreateInterpretationPanel) {
    props.setSelectedInterpretationPrimaryElement(primaryElement);
  } else if (props.displayCreateSyndromPanel) {
    props.setSelectedSyndromPrimaryElement(primaryElement);
  } else if (props.displayCreateHypothesisPanel) {
    props.setSelectedHypothesisPrimaryElement(primaryElement);
  }
};
</script>

<template>
  <div class="w-full card items-center bg-base-100 rounded-lg shadow-lg border">
    <div class="w-full h-10 min-h-10 flex justify-start items-center bg-slate-50 rounded-lg">
      <h2 class="font-bold ml-4">Élements primaires</h2>
    </div>
    <div class="w-full h-full p-4 overflow-auto">
      <template v-if="isSelectablePrimaryElement()">
        <span
          v-for="primaryElement in diagnosticStore.getPrimaryElements"
          v-bind:key="primaryElement.actionId"
          :class="`badge badge-lg m-1 primary-element-selectable min-h-fit ${
            isSelectedPrimaryElement(primaryElement) && 'badge-ghost'
          }`"
          @click="handleSelectPrimaryElement(primaryElement)"
        >
          {{ primaryElement.value }}
        </span>
      </template>
      <template v-else>
        <span
          v-for="primaryElement in diagnosticStore.getPrimaryElements"
          v-bind:key="primaryElement.actionId"
          class="badge badge-lg m-1 min-h-fit"
        >
          {{ primaryElement.value }}
        </span>
      </template>
    </div>
  </div>
</template>

<style scoped>
.primary-element-selectable {
  cursor: pointer;
}

.primary-element-selectable:hover {
  background-color: oklch(var(--b2));
}
</style>
