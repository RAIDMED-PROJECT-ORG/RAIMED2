import type { Question } from '../question/question.model';
import type { TypeAction } from './typeAction.enum';
import type { Prescription } from '@/models/prescription/prescription.model';
import type { Precision } from '@/models/question/precision.model';

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

  prescription?: Prescription;

  zone: string;

  signs: string;

  precision?: Precision;
}
