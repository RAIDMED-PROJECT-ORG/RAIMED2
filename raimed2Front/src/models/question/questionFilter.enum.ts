export enum QuestionFilter {
  MIXED = 'MIXED',
  MALE = 'MALE',
  FEMALE = 'FEMALE'
}

export const QuestionFilterDisplayNames: Record<QuestionFilter, string> = {
  [QuestionFilter.MIXED]: 'Mixte',
  [QuestionFilter.MALE]: 'Homme',
  [QuestionFilter.FEMALE]: 'Femme'
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
