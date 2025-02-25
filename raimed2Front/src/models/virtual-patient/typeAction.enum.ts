export enum TypeAction {
  SPONTANEOUS_PATIENT_SPEECH = 'SPONTANEOUS_PATIENT_SPEECH',
  CLOSED_QUESTION = 'CLOSED_QUESTION',
  OPENED_QUESTION = 'OPENED_QUESTION',
  PRECISION = 'PRECISION',
  INSPECTION = 'INSPECTION',
  PALPATION = 'PALPATION',
  PERCUSSION = 'PERCUSSION',
  AUSCULTATION = 'AUSCULTATION',
  BIOLOGY = 'BIOLOGY',
  IMAGERY = 'IMAGERY',
  BIOPSY = 'BIOPSY',
  PRESCRIPTION = 'PRESCRIPTION'
}

export const getTypeActionDisplayName = (typeAction: TypeAction): string => {
  switch (typeAction) {
    case TypeAction.SPONTANEOUS_PATIENT_SPEECH:
      return 'Discours spontanée du patient';
    case TypeAction.CLOSED_QUESTION:
      return 'Question fermée';
    case TypeAction.OPENED_QUESTION:
      return 'Question ouverte';
    case TypeAction.PRECISION:
      return 'Préciser un symptôme';
    case TypeAction.INSPECTION:
      return 'Inspection';
    case TypeAction.PALPATION:
      return 'Palpation et manoeuvre';
    case TypeAction.PERCUSSION:
      return 'Percussion';
    case TypeAction.AUSCULTATION:
      return 'Auscultation';
    case TypeAction.BIOLOGY:
      return 'Prescription biologie et microbiologie';
    case TypeAction.IMAGERY:
      return 'Prescription imagerie';
    case TypeAction.BIOPSY:
      return 'Prescription biopsie';
  }
};
