import type { Question } from '@/models/question/question.model';
import type { Characteristics } from '@/models/new-patient/characteristics.model';
import type { ExamResults } from '@/models/diagnostic/exam.model';
import { v4 as uuidv4 } from 'uuid';
import { QuestionType } from '@/models/question/questionType.enum';
import { QuestionFilter } from '@/models/question/questionFilter.enum';
import type { Listen } from '@/models/listen/listen.model';

export interface NewPatient {
  characteristic: Characteristics | null;
  questions: Question[];
  inspection: ExamResults[];
  palpation: ExamResults[];
  percussion: ExamResults[];
  auscultation: ExamResults[];
  listen: Listen[];
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
    listen: [
      {
        id: uuidv4(),
        content: 'Je ne me sens pas bien',
      },
      {
        id: uuidv4(),
        content: 'J\'ai mal à la tête',
      },
      {
        id: uuidv4(),
        content: 'Je suis fatigué',
      },
    ],
  };
};
