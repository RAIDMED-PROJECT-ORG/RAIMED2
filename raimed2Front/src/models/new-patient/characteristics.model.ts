import type { Gender } from '@/models/virtual-patient/gender.enum';

export interface Characteristics {
  sex: Gender;
  age: number;
  diagnostic: string;
}
