import type { ExamType } from '@/models/exam/examType.enum';

export interface Exam {
  id: string;
  zone: string;
  signs: string;
  type: ExamType;
}
