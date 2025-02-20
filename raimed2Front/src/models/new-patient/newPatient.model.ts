import type { Question } from '@/models/question/question.model';
import type { Characteristics } from '@/models/new-patient/characteristics.model';
import type { ExamResults } from '@/models/diagnostic/exam.model';
import { v4 as uuidv4 } from 'uuid';
import { QuestionType } from '@/models/question/questionType.enum';
import { QuestionFilter } from '@/models/question/questionFilter.enum';
import type { Prescription } from '@/models/prescription/prescription.model';
import type { Listen } from '@/models/listen/listen.model';
import { PrescriptionType } from '@/models/prescription/prescriptionType.enum';

export interface NewPatient {
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
    biology: [
      {
        id: uuidv4(),
        content: 'Bilan hépatique',
        result: 'Bilan hépatique normal',
        type: PrescriptionType.BIOLOGY
      },
      {
        id: uuidv4(),
        content: 'Cytoponction foie',
        result: 'Cellules sans signe de malginité',
        type: PrescriptionType.BIOLOGY
      }
    ],
    imagery: [],
    biopsy: [],
    questions: [
      {
        id: uuidv4(),
        content: 'Quels sont vos antécédents familiaux ?',
        answer: 'De multiples cancers',
        primaryElement: 'Cancer',
        type: QuestionType.OPENED,
        filter: QuestionFilter.MIXED,
        isMutual: false
      },
      {
        id: uuidv4(),
        content: 'Êtes-vous malade ?',
        answer: 'Oui',
        primaryElement: 'Malade',
        type: QuestionType.CLOSED,
        filter: QuestionFilter.MALE,
        isMutual: false
      }
    ],
    listen: [
      {
        id: uuidv4(),
        content: 'Je ne me sens pas bien'
      },
      {
        id: uuidv4(),
        content: 'J\'ai mal à la tête',
        primaryElement: 'Mal de tête'
      },
      {
        id: uuidv4(),
        content: 'Je suis fatigué',
        primaryElement: 'Fatigue'
      }
    ],
    precisions: []
  };
}