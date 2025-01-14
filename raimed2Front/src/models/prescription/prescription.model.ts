import type {PrescriptionType} from '@/models/prescription/prescriptionType.enum';

export interface Prescription {
    id: string;
    type: PrescriptionType;
    name: string;
    result?: string;
    teacherId?: string;
}
