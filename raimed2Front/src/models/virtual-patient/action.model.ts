import type { Question } from '../question/question.model';
import type { TypeAction } from './typeAction.enum';

export interface Action {
  id?: string;
  type: TypeAction;
  primaryElement: string;

  // SPONTANEOUS_PATIENT_SPEECH
  speech?: string;

  // CLOSED_QUESTION
  closedAnswer?: string;

  // OPENED_QUESTION
  openedAnswer?: string;

  question?: Question;
}
