<script setup lang="ts">
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faPlus, faClose, faCheck } from '@fortawesome/free-solid-svg-icons';
import { ref } from 'vue';
import type { Interpretation } from '@/models/diagnostic/interpretation.model';
import type { PrimaryElement } from '@/models/diagnostic/primaryElement.model';
import { useDiagnosticStore } from '@/stores/diagnostic.store';

const props = defineProps<{
  // Create panel management
  displayCreateInterpretationPanel: boolean;
  handleOnChangeDisplayCreateInterpretationPanel: () => void;

  // Creation of an interpretation
  selectedInterpretationPrimaryElement?: PrimaryElement;
  unselectInterpretationPrimaryElement: () => void;
  handleOnCreateInterpretation: (
    primaryElement: PrimaryElement,
    interpretationValue: string
  ) => void;

  // Creation of a syndrom
  displayCreateSyndromPanel: boolean;
  selectedSyndromInterpretations: Interpretation[];
  setSelectedSyndromInterpretation: (interpretation: Interpretation) => void;

  // Creation of an hypothesis
  displayCreateHypothesisPanel: boolean;
  selectedHypothesisInterpretations: Interpretation[];
  setSelectedHypothesisInterpretation: (interpretation: Interpretation) => void;
}>();

const diagnosticStore = useDiagnosticStore();

const interpretationValue = ref<string>('');

const isSelectableInterpretation = () =>
  props.displayCreateSyndromPanel || props.displayCreateHypothesisPanel;

/**
 * Allow to handle the click on the create interpretation button
 */
const handleOnClickCreateInterpretation = () => {
  if (props.selectedInterpretationPrimaryElement) {
    props.handleOnCreateInterpretation(
      props.selectedInterpretationPrimaryElement,
      interpretationValue.value
    );
    interpretationValue.value = '';
  }
};

const isSelectedInterpretation = (interpretation: Interpretation) => {
  if (props.displayCreateSyndromPanel) {
    return props.selectedSyndromInterpretations.some(
      (selectedInterpretation) => selectedInterpretation.id === interpretation.id
    );
  } else if (props.displayCreateHypothesisPanel) {
    return props.selectedHypothesisInterpretations.some(
      (selectedInterpretation) => selectedInterpretation.id === interpretation.id
    );
  }
  return false;
};

const handleSelectInterpretation = (interpretation: Interpretation) => {
  if (props.displayCreateSyndromPanel) {
    props.setSelectedSyndromInterpretation(interpretation);
  } else if (props.displayCreateHypothesisPanel) {
    props.setSelectedHypothesisInterpretation(interpretation);
  }
};
</script>

<template>
  <div class="w-full card items-center bg-base-100 rounded-lg shadow-lg border">
    <div class="w-full h-10 min-h-10 flex justify-start items-center bg-slate-50 rounded-lg">
      <h2 class="font-bold ml-4">Interprétations</h2>
    </div>
    <div class="w-full h-full p-4 overflow-auto">
      <!-- Create interpretation panel -->
      <template v-if="displayCreateInterpretationPanel">
        <div class="w-full h-full flex flex-row bg-slate-100">
          <div class="flex-grow flex flex-row justify-center">
            <div class="flex-grow flex items-center p-2">
              <div class="w-1/2 flex flex-col justify-center items-center">
                <span class="text-md mb-2">Élement primaire selectionné : </span>
                <span
                  v-if="selectedInterpretationPrimaryElement"
                  class="badge badge-neutral ml-2 cursor-pointer overflow-hidden items-start"
                  @click="unselectInterpretationPrimaryElement"
                >
                  {{ selectedInterpretationPrimaryElement.value }}
                </span>
                <span v-else class="badge overflow-hidden items-start">Aucun élément sélectionné</span>
              </div>
              <div class="divider divider-horizontal" />
              <div class="w-1/2 card">
                <label class="form-control w-full">
                  <div class="label">
                    <span class="label-text">Quelle est votre interprétation ?</span>
                  </div>
                  <input
                    id="interpretation-value"
                    autocomplete="off"
                    type="text"
                    placeholder="Interprétation"
                    class="input input-bordered input-md w-full"
                    v-model="interpretationValue"
                  />
                </label>
              </div>
            </div>
            <div class="flex items-center p-2">
              <button
                class="btn btn-lg btn-success text-white"
                @click="handleOnClickCreateInterpretation"
                :disabled="!selectedInterpretationPrimaryElement || !interpretationValue"
              >
                <FontAwesomeIcon :icon="faCheck" />
              </button>
            </div>
          </div>
          <div class="p-2">
            <button
              class="btn btn-sm btn-ghost btn-circle"
              @click="handleOnChangeDisplayCreateInterpretationPanel"
            >
              <FontAwesomeIcon :icon="faClose" />
            </button>
          </div>
        </div>
      </template>

      <!-- Display selectable interpretation list -->
      <template v-else-if="isSelectableInterpretation()">
        <span
          v-for="interpretation in diagnosticStore.getInterpretations"
          v-bind:key="interpretation.id"
          :class="`badge badge-lg badge-warning m-1 interpretation-selectable overflow-hidden items-start ${
            isSelectedInterpretation(interpretation) && 'interpretation-selected'
          }`"
          @click="handleSelectInterpretation(interpretation)"
        >
          {{ interpretation.value }}
        </span>
        <button
          class="btn btn-square btn-sm"
          @click="handleOnChangeDisplayCreateInterpretationPanel"
        >
          <FontAwesomeIcon :icon="faPlus" />
        </button>
      </template>

      <!-- Display interpretation list -->
      <template v-else>
        <span
          v-for="interpretation in diagnosticStore.getInterpretations"
          v-bind:key="interpretation.id"
          class="badge badge-lg badge-warning m-1 min-h-fit"
        >
          {{ interpretation.value }}
        </span>
        <button
          class="btn btn-square btn-sm"
          @click="handleOnChangeDisplayCreateInterpretationPanel"
        >
          <FontAwesomeIcon :icon="faPlus" />
        </button>
      </template>
    </div>
  </div>
</template>

<style scoped>
.interpretation-selectable {
  cursor: pointer;
}
.interpretation-selectable:hover,
.interpretation-selected {
  background-color: #f8df9f;
}
</style>
