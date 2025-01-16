import type { Question } from '@/models/question/question.model';
import type { Characteristics } from '@/models/new-patient/characteristics.model';
import { v4 as uuidv4 } from 'uuid';
import { QuestionType } from '@/models/question/questionType.enum';
import { QuestionFilter } from '@/models/question/questionFilter.enum';

export interface NewPatient {
  characteristic: Characteristics | null;
  questions: Question[] | null;
}

export const initializeNewPatient = (): NewPatient => {
  return {
    characteristic: null,
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
    ]
  };
};
