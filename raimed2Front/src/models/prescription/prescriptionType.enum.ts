export enum PrescriptionType {
    BIOLOGY = 'BIOLOGY',
    IMAGING = 'IMAGING',
    BIOPSY = 'BIOPSY',
}

export const getPrescriptionTypeDisplayName = (type: PrescriptionType): string => {
    switch (type) {
        case PrescriptionType.BIOLOGY:
            return 'Biologie / Microbiologie';
        case PrescriptionType.IMAGING:
            return 'Imagerie';
        case PrescriptionType.BIOPSY:
            return 'Biopsie';
    }
};
