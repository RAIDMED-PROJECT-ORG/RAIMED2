export enum PrescriptionType {
    BIOLOGY = 'BIOLOGY',
    IMAGERY = 'IMAGERY',
    BIOPSY = 'BIOPSY',
}

export const getPrescriptionTypeDisplayName = (type: PrescriptionType): string => {
    switch (type) {
        case PrescriptionType.BIOLOGY:
            return 'Biologie / Microbiologie';
        case PrescriptionType.IMAGERY:
            return 'Imagerie';
        case PrescriptionType.BIOPSY:
            return 'Biopsie';
    }
};
