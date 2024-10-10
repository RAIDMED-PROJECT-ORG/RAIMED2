<script setup lang="ts">
// Import the components of the reasoning sections
import PrimaryElementList from '@/components/primaryElementList/PrimaryElementList.vue';
import InterpretationList from '@/components/interpretationList/InterpretationList.vue';
import SyndromList from '@/components/syndromList/SyndromList.vue';
import HypothesisList from '@/components/hypothesisList/HypothesisList.vue';
import ModalDiagnosticValidation from '@/components/modalDiagnosticValidation/ModalDiagnosticValidation.vue';

import type { PrimaryElement } from '@/models/diagnostic/primaryElement.model';
import type { Interpretation } from '@/models/diagnostic/interpretation.model';
import type { Syndrom, SyndromElements } from '@/models/diagnostic/syndrom.model';
import type { AddInterpretationDto } from '@/models/diagnostic/addInterpretationDto';
import type { AddSyndromDto } from '@/models/diagnostic/addSyndromDto';
import type { AddHypothesisDto } from '@models/diagnostic/addHypothesisDto';

import { ref } from 'vue';
import { useMutation } from '@tanstack/vue-query';
import axiosInstance from '@/service/httpClient/axios.config';
import { useDiagnosticStore } from '@/stores/diagnostic.store';
import type { HypothesisElements } from '@/models/diagnostic/hypothesis.model';

const props = defineProps<{
  diagnosticId: string;
}>();

const diagnosticStore = useDiagnosticStore();

// Control the display of the create interpretation panel
const displayCreateInterpretationPanel = ref(false);

// Control the selected primary element for interpretation
const selectedInterpretationPrimaryElement = ref<PrimaryElement | undefined>(undefined);

// Control the display of the create syndrom panel
const displayCreateSyndromPanel = ref(false);

// Control the selected primary element(s) / interpretation(s) for syndrom
const selectedElementsForSyndrom = ref<SyndromElements>({
  primaryElements: [],
  interpretations: []
});

// Control the display of the create hypothesis panel
const displayCreateHypothesisPanel = ref(false);

// Control the selected primary element(s) / interpretation(s) / syndrom(s) for hypothesis
const selectedElementsForHypothesis = ref<HypothesisElements>({
  primaryElements: [],
  interpretations: [],
  syndroms: []
});

// Add interpretation to diagnostic
const mutationAddInterpretationToDiagnostic = useMutation({
  mutationFn: (addInterpretationDto: AddInterpretationDto) =>
    axiosInstance
      .post(`/diagnostic/${props.diagnosticId}/interpretation`, addInterpretationDto)
      .then((res) => res.data),
  onSuccess: (data) => diagnosticStore.setDiagnosticInterpretations(data)
});

// Add syndrom to diagnostic
const mutationAddSyndromToDiagnostic = useMutation({
  mutationFn: (addSyndromDto: AddSyndromDto) =>
    axiosInstance
      .post(`/diagnostic/${props.diagnosticId}/syndrom`, addSyndromDto)
      .then((res) => res.data),
  onSuccess: (data) => diagnosticStore.setDiagnosticSyndroms(data)
});

const mutationAddHypothesisToDiagnostic = useMutation({
  mutationFn: (addHypothesisDto: AddHypothesisDto) =>
    axiosInstance
      .post(`/diagnostic/${props.diagnosticId}/hypothesis`, addHypothesisDto)
      .then((res) => res.data),
  onSuccess: (data) => diagnosticStore.setDiagnosticHypothesis(data)
});

/**
 * Allow to handle the click on the create interpretation button
 * and display the create interpretation panel
 */
const handleOnChangeDisplayCreateInterpretationPanel = () => {
  displayCreateInterpretationPanel.value = !displayCreateInterpretationPanel.value;
  if (!displayCreateInterpretationPanel.value) {
    // Reset the state if the panel is closed
    selectedInterpretationPrimaryElement.value = undefined;
  } else {
    // If the other reasoning create panel are displayed, we close them
    if (displayCreateSyndromPanel.value) handleOnChangeDisplayCreateSyndromPanel();
    if (displayCreateHypothesisPanel.value) handleOnChangeDisplayCreateHypothesisPanel();
  }
};

/**
 * Allow to handle the click on the create syndrom button
 * and display the create syndrom panel
 */
const handleOnChangeDisplayCreateSyndromPanel = () => {
  displayCreateSyndromPanel.value = !displayCreateSyndromPanel.value;
  if (!displayCreateSyndromPanel.value) {
    // Reset the state if the panel is closed
    selectedElementsForSyndrom.value = { primaryElements: [], interpretations: [] };
  } else {
    // If the other reasoning create panel are displayed, we close them
    if (displayCreateInterpretationPanel.value) handleOnChangeDisplayCreateInterpretationPanel();
    if (displayCreateHypothesisPanel.value) handleOnChangeDisplayCreateHypothesisPanel();
  }
};

/**
 * Allow to handle the click on the create hypothesis button
 * and display the create hypothesis panel
 */
const handleOnChangeDisplayCreateHypothesisPanel = () => {
  displayCreateHypothesisPanel.value = !displayCreateHypothesisPanel.value;
  if (!displayCreateHypothesisPanel.value) {
    // Reset the state if the panel is closed
    selectedElementsForHypothesis.value = {
      primaryElements: [],
      interpretations: [],
      syndroms: []
    };
  } else {
    // If the other reasoning create panel are displayed, we close them
    if (displayCreateInterpretationPanel.value) handleOnChangeDisplayCreateInterpretationPanel();
    if (displayCreateSyndromPanel.value) handleOnChangeDisplayCreateSyndromPanel();
  }
};

/**
 * Allow to handle the click on a primary element or an interpretation to select it for the syndrom or remove it
 * @param primaryElement the primary element to select or remove
 * @param interpretation the interpretation to select or remove
 */
const handleOnSelectSyndromElement = (
  primaryElement?: PrimaryElement,
  interpretation?: Interpretation
) => {
  if (primaryElement) {
    if (
      selectedElementsForSyndrom.value.primaryElements.some(
        (element) => element.value === primaryElement.value
      )
    ) {
      // If the primary element is already selected, we remove it
      selectedElementsForSyndrom.value.primaryElements =
        selectedElementsForSyndrom.value.primaryElements.filter(
          (element) => element.value !== primaryElement.value
        );
    } else {
      // If the needed elements are not already selected, we add it
      selectedElementsForSyndrom.value.primaryElements.push(primaryElement);
    }
  } else if (interpretation) {
    if (
      selectedElementsForSyndrom.value.interpretations.some(
        (element) => element.value === interpretation.value
      )
    ) {
      // If the interpretation is already selected, we remove it
      selectedElementsForSyndrom.value.interpretations =
        selectedElementsForSyndrom.value.interpretations.filter(
          (element) => element.value !== interpretation.value
        );
    } else {
      // If the needed elements are not already selected, we add it
      selectedElementsForSyndrom.value.interpretations.push(interpretation);
    }
  }
};

/**
 * Allow to handle the click on a primary element or an interpretation or a syndrom to select it for the hypothesis or remove it
 * @param primaryElement the primary element to select or remove
 * @param interpretation the interpretation to select or remove
 * @param syndrom the syndrom to select or remove
 */
const handleOnSelectHypothesisElement = (
  primaryElement?: PrimaryElement,
  interpretation?: Interpretation,
  syndrom?: Syndrom
): void => {
  if (primaryElement) {
    if (
      selectedElementsForHypothesis.value.primaryElements.some(
        (element) => element.value === primaryElement.value
      )
    ) {
      // If the primary element is already selected, we remove it
      selectedElementsForHypothesis.value.primaryElements =
        selectedElementsForHypothesis.value.primaryElements.filter(
          (element) => element.value !== primaryElement.value
        );
    } else {
      // If the needed elements are not already selected, we add it
      selectedElementsForHypothesis.value.primaryElements.push(primaryElement);
    }
  } else if (interpretation) {
    if (
      selectedElementsForHypothesis.value.interpretations.some(
        (element) => element.value === interpretation.value
      )
    ) {
      // If the interpretation is already selected, we remove it
      selectedElementsForHypothesis.value.interpretations =
        selectedElementsForHypothesis.value.interpretations.filter(
          (element) => element.value !== interpretation.value
        );
    } else {
      // If the needed elements are not already selected, we add it
      selectedElementsForHypothesis.value.interpretations.push(interpretation);
    }
  } else if (syndrom) {
    if (
      selectedElementsForHypothesis.value.syndroms.some(
        (element) => element.value === syndrom.value
      )
    ) {
      // If the syndrom is already selected, we remove it
      selectedElementsForHypothesis.value.syndroms =
        selectedElementsForHypothesis.value.syndroms.filter(
          (element) => element.value !== syndrom.value
        );
    } else {
      // If the needed elements are not already selected, we add it
      selectedElementsForHypothesis.value.syndroms.push(syndrom);
    }
  }
};

/**
 * Allow to handle when an interpretation is created and add it to the diagnostic
 * @param primaryElement
 * @param interpretationValue
 */
const handleOnCreateInterpretation = (
  primaryElement: PrimaryElement,
  interpretationValue: string
) => {
  if (primaryElement && interpretationValue) {
    mutationAddInterpretationToDiagnostic.mutate({
      value: interpretationValue,
      actionId: primaryElement.actionId,
      primaryElement: primaryElement.value
    });
    displayCreateInterpretationPanel.value = false;
    selectedInterpretationPrimaryElement.value = undefined;
  }
};

/**
 * Allow to handle when a syndrom is created and add it to the diagnostic
 * @param syndromElements
 * @param syndromValue
 */
const handleOnCreateSyndrom = (syndromElements: SyndromElements, syndromValue: string) => {
  if (
    syndromValue &&
    syndromElements.primaryElements.length + syndromElements.interpretations.length >= 2
  ) {
    mutationAddSyndromToDiagnostic.mutate({
      value: syndromValue,
      primaryElements: syndromElements.primaryElements,
      interpretations: syndromElements.interpretations
    });
    displayCreateSyndromPanel.value = false;
    selectedElementsForSyndrom.value = { primaryElements: [], interpretations: [] };
  }
};

/**
 * Allow to handle when an hypothesis is created and add it to the diagnostic
 * @param hypothesisElements
 * @param hypothesisValue
 */
const handleOnCreateHypothesis = (
  hypothesisElements: HypothesisElements,
  hypothesisValue: string
) => {
  if (
    hypothesisValue &&
    selectedElementsForHypothesis.value.primaryElements.length +
      selectedElementsForHypothesis.value.interpretations.length +
      selectedElementsForHypothesis.value.syndroms.length >=
      3
  ) {
    mutationAddHypothesisToDiagnostic.mutate({
      primaryElements: selectedElementsForHypothesis.value.primaryElements,
      interpretations: selectedElementsForHypothesis.value.interpretations,
      syndroms: selectedElementsForHypothesis.value.syndroms,
      value: hypothesisValue
    });
    displayCreateHypothesisPanel.value = false;
    selectedElementsForHypothesis.value = {
      primaryElements: [],
      interpretations: [],
      syndroms: []
    };
  }
};
</script>

<template>
  <div class="flex-grow grid grid-cols-1 grid-rows-4 gap-2 overflow-auto">
    <PrimaryElementList
      :displayCreateInterpretationPanel="displayCreateInterpretationPanel"
      :selectedInterpretationPrimaryElement="selectedInterpretationPrimaryElement"
      :setSelectedInterpretationPrimaryElement="
        (primaryElement: PrimaryElement) => (selectedInterpretationPrimaryElement = primaryElement)
      "
      :displayCreateSyndromPanel="displayCreateSyndromPanel"
      :selectedSyndromPrimaryElements="selectedElementsForSyndrom.primaryElements"
      :setSelectedSyndromPrimaryElement="
        (primaryElement) => handleOnSelectSyndromElement(primaryElement)
      "
      :displayCreateHypothesisPanel="displayCreateHypothesisPanel"
      :selectedHypothesisPrimaryElement="selectedElementsForHypothesis.primaryElements"
      :setSelectedHypothesisPrimaryElement="
        (primaryElement) => handleOnSelectHypothesisElement(primaryElement, undefined, undefined)
      "
    />
    <InterpretationList
      :displayCreateInterpretationPanel="displayCreateInterpretationPanel"
      :selectedInterpretationPrimaryElement="selectedInterpretationPrimaryElement"
      :unselectInterpretationPrimaryElement="
        () => (selectedInterpretationPrimaryElement = undefined)
      "
      :handleOnChangeDisplayCreateInterpretationPanel="
        handleOnChangeDisplayCreateInterpretationPanel
      "
      :handleOnCreateInterpretation="handleOnCreateInterpretation"
      :displayCreateSyndromPanel="displayCreateSyndromPanel"
      :selectedSyndromInterpretations="selectedElementsForSyndrom.interpretations"
      :setSelectedSyndromInterpretation="
        (interpretation) => handleOnSelectSyndromElement(undefined, interpretation)
      "
      :displayCreateHypothesisPanel="displayCreateHypothesisPanel"
      :selectedHypothesisInterpretations="selectedElementsForHypothesis.interpretations"
      :setSelectedHypothesisInterpretation="
        (interpretation) => handleOnSelectHypothesisElement(undefined, interpretation, undefined)
      "
    />
    <SyndromList
      :displayCreateSyndromPanel="displayCreateSyndromPanel"
      :handleOnChangeDisplayCreateSyndromPanel="handleOnChangeDisplayCreateSyndromPanel"
      :selectedElementsForSyndrom="selectedElementsForSyndrom"
      :unselectSyndromPrimaryElement="
        (primaryElement) =>
          (selectedElementsForSyndrom.primaryElements =
            selectedElementsForSyndrom.primaryElements.filter(
              (element) => element.value !== primaryElement.value
            ))
      "
      :unselectSyndromInterpretation="
        (interpretation) =>
          (selectedElementsForSyndrom.interpretations =
            selectedElementsForSyndrom.interpretations.filter(
              (element) => element.value !== interpretation.value
            ))
      "
      :handleOnCreateSyndrom="handleOnCreateSyndrom"
      :displayCreateHypothesisPanel="displayCreateHypothesisPanel"
      :selectedHypothesisSyndroms="selectedElementsForHypothesis.syndroms"
      :setSelectedHypothesisSyndrom="
        (syndrom) => handleOnSelectHypothesisElement(undefined, undefined, syndrom)
      "
    />
    <HypothesisList
      :displayCreateHypothesisPanel="displayCreateHypothesisPanel"
      :handleOnChangeDisplayCreateHypothesisPanel="handleOnChangeDisplayCreateHypothesisPanel"
      :selectedElementsForHypothesis="selectedElementsForHypothesis"
      :unselectHypothesisPrimaryElement="
        (primaryElement) =>
          (selectedElementsForHypothesis.primaryElements =
            selectedElementsForHypothesis.primaryElements.filter(
              (element) => element.value !== primaryElement.value
            ))
      "
      :unselectHypothesisInterpretation="
        (interpretation) =>
          (selectedElementsForHypothesis.interpretations =
            selectedElementsForHypothesis.interpretations.filter(
              (element) => element.value !== interpretation.value
            ))
      "
      :unselectHypothesisSyndrom="
        (syndrom) =>
          (selectedElementsForHypothesis.syndroms = selectedElementsForHypothesis.syndroms.filter(
            (element) => element.value !== syndrom.value
          ))
      "
      :handleOnCreateHypothesis="handleOnCreateHypothesis"
    />
  </div>
  <div class="flex justify-center items-center mt-2">
    <ModalDiagnosticValidation :diagnosticId="diagnosticId" />
  </div>
</template>
