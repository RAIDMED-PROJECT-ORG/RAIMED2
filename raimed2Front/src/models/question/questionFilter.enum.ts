import { Gender, GenderDisplayNames } from '@/models/virtual-patient/gender.enum';

export enum QuestionFilter {
  MIXED = 'MIXED',
  MALE = Gender.MALE,
  FEMALE = Gender.FEMALE
}

export const QuestionFilterDisplayNames: Record<QuestionFilter, string> = {
  [QuestionFilter.MIXED]: 'Mixte',
  [QuestionFilter.MALE]: GenderDisplayNames[QuestionFilter.MALE],
  [QuestionFilter.FEMALE]: GenderDisplayNames[QuestionFilter.FEMALE]
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
