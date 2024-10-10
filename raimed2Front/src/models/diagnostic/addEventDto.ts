import type { TypeAction } from '../virtual-patient/typeAction.enum';

export interface AddEventDto {
  typeAction: TypeAction;
  actionId: string;
}
