<script setup lang="ts">
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faCheck, faClose, faPlus } from '@fortawesome/free-solid-svg-icons';
import { ref } from 'vue';
import type { Syndrom, SyndromElements } from '@/models/diagnostic/syndrom.model';
import type { PrimaryElement } from '@/models/diagnostic/primaryElement.model';
import type { Interpretation } from '@/models/diagnostic/interpretation.model';
import { useDiagnosticStore } from '@/stores/diagnostic.store';

const props = defineProps<{
  // Create panel management
  displayCreateSyndromPanel: boolean;
  handleOnChangeDisplayCreateSyndromPanel: () => void;

  // Creation of a syndrom
  selectedElementsForSyndrom: SyndromElements;
  unselectSyndromPrimaryElement: (primaryElement: PrimaryElement) => void;
  unselectSyndromInterpretation: (interpretation: Interpretation) => void;
  handleOnCreateSyndrom: (
    selectedElementsForSyndrom: SyndromElements,
    syndromValue: string
  ) => void;

  // Creation of an hypothesis
  displayCreateHypothesisPanel: boolean;
  selectedHypothesisSyndroms: Syndrom[];
  setSelectedHypothesisSyndrom: (syndrom: Syndrom) => void;
}>();

const diagnosticStore = useDiagnosticStore();

const syndromValue = ref<string>('');

const isSelectableSyndrom = () => props.displayCreateHypothesisPanel;

/**
 * Allow to handle the click on the create syndrom button
 */
const handleOnClickCreateSyndrom = () => {
  props.handleOnCreateSyndrom(props.selectedElementsForSyndrom, syndromValue.value);
  syndromValue.value = '';
};

const isSelectedSyndrom = (syndrom: Syndrom) => {
  if (props.displayCreateHypothesisPanel) {
    return props.selectedHypothesisSyndroms.some(
      (selectedSyndrom) => selectedSyndrom.id === syndrom.id
    );
  }
  return false;
};

const handleSelectSyndrom = (syndrom: Syndrom) => {
  if (props.displayCreateHypothesisPanel) {
    props.setSelectedHypothesisSyndrom(syndrom);
  }
};
</script>

<template>
  <div class="w-full card items-center bg-base-100 rounded-lg shadow-lg border">
    <div class="w-full h-10 min-h-10 flex justify-start items-center bg-slate-50 rounded-lg">
      <h2 class="font-bold ml-4">Syndromes</h2>
    </div>
    <div class="w-full h-full p-4 overflow-auto">
      <!-- Create syndrom panel -->
      <template v-if="displayCreateSyndromPanel">
        <div class="w-full h-full flex flex-row bg-slate-100">
          <div class="flex-grow flex flex-row justify-center">
            <div class="flex-grow flex items-center p-2">
              <div class="w-1/2 flex-grow flex flex-col justify-center items-center">
                <span class="text-md truncate mb-2">Élements selectionnés (au moins 2) : </span>
                <div
                  v-if="
                    selectedElementsForSyndrom.primaryElements.length ||
                    selectedElementsForSyndrom.interpretations.length
                  "
                  class="flex flex-row flex-wrap"
                >
                  <span
                    v-for="primaryElement in selectedElementsForSyndrom.primaryElements"
                    v-bind:key="primaryElement.actionId"
                    class="badge badge-neutral m-1 cursor-pointer"
                    @click="unselectSyndromPrimaryElement(primaryElement)"
                  >
                    {{ primaryElement.value }}
                  </span>

                  <span
                    v-for="interpretation in selectedElementsForSyndrom.interpretations"
                    v-bind:key="interpretation.id"
                    class="badge badge-warning m-1 cursor-pointer"
                    @click="unselectSyndromInterpretation(interpretation)"
                  >
                    {{ interpretation.value }}
                  </span>
                </div>

                <span v-else class="badge"> Aucuns éléments sélectionnés </span>
              </div>
              <div class="divider divider-horizontal" />
              <div class="w-1/2 card">
                <label class="form-control w-full">
                  <div class="label">
                    <span class="label-text">Renseigner le syndrome</span>
                  </div>
                  <input
                    id="syndrom-value"
                    autocomplete="off"
                    type="text"
                    placeholder="Syndrome"
                    class="input input-bordered input-md w-full"
                    v-model="syndromValue"
                  />
                </label>
              </div>
            </div>
            <div class="flex items-center p-2">
              <button
                class="btn btn-lg btn-success text-white"
                @click="handleOnClickCreateSyndrom"
                :disabled="
                  selectedElementsForSyndrom.interpretations.length +
                    selectedElementsForSyndrom.primaryElements.length <
                    2 || !syndromValue
                "
              >
                <FontAwesomeIcon :icon="faCheck" />
              </button>
            </div>
          </div>
          <div class="p-2">
            <button
              class="btn btn-sm btn-ghost btn-circle"
              @click="handleOnChangeDisplayCreateSyndromPanel"
            >
              <FontAwesomeIcon :icon="faClose" />
            </button>
          </div>
        </div>
      </template>

      <!-- Display selectable syndrom list -->
      <template v-else-if="isSelectableSyndrom()">
        <span
          v-for="syndrom in diagnosticStore.getSyndroms"
          v-bind:key="syndrom.id"
          :class="`badge badge-lg bg-orange-300 m-1 syndrom-selectable ${
            isSelectedSyndrom(syndrom) && 'syndrom-selected'
          }`"
          @click="handleSelectSyndrom(syndrom)"
        >
          {{ syndrom.value }}
        </span>
        <button class="btn btn-square btn-sm" @click="handleOnChangeDisplayCreateSyndromPanel">
          <FontAwesomeIcon :icon="faPlus" />
        </button>
      </template>

      <!-- Display syndrom list -->
      <template v-else>
        <span
          v-for="syndrom in diagnosticStore.getSyndroms"
          v-bind:key="syndrom.id"
          class="badge badge-lg bg-orange-300 m-1"
        >
          {{ syndrom.value }}
        </span>
        <button class="btn btn-square btn-sm" @click="handleOnChangeDisplayCreateSyndromPanel">
          <FontAwesomeIcon :icon="faPlus" />
        </button>
      </template>
    </div>
  </div>
</template>

<style scoped>
.syndrom-selectable {
  cursor: pointer;
}
.syndrom-selectable:hover,
.syndrom-selected {
  background-color: #fb923c;
}
</style>
