import { defineStore } from 'pinia';
import type { ChatMessage } from '@/models/chat/chatMessage.model';
import type { VirtualPatient } from '@/models/virtual-patient/virtualPatient.model';
import type { PrimaryElement } from '@/models/diagnostic/primaryElement.model';
import type { Event } from '@/models/diagnostic/event.model';
import type { Interpretation } from '@/models/diagnostic/interpretation.model';
import type { Syndrom } from '@/models/diagnostic/syndrom.model';
import { TypeAction } from '@/models/virtual-patient/typeAction.enum';
import { AuthorChat } from '@/models/chat/authorChat.enum';
import { Gender } from '@/models/virtual-patient/gender.enum';
import type { Hypothesis } from '@/models/diagnostic/hypothesis.model';
import { ZoneDisplayNames, Zones } from '@/models/diagnostic/exam.model';

interface DiagnosticState {
  virtualPatient: VirtualPatient | null;
  diagnosticEvents: Event[];
  diagnosticInterpretations: Interpretation[];
  diagnosticSyndroms: Syndrom[];
  diagnosticHypothesis: Hypothesis[];
}

export const useDiagnosticStore = defineStore('diagnostic', {
  state: (): DiagnosticState => ({
    virtualPatient: null,
    diagnosticEvents: [],
    diagnosticInterpretations: [],
    diagnosticSyndroms: [],
    diagnosticHypothesis: []
  }),

  getters: {
    /**
     * Get the virtual patient details
     * @param state the current state
     * @returns the virtual patient details
     */
    getVirtualPatientDetails: (state): { id?: string; age: number; title: string } => {
      let virtualPatientTitle = '';
      if (state.virtualPatient?.gender) {
        virtualPatientTitle = state.virtualPatient.gender === Gender.MALE ? 'Monsieur' : 'Madame';
      }
      return {
        id: state.virtualPatient?.id,
        age: state.virtualPatient?.age || 0,
        title: virtualPatientTitle
      };
    },

    getSpontaneousPatientSpeechActionId: (state): string | undefined => {
      const spontaneousPatientSpeechActions = state.virtualPatient?.actions?.filter(
        (action) => action.type === TypeAction.SPONTANEOUS_PATIENT_SPEECH
      );
      const spontaneousPatientSpeechEvents = state.diagnosticEvents.filter(
        (event) => event.action.type === TypeAction.SPONTANEOUS_PATIENT_SPEECH
      );
      const availableSpontaneousPatientSpeechActions = spontaneousPatientSpeechActions?.filter(
        (action) => !spontaneousPatientSpeechEvents.find((event) => event.action.id === action.id)
      );

      // If there is at least one available spontaneous patient speech action, return the first one
      if (availableSpontaneousPatientSpeechActions?.length) {
        return availableSpontaneousPatientSpeechActions[0].id;
      }
    },

    isAvailableSpontaneousPatientSpeechAction: (state): boolean => {
      const spontaneousPatientSpeechActions = state.virtualPatient?.actions?.filter(
        (action) => action.type === TypeAction.SPONTANEOUS_PATIENT_SPEECH
      );
      const spontaneousPatientSpeechEvents = state.diagnosticEvents.filter(
        (event: Event) => event.action.type === TypeAction.SPONTANEOUS_PATIENT_SPEECH
      );
      return spontaneousPatientSpeechActions?.length !== spontaneousPatientSpeechEvents.length;
    },

    /**
     * Get the chat messages from the diagnostic events
     * @param state the current state
     * @returns the chat messages
     */
    getChatMessages: (state: DiagnosticState): ChatMessage[] => {
      const chatMessages: ChatMessage[] = [];
      const eventsWithChatMessages = state.diagnosticEvents.filter(
        (event) =>
          event.action.type === TypeAction.SPONTANEOUS_PATIENT_SPEECH ||
          event.action.type === TypeAction.CLOSED_QUESTION ||
          event.action.type === TypeAction.OPENED_QUESTION ||
          event.action.type === TypeAction.PRESCRIPTION ||
          event.action.type === TypeAction.EXAMEN
      );
      eventsWithChatMessages.forEach((event: Event) => {
        if (event.action.type === TypeAction.SPONTANEOUS_PATIENT_SPEECH) {
          chatMessages.push({
            eventId: event.id,
            author: AuthorChat.VIRTUAL_PATIENT,
            content: event.action.speech ?? ''
          });
        } else if (event.action.type === TypeAction.CLOSED_QUESTION) {
          chatMessages.push({
            eventId: event.id,
            author: AuthorChat.STUDENT,
            content: event.action.question?.content ?? ''
          });
          chatMessages.push({
            eventId: event.id,
            author: AuthorChat.VIRTUAL_PATIENT,
            content: event.action.closedAnswer ?? ''
          });
        } else if (event.action.type === TypeAction.OPENED_QUESTION) {
          chatMessages.push({
            eventId: event.id,
            author: AuthorChat.STUDENT,
            content: event.action.question?.content ?? ''
          });
          chatMessages.push({
            eventId: event.id,
            author: AuthorChat.VIRTUAL_PATIENT,
            content: event.action.openedAnswer ?? ''
          });
        } else if (event.action.type === TypeAction.PRESCRIPTION) {
          chatMessages.push({
            eventId: event.id,
            author: AuthorChat.STUDENT,
            content: event.action.prescription?.content ?? ''
          });
          chatMessages.push({
            eventId: event.id,
            author: AuthorChat.VIRTUAL_PATIENT,
            content: event.action.prescription?.result ?? ''
          });
        } else if (event.action.type === TypeAction.EXAMEN) {
          chatMessages.push({
            eventId: event.id,
            author: AuthorChat.STUDENT,
            content: ZoneDisplayNames[event.action.zone as Zones] ?? ''
          });
          chatMessages.push({
            eventId: event.id,
            author: AuthorChat.VIRTUAL_PATIENT,
            content: event.action.signs ?? ''
          });
        }
      });
      return chatMessages;
    },

    /**
     * Get the primary elements of the diagnostic events
     * @param state the current state
     * @returns the primary elements
     */
    getPrimaryElements: (state: DiagnosticState): PrimaryElement[] => {
      const primaryElements: PrimaryElement[] = [];
      state.diagnosticEvents.map((event: Event) => {
        const isExam = event.action.type === TypeAction.EXAMEN;
        if (event.action.primaryElement && isExam) {
          primaryElements.push({ actionId: event.action.id ?? '', value: event.action.signs });
          return;
        }
        if (event.action.primaryElement && event.action.type !== TypeAction.PRESCRIPTION) {
          primaryElements.push({ actionId: event.action.id ?? '', value: event.action.primaryElement });
        }
      });

      return primaryElements;
    },

    /**
     * Get the interpretations of the diagnostic events
     * @param state the current state
     * @returns the interpretations
     */
    getInterpretations: (state: DiagnosticState): Interpretation[] => {
      return state.diagnosticInterpretations;
    },

    /**
     * Get the syndroms of the diagnostic events
     * @param state the current state
     * @returns the syndroms
     */
    getSyndroms: (state: DiagnosticState): Syndrom[] => {
      return state.diagnosticSyndroms;
    },

    /**
     * Get the hypothesis of the diagnostic events
     * @param state the current state
     * @returns the hypothesis
     */
    getHypothesis: (state: DiagnosticState): Hypothesis[] => {
      return state.diagnosticHypothesis;
    }
  },

  actions: {
    setVirtualPatient(virtualPatient: VirtualPatient) {
      this.virtualPatient = virtualPatient;
    },

    setDiagnosticEvents(diagnosticEvents: Event[]) {
      this.diagnosticEvents = diagnosticEvents;
    },

    setDiagnosticInterpretations(diagnosticInterpretations: Interpretation[]) {
      this.diagnosticInterpretations = diagnosticInterpretations;
    },

    setDiagnosticSyndroms(diagnosticSyndroms: Syndrom[]) {
      this.diagnosticSyndroms = diagnosticSyndroms;
    },

    setDiagnosticHypothesis(diagnosticHypothesis: Hypothesis[]) {
      this.diagnosticHypothesis = diagnosticHypothesis;
    }
  }
});
