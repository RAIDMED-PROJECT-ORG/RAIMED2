import type { Question } from '@/models/question/question.model';
import type { Characteristics } from '@/models/new-patient/characteristics.model';
import type { ExamResults } from '@/models/diagnostic/exam.model';
import type { Prescription } from '@/models/prescription/prescription.model';
import type { Listen } from '@/models/listen/listen.model';
import { PrescriptionType } from '@/models/prescription/prescriptionType.enum';
import type { Precision } from '@/models/question/precision.model';
import type { Speech } from '@/models/listen/listen.model';

export interface NewPatient {
  id?: string;
  characteristic: Characteristics | null;
  questions: Question[];
  precisions: Precision[];
  inspection: ExamResults[];
  palpation: ExamResults[];
  percussion: ExamResults[];
  auscultation: ExamResults[];
  listen: Listen[];
  biology: Prescription[];
  imagery: Prescription[];
  biopsy: Prescription[];
}

export const initializeNewPatient = (): NewPatient => {
  return {
    characteristic: null,
    inspection: [],
    palpation: [],
    percussion: [],
    auscultation: [],
    biology: [],
    imagery: [],
    biopsy: [],
    questions:[],
    speech: [],
  };
};
