import type {PrescriptionType} from '@/models/prescription/prescriptionType.enum';

export interface Prescription {
  id: string;
  content: string;
  result?: string;
  type: PrescriptionType;
}
