export enum QuestionType {
  OPENED = 'OPENED',
  CLOSED = 'CLOSED'
}

export const QuestionTypeDisplayNames: Record<QuestionType, string> = {
  [QuestionType.OPENED]: 'Questions ouvertes',
  [QuestionType.CLOSED]: 'Questions fermées'
};
