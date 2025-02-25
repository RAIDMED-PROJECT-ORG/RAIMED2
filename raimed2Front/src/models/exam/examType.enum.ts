export enum ExamType {
  INSPECTION = 'INSPECTION',
  PALPATION = 'PALPATION',
  PERCUSSION = 'PERCUSSION',
  AUSCULTATION = 'AUSCULTATION'
}

export const ExamTypeDisplayNames: Record<ExamType, string> = {
  [ExamType.INSPECTION]: 'Inspection',
  [ExamType.PALPATION]: 'Palpation',
  [ExamType.PERCUSSION]: 'Percussion',
  [ExamType.AUSCULTATION]: 'Auscultation'
};
