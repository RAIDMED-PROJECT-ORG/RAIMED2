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
