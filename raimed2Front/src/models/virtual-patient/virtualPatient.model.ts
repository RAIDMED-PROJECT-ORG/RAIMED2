import type { Action } from './action.model';
import type { Gender } from './gender.enum';

export interface VirtualPatient {
  id: string;
  age?: number;
  gender?: Gender;
  createdBy: string;
  createdAt?: Date;
  actions?: Action[];
  result: string;
}
