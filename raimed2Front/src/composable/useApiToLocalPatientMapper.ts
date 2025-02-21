import type { VirtualPatient } from '@/models/virtual-patient/virtualPatient.model';
import type { NewPatient } from '@/models/new-patient/newPatient.model';
import { Gender } from '@/models/virtual-patient/gender.enum';
import type { Action } from '@/models/virtual-patient/action.model';
import type { Question } from '@/models/question/question.model';
import { TypeAction } from '@/models/virtual-patient/typeAction.enum';
import { QuestionType } from '@/models/question/questionType.enum';
import { QuestionFilter } from '@/models/question/questionFilter.enum';
import type { Listen } from '@/models/listen/listen.model';

export function useApiToLocalPatientMapper() {
  function mapApiToLocal(patient: VirtualPatient): NewPatient {
    return {
      ...(patient.id !== undefined && { id: patient.id }),
      characteristic: {
        age: patient.age ?? 0,
        gender: patient.gender ?? Gender.MALE,
        diagnostic: patient.result ?? ''
      },
      questions: [...mapApiActionToLocalQuestion(patient.actions ?? [])],
      listen: [...mapApiActionToLocalListen(patient.actions ?? [])],
      inspection: [],
      palpation: [],
      percussion: [],
      auscultation: [],
      biology: [],
      imagery: [],
      biopsy: [],
      precisions: [],
    };
  }

  function mapApiActionToLocalQuestion(actions: Action[]): Question[] {
    const questions: Question[] = [];

    actions.forEach((action) => {
      if (action.type === TypeAction.CLOSED_QUESTION || action.type === TypeAction.OPENED_QUESTION) {
        questions.push({
          id: action.question?.id ?? '',
          content: action.question?.content ?? '',
          answer: action.closedAnswer ?? action.openedAnswer ?? '',
          type: action.type === TypeAction.CLOSED_QUESTION ? QuestionType.CLOSED : QuestionType.OPENED,
          filter: action.question?.filter ?? QuestionFilter.MIXED,
          isMutual: action.question?.isMutual ?? false
        });
      }
    });

    return questions;
  }

  function mapApiActionToLocalListen(actions: Action[]): Listen[] {
    const listen: Listen[] = [];

    actions.forEach((action) => {
      if (action.type === TypeAction.CLOSED_QUESTION || action.type === TypeAction.OPENED_QUESTION) {
        listen.push({
          id: action.question?.id ?? '',
          content: action.primaryElement,
        });
      }
    });

    return listen;
  }

  return { mapApiToLocal };
}
