import { QuestionType } from '@/models/question/questionType.enum';
import { QuestionFilter } from '@/models/question/questionFilter.enum';

export interface Question {
  id: string;
  content: string;
  type: QuestionType;
  filter: QuestionFilter;
  isMutual: boolean;
  answer?: string;
  teacherId?: string | null;
  primaryElement?: string;
}

export enum ClosedQuestionAnswer {
  YES = 'Oui',
  NO = 'Non'
}

export const ClosedQuestionAnswerDisplayNames: Record<ClosedQuestionAnswer, string> = {
  [ClosedQuestionAnswer.YES]: 'Oui',
  [ClosedQuestionAnswer.NO]: 'Non'
};

export const getFakeQuestions = (): Question[] => {
  return [
    {
      id: 'id1',
      content: 'Est-ce que tu as mal au ventre ?',
      answer: 'Oui',
      type: QuestionType.OPENED,
      filter: QuestionFilter.MALE,
      isMutual: false
    },
    {
      id: 'id2',
      content: 'Est-ce que tu as mal à la tête ?',
      answer: 'Oui, très souvent, vers l\'arrière de la tête',
      type: QuestionType.CLOSED,
      filter: QuestionFilter.MALE,
      isMutual: false
    },
    {
      id: 'id3',
      content: 'Est-ce que vous avez déjà eu des problèmes de santé ?',
      answer: 'Oui, un cancer du sein',
      type: QuestionType.OPENED,
      filter: QuestionFilter.FEMALE,
      isMutual: false
    },
    {
      id: 'id4',
      content: 'Consommes-tu de l\'alcool ?',
      answer: 'Non, jamais',
      type: QuestionType.CLOSED,
      filter: QuestionFilter.MIXED,
      isMutual: false
    }
  ];
};
