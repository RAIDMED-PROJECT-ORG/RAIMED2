import type { Question } from '@/models/question/question.model';
import type { Characteristics } from '@/models/new-patient/characteristics.model';
import { v4 as uuidv4 } from 'uuid';
import { QuestionType } from '@/models/question/questionType.enum';
import { QuestionFilter } from '@/models/question/questionFilter.enum';
import {PrescriptionType} from '@/models/prescription/prescriptionType.enum';
import type {Prescription} from '@/models/prescription/prescription.model';
import type {ExamResults} from '@/models/diagnostic/exam.model';

export interface NewPatient {
  characteristic: Characteristics | null;
  questions: Question[] | null;
  inspection: ExamResults[];
  palpation: ExamResults[];
  percussion: ExamResults[];
  auscultation: ExamResults[];
  prescriptions: Prescription[] | null;
}

export const initializeNewPatient = (): NewPatient => {

  return {
    characteristic: null,
    inspection: [],
    palpation: [],
    percussion: [],
    auscultation: [],
    questions: [
      {
        id: uuidv4(),
        content: 'Quels sont vos antécédents familiaux ?',
        answer: 'De multiples cancers',
        type: QuestionType.OPENED,
        filter: QuestionFilter.FEMALE,
        isMutual: false
      },
      {
        id: uuidv4(),
        content: 'Quels sont vos antécédents familiaux ?',
        answer: 'De multiples cancers',
        type: QuestionType.OPENED,
        filter: QuestionFilter.MIXED,
        isMutual: false
      },
      {
        id: uuidv4(),
        content: 'Êtes-vous malade ?',
        answer: 'Oui',
        type: QuestionType.CLOSED,
        filter: QuestionFilter.MALE,
        isMutual: false
      }
    ],
    prescriptions: [
      {
        id: '1',
        type: PrescriptionType.BIOLOGY,
        name: 'Examen de sang',
        result: 'RAS',
        teacherId: '1',
        isMutual: false,
      },
      {
        id: '3',
        type: PrescriptionType.BIOPSY,
        name: 'Cytoponction',
        result: 'RAS',
        teacherId: '1',
        isMutual: false,
      },
    ]
  };
};
