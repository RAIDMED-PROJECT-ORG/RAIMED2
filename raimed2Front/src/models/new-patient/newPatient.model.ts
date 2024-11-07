import type { Question } from '@/models/question/question.model';
import type { Characteristics } from '@/models/new-patient/characteristics.model';

export interface NewPatient {
  characteristic: Characteristics | null;
  questions: Question[] | null;
}

export const initializeNewPatient = (): NewPatient => {
  return {
    characteristic: null,
    questions: null
  };
};
