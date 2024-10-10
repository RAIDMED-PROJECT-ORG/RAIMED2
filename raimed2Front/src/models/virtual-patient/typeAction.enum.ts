export enum TypeAction {
  SPONTANEOUS_PATIENT_SPEECH = 'SPONTANEOUS_PATIENT_SPEECH',
  CLOSED_QUESTION = 'CLOSED_QUESTION',
  OPENED_QUESTION = 'OPENED_QUESTION',
  SPECIFY_SYMPTOM = 'SPECIFY_SYMPTOM',
  INSPECTION = 'INSPECTION',
  PALPATATION = 'PALPATATION',
  PERCUSSION = 'PERCUSSION',
  AUSCULTATION = 'AUSCULTATION',
  BIOLOGY_MICROBIOLOGY_PRESCRIPTION = 'BIOLOGY_MICROBIOLOGY_PRESCRIPTION',
  IMAGING_PRESCRIPTION = 'IMAGING_PRESCRIPTION',
  BIOPSIES_PRESCRIPTION = 'BIOPSIES_PRESCRIPTION'
}

export const getTypeActionDisplayName = (typeAction: TypeAction): string => {
  switch (typeAction) {
    case TypeAction.SPONTANEOUS_PATIENT_SPEECH:
      return 'Discours spontanée du patient';
    case TypeAction.CLOSED_QUESTION:
      return 'Question fermée';
    case TypeAction.OPENED_QUESTION:
      return 'Question ouverte';
    case TypeAction.SPECIFY_SYMPTOM:
      return 'Préciser un symptôme';
    case TypeAction.INSPECTION:
      return 'Inspection';
    case TypeAction.PALPATATION:
      return 'Palpation et manoeuvres';
    case TypeAction.PERCUSSION:
      return 'Percussion';
    case TypeAction.AUSCULTATION:
      return 'Auscultation';
    case TypeAction.BIOLOGY_MICROBIOLOGY_PRESCRIPTION:
      return 'Prescription biologie et microbiologie';
    case TypeAction.IMAGING_PRESCRIPTION:
      return 'Prescription imagerie';
    case TypeAction.BIOPSIES_PRESCRIPTION:
      return 'Prescription biopsies';
  }
};
