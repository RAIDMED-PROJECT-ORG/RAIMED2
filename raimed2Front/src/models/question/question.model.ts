import type { QuestionType } from '@/models/question/questionType.enum';
import type { QuestionFilter } from '@/models/question/questionFilter.enum';

export interface Question {
  id: string;
  content: string;
  type: QuestionType;
  filter: QuestionFilter;
}
