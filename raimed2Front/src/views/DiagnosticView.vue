<script setup lang="ts">
import AuthenticatedViewLayout from '@/layouts/AuthenticatedViewLayout.vue';
import ChatWindow from '@/components/chatWindow/ChatWindow.vue';
import ClosedQuestionPanel from '@/components/diagnosticActions/ClosedQuestionPanel.vue';
import DiagnosticActions from '@/components/diagnosticActions/DiagnosticActions.vue';
import OpenedQuestionPanel from '@/components/diagnosticActions/OpenedQuestionPanel.vue';
import RaisoningSection from '@/components/raisoningSection/RaisoningSection.vue';

import type { AddEventDto } from '@/models/diagnostic/addEventDto';
import type { Interpretation } from '@/models/diagnostic/interpretation.model';
import type { Syndrom } from '@/models/diagnostic/syndrom.model';

import { onMounted, ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import axiosInstance from '@/service/httpClient/axios.config';
import { useDiagnosticStore } from '@/stores/diagnostic.store';
import { useMutation, useQuery } from '@tanstack/vue-query';
import ErrorAlert from '@/components/alert/ErrorAlert.vue';
import { PrescriptionType } from '@/models/prescription/prescriptionType.enum';
import PrescriptionPanel from '@/components/diagnosticActions/PrescriptionPanel.vue';
import ExamPanel from '@/components/diagnosticActions/ExamPanel.vue';
import { ExamType } from '@/models/exam/examType.enum';
import { TypeAction } from '@/models/virtual-patient/typeAction.enum';
import PrecisionPanel from '@/components/diagnosticActions/PrecisionPanel.vue';

const router = useRouter();

const virtualPatientId: string = router.currentRoute.value.params.virtualPatientId as string;
const diagnosticId: string = router.currentRoute.value.params.diagnosticId as string;

const diagnosticStore = useDiagnosticStore();

// Control the display of the closed question action panel
const displayClosedQuestionActionPanel = ref(false);

// Control the display of the opened question action panel
const displayOpenedQuestionActionPanel = ref(false);

const displayPrecisionActionPanel = ref(false);

const displayPrescriptionActionPanel = ref<{
  visibility: boolean;
  prescriptionType: PrescriptionType | null;
}>({
  visibility: false,
  prescriptionType: null
});

const displayExamActionPanel = ref<{
  visibility: boolean;
  examType: ExamType | null;
}>({
  visibility: false,
  examType: null
});

const queryDiagnostic = useQuery({
  queryKey: ['diagnostic', diagnosticId],
  queryFn: () => axiosInstance.get(`/diagnostic/${diagnosticId}`).then((res) => res.data),
  refetchOnWindowFocus: false,
  retry: false
});

/**
 * Query to fetch the virtual patient
 */
const queryVirtualPatient = useQuery({
  queryKey: ['virtualPatient', virtualPatientId],
  queryFn: () => axiosInstance.get(`/virtual-patient/${virtualPatientId}`).then((res) => res.data),
  refetchOnWindowFocus: false,
  retry: false
});

/**
 * Watch the virtual patient changes
 */
watch(queryVirtualPatient.data, (virtualPatient) => {
  // Update the state of the diagnostic store
  if (virtualPatient) diagnosticStore.setVirtualPatient(virtualPatient);
});

/**
 * Query to fetch all diagnostic events
 */
const queryDiagnosticEvents = useQuery({
  queryKey: ['diagnosticEvents'],
  queryFn: () => axiosInstance.get(`/diagnostic/${diagnosticId}/event`).then((res) => res.data),
  refetchOnWindowFocus: false,
  retry: false
});

/**
 * Watch the diagnostic events changes
 */
watch(queryDiagnosticEvents.data, (events) => {
  // Update the state of the diagnostic store
  if (events) diagnosticStore.setDiagnosticEvents(events);
});

/**
 * Query to fetch all diagnostic interpretations
 */
const queryDiagnosticInterpretations = useQuery({
  queryKey: ['diagnosticInterpretations'],
  queryFn: () =>
    axiosInstance
      .get<Interpretation[]>(`/diagnostic/${diagnosticId}/interpretation`)
      .then((res) => res.data),
  refetchOnWindowFocus: false,
  retry: false
});

/**
 * Watch the diagnostic interpretations changes
 */
watch(queryDiagnosticInterpretations.data, (interpretations) => {
  // Update the state of the diagnostic store
  if (interpretations) diagnosticStore.setDiagnosticInterpretations(interpretations);
});

/**
 * Query to fetch all diagnostic syndroms
 */
const queryDiagnosticSyndroms = useQuery({
  queryKey: ['diagnosticSyndroms'],
  queryFn: () =>
    axiosInstance.get<Syndrom[]>(`/diagnostic/${diagnosticId}/syndrom`).then((res) => res.data),
  refetchOnWindowFocus: false,
  retry: false
});

/**
 * Watch the diagnostic syndroms changes
 */
watch(queryDiagnosticSyndroms.data, (syndroms) => {
  // Update the state of the diagnostic store
  if (syndroms) diagnosticStore.setDiagnosticSyndroms(syndroms);
});

/**
 * Query to fetch all diagnostic hypothesis
 */
const queryDiagnosticHypothesis = useQuery({
  queryKey: ['diagnosticHypothesis'],
  queryFn: () =>
    axiosInstance.get(`/diagnostic/${diagnosticId}/hypothesis`).then((res) => res.data),
  refetchOnWindowFocus: false,
  retry: false
});

/**
 * Watch the diagnostic hypothesis changes
 */
watch(queryDiagnosticHypothesis.data, (hypothesis) => {
  // Update the state of the diagnostic store
  if (hypothesis) diagnosticStore.setDiagnosticHypothesis(hypothesis);
});

// Add event to diagnostic
const mutationAddEventToDiagnostic = useMutation({
  mutationFn: (addEventDto: AddEventDto) =>
    axiosInstance.post(`/diagnostic/${diagnosticId}/event`, addEventDto).then((res) => res.data),
  onSuccess: (data) => {
    diagnosticStore.setDiagnosticEvents(data);
  }
});

/**
 * Allow to handle the click on an action button and start the corresponding
 * process according to the action type
 * @param typeAction
 */
const handleOnClickActionButton = (typeAction: TypeAction) => {
  switch (typeAction) {
    case TypeAction.SPONTANEOUS_PATIENT_SPEECH: {
      const actionId = diagnosticStore.getSpontaneousPatientSpeechActionId;
      if (actionId) mutationAddEventToDiagnostic.mutate({ typeAction, actionId });
      break;
    }
    case TypeAction.CLOSED_QUESTION: {
      displayClosedQuestionActionPanel.value = true;
      break;
    }
    case TypeAction.OPENED_QUESTION: {
      displayOpenedQuestionActionPanel.value = true;
      break;
    }
    case TypeAction.PRECISION: {
      displayPrecisionActionPanel.value = true;
      break;
    }
    case TypeAction.BIOLOGY: {
      displayPrescriptionActionPanel.value = {
        visibility: true,
        prescriptionType: PrescriptionType.BIOLOGY
      };
      break;
    }
    case TypeAction.IMAGERY: {
      displayPrescriptionActionPanel.value = {
        visibility: true,
        prescriptionType: PrescriptionType.IMAGERY
      };
      break;
    }
    case TypeAction.BIOPSY: {
      displayPrescriptionActionPanel.value = {
        visibility: true,
        prescriptionType: PrescriptionType.BIOPSY
      };
      break;
    }
    case TypeAction.INSPECTION: {
      displayExamActionPanel.value = {
        visibility: true,
        examType: ExamType.INSPECTION
      };
      break;
    }
    case TypeAction.PALPATION: {
      displayExamActionPanel.value = {
        visibility: true,
        examType: ExamType.PALPATION
      };
      break;
    }
    case TypeAction.PERCUSSION: {
      displayExamActionPanel.value = {
        visibility: true,
        examType: ExamType.PERCUSSION
      };
      break;
    }
    case TypeAction.AUSCULTATION: {
      displayExamActionPanel.value = {
        visibility: true,
        examType: ExamType.AUSCULTATION
      };
      break;
    }
    default:
      break;
  }
};

/**
 * Allow to handle the click to ask a closed question and add the event to the diagnostic,
 * and finally close the panel of closed questions
 * @param actionId
 */
const handleOnAskClosedQuestion = (actionId: string) => {
  if (actionId)
    mutationAddEventToDiagnostic.mutate({ typeAction: TypeAction.CLOSED_QUESTION, actionId });
  displayClosedQuestionActionPanel.value = false;
};

/**
 * Allow to handle the click to ask an opened question and add the event to the diagnostic,
 * and finally close the panel of opened questions
 * @param actionId
 */
const handleOnAskOpenedQuestion = (actionId: string) => {
  if (actionId)
    mutationAddEventToDiagnostic.mutate({ typeAction: TypeAction.OPENED_QUESTION, actionId });
  displayOpenedQuestionActionPanel.value = false;
};

const handleOnAskPrescription = (actionId: string) => {
  if (actionId) {
    mutationAddEventToDiagnostic.mutate({ typeAction: TypeAction.PRESCRIPTION, actionId });
  }
  displayPrescriptionActionPanel.value = { visibility: false, prescriptionType: null };
};

const handleOnAskPrecision = (actionId: string) => {
  if (actionId) {
    mutationAddEventToDiagnostic.mutate({ typeAction: TypeAction.PRECISION, actionId });
  }
  displayPrecisionActionPanel.value = false;
};

const handleOnAskExam = (actionId: string, examType: ExamType) => {
  if (actionId) {
    mutationAddEventToDiagnostic.mutate({ typeAction: TypeAction.EXAMEN, actionId });
  }
  displayExamActionPanel.value = { visibility: false, examType: null };
};

const isDiagnosticAlreadyDone = () => {
  return (
    queryDiagnostic.data.value?.status === 'SUCCESS' ||
    queryDiagnostic.data.value?.status === 'FAILURE'
  );
};

onMounted(() => {
  diagnosticStore.reinitializeDiagnostic();
});
</script>

<template>
  <AuthenticatedViewLayout :title="`Diagnostic patient #${virtualPatientId}`">
    <div
      v-if="
        queryDiagnostic.isLoading.value ||
        queryVirtualPatient.isLoading.value ||
        queryDiagnosticEvents.isLoading.value ||
        queryDiagnosticInterpretations.isLoading.value ||
        queryDiagnosticSyndroms.isLoading.value ||
        queryDiagnosticHypothesis.isLoading.value
      "
      class="w-full h-full flex justify-around items-center"
    >
      <span class="loading loading-dots loading-lg"></span>
    </div>

    <div
      v-else-if="
        queryDiagnostic.isError.value ||
        queryVirtualPatient.isError.value ||
        queryDiagnosticEvents.isError.value ||
        queryDiagnosticInterpretations.isError.value ||
        queryDiagnosticSyndroms.isError.value ||
        queryDiagnosticHypothesis.isError.value
      "
      class="w-full h-full flex justify-center items-center"
    >
      <ErrorAlert
        class="w-1/2"
        :message="`Il y a eu une erreur lors du chargement du cas du patient #${virtualPatientId}`"
      />
    </div>

    <div
      v-else-if="isDiagnosticAlreadyDone()"
      class="w-full h-full flex justify-around items-center"
    >
      <ErrorAlert class="w-1/2" message="Ce diagnostic à déjà été effectué" />
    </div>

    <div v-else class="w-full h-full flex flex-row overflow-auto">
      <div class="w-1/2 flex flex-col px-1 py-2">
        <template v-if="displayClosedQuestionActionPanel">
          <ClosedQuestionPanel
            :handleOnClose="() => (displayClosedQuestionActionPanel = false)"
            :handleOnAskClosedQuestion="handleOnAskClosedQuestion"
          />
        </template>
        <template v-else-if="displayOpenedQuestionActionPanel">
          <OpenedQuestionPanel
            :handleOnClose="() => (displayOpenedQuestionActionPanel = false)"
            :handleOnAskOpenedQuestion="handleOnAskOpenedQuestion"
          />
        </template>
        <template v-else-if="displayPrecisionActionPanel">
          <PrecisionPanel
            :handleOnClose="() => (displayPrecisionActionPanel = false)"
            :handleOnAskPrecision="handleOnAskPrecision"
          />
        </template>

        <template
          v-else-if="
            displayPrescriptionActionPanel && displayPrescriptionActionPanel.prescriptionType
          "
        >
          <PrescriptionPanel
            :handleOnClose="
              () => (displayPrescriptionActionPanel = { visibility: false, prescriptionType: null })
            "
            :prescriptionType="displayPrescriptionActionPanel.prescriptionType"
            :handleOnAskPrescription="
              (actionId: string) => {
                handleOnAskPrescription(actionId);
              }
            "
          />
        </template>
        <template v-else-if="displayExamActionPanel && displayExamActionPanel.examType">
          <ExamPanel
            :handleOnClose="() => (displayExamActionPanel = { visibility: false, examType: null })"
            :exam-type="displayExamActionPanel.examType"
            :handle-on-ask-exam="
              (actionId: string) => {
                handleOnAskExam(actionId, ExamType.INSPECTION);
              }
            "
          />
        </template>
        <template v-else>
          <ChatWindow
            class="flex-grow overflow-auto"
            :chatMessages="diagnosticStore.getChatMessages"
          />
          <DiagnosticActions class="mt-2" :handleOnClickActionButton="handleOnClickActionButton" />
        </template>
      </div>
      <div class="w-1/2 h-full flex flex-col px-1 py-2">
        <RaisoningSection :diagnosticId="diagnosticId" />
      </div>
    </div>
  </AuthenticatedViewLayout>
</template>
