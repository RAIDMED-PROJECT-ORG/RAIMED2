import type { Action } from '../virtual-patient/action.model';

export interface Event {
  id: string;
  date: Date;
  action: Action;
}
