export enum QuestionFilter {
  MIXED = 'MIXED',
  MALE = 'MALE',
  FEMALE = 'FEMALE'
}

export const getQuestionFilterDisplayName = (filter: QuestionFilter): string => {
  switch (filter) {
    case QuestionFilter.MIXED:
      return 'Mixte';
    case QuestionFilter.MALE:
      return 'Homme';
    case QuestionFilter.FEMALE:
      return 'Femme';
  }
};

export const getQuestionFilterByFirstLetter = (filter: string) => {
  switch (filter) {
    case QuestionFilter.MALE:
      return 'H';
    case QuestionFilter.FEMALE:
      return 'F';
    case QuestionFilter.MIXED:
      return 'M';
  }
};
