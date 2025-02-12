import type { Gender } from '@/models/virtual-patient/gender.enum';

export interface Characteristics {
  gender: Gender;
  age: number;
  diagnostic: string;
}
