export enum PrescriptionType {
  BIOLOGY = 'BIOLOGY',
  IMAGERY = 'IMAGERY',
  BIOPSY = 'BIOPSY'
}

export const PrescriptionTypeDisplayNames: Record<PrescriptionType, string> = {
  [PrescriptionType.BIOLOGY]: 'Biologie / Microbiologie',
  [PrescriptionType.IMAGERY]: 'Imagerie',
  [PrescriptionType.BIOPSY]: 'Biopsie'
};
