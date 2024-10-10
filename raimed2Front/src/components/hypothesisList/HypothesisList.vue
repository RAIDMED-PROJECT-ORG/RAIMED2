<script setup lang="ts">
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faCheck, faClose, faPlus } from '@fortawesome/free-solid-svg-icons';
import type { HypothesisElements } from '@/models/diagnostic/hypothesis.model';
import { ref } from 'vue';
import { useDiagnosticStore } from '@/stores/diagnostic.store';
import type { PrimaryElement } from '@/models/diagnostic/primaryElement.model';
import type { Interpretation } from '@/models/diagnostic/interpretation.model';
import type { Syndrom } from '@/models/diagnostic/syndrom.model';

const props = defineProps<{
  displayCreateHypothesisPanel: boolean;
  handleOnChangeDisplayCreateHypothesisPanel: () => void;
  selectedElementsForHypothesis: HypothesisElements;

  unselectHypothesisPrimaryElement: (primaryElement: PrimaryElement) => void;
  unselectHypothesisInterpretation: (interpretation: Interpretation) => void;
  unselectHypothesisSyndrom: (syndrom: Syndrom) => void;

  handleOnCreateHypothesis: (
    selectedElementsForHypothesis: HypothesisElements,
    hypothesisValue: string
  ) => void;
}>();

const diagnosticStore = useDiagnosticStore();

const hypothesisValue = ref<string>('');

const handleOnClickCreateHypothesis = () => {
  props.handleOnCreateHypothesis(props.selectedElementsForHypothesis, hypothesisValue.value);
  hypothesisValue.value = '';
};
</script>

<template>
  <div class="w-full card items-center bg-base-100 rounded-lg shadow-lg border">
    <div class="w-full h-10 min-h-10 flex justify-start items-center bg-slate-50 rounded-lg">
      <h2 class="font-bold ml-4">Hypothèses</h2>
    </div>
    <div class="w-full h-full p-4 overflow-auto">
      <!-- Create Hypothesis panel -->
      <template v-if="displayCreateHypothesisPanel">
        <div class="w-full h-full flex flex-row bg-slate-100">
          <div class="flex-grow flex flex-row justify-center">
            <div class="flex-grow flex items-center p-2">
              <div class="w-1/2 flex-grow flex flex-col justify-center items-center">
                <span class="text-md truncate mb-2">Élements selectionnés (au moins 3) : </span>
                <div
                  v-if="
                    selectedElementsForHypothesis.primaryElements.length ||
                    selectedElementsForHypothesis.interpretations.length ||
                    selectedElementsForHypothesis.syndroms.length
                  "
                  class="flex flex-row flex-wrap justify-center"
                >
                  <span
                    v-for="primaryElement in selectedElementsForHypothesis.primaryElements"
                    v-bind:key="primaryElement.actionId"
                    class="badge badge-neutral m-1 cursor-pointer"
                    @click="unselectHypothesisPrimaryElement(primaryElement)"
                  >
                    {{ primaryElement.value }}
                  </span>
                  <span
                    v-for="interpretation in selectedElementsForHypothesis.interpretations"
                    v-bind:key="interpretation.id"
                    class="badge badge-warning m-1 cursor-pointer"
                    @click="unselectHypothesisInterpretation(interpretation)"
                  >
                    {{ interpretation.value }}
                  </span>
                  <span
                    v-for="syndrom in selectedElementsForHypothesis.syndroms"
                    v-bind:key="syndrom.id"
                    class="badge bg-orange-300 m-1 cursor-pointer"
                    @click="unselectHypothesisSyndrom(syndrom)"
                  >
                    {{ syndrom.value }}
                  </span>
                </div>
                <span v-else class="badge"> Aucuns éléments sélectionnés </span>
              </div>
              <div class="divider divider-horizontal" />
              <div class="w-1/2 flex-grow card">
                <label class="form-control w-full">
                  <div class="label">
                    <span class="label-text">Renseigner l'hypothèse</span>
                  </div>
                  <input
                    id="hypothesis-value"
                    autocomplete="off"
                    type="text"
                    placeholder="Hypothèse"
                    class="input input-bordered input-md w-full"
                    v-model="hypothesisValue"
                  />
                </label>
              </div>
            </div>
            <div class="flex items-center p-2">
              <button
                class="btn btn-lg btn-success text-white"
                @click="handleOnClickCreateHypothesis"
                :disabled="
                  selectedElementsForHypothesis.interpretations.length +
                    selectedElementsForHypothesis.primaryElements.length +
                    selectedElementsForHypothesis.syndroms.length <
                    3 || !hypothesisValue
                "
              >
                <FontAwesomeIcon :icon="faCheck" />
              </button>
            </div>
          </div>
          <div class="p-2">
            <button
              class="btn btn-sm btn-ghost btn-circle"
              @click="handleOnChangeDisplayCreateHypothesisPanel"
            >
              <FontAwesomeIcon :icon="faClose" />
            </button>
          </div>
        </div>
      </template>

      <template v-else>
        <span
          v-for="syndrom in diagnosticStore.getHypothesis"
          v-bind:key="syndrom.id"
          class="badge badge-lg bg-blue-400 m-1"
        >
          {{ syndrom.value }}
        </span>
        <button class="btn btn-square btn-sm" @click="handleOnChangeDisplayCreateHypothesisPanel">
          <FontAwesomeIcon :icon="faPlus" />
        </button>
      </template>
    </div>
  </div>
</template>
