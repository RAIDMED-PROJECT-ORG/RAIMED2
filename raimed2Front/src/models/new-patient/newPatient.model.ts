import type { Question } from '@/models/question/question.model';
import type { Characteristics } from '@/models/new-patient/characteristics.model';
import type { ExamResults } from '@/models/diagnostic/exam.model';

export interface NewPatient {
  characteristic: Characteristics | null;
  questions: Question[];
  inspection: ExamResults[];
  palpation: ExamResults[];
  percussion: ExamResults[];
  auscultation: ExamResults[];
}

export const initializeNewPatient = (): NewPatient => {
  return {
    characteristic: null,
    questions: [],
    inspection: [],
    palpation: [],
    percussion: [],
    auscultation: []
  };
};
