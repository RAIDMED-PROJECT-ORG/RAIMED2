import type { Action } from './action.model';
import type { Gender } from './gender.enum';
import type {User} from '@/models/user/user.model';

export interface VirtualPatient {
  id?: string;
  age?: number;
  gender?: Gender;
  createdBy?: User;
  createdAt?: string;
  actions?: Action[];
  result?: string;
}
