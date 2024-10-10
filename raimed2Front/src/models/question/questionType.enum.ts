export enum QuestionType {
  OPENED = 'OPENED',
  CLOSED = 'CLOSED'
}

export const getQuestionTypeDisplayName = (type: QuestionType): string => {
  switch (type) {
    case QuestionType.OPENED:
      return 'Questions ouvertes';
    case QuestionType.CLOSED:
      return 'Questions fermées';
  }
};
