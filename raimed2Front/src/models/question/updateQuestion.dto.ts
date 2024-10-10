import type { QuestionFilter } from '@/models/question/questionFilter.enum';
import type { QuestionType } from '@/models/question/questionType.enum';

export interface UpdateQuestionDto {
  content: string;
  filter: QuestionFilter;
  type: QuestionType;
}
