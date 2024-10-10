import type { Interpretation } from './interpretation.model';
import type { PrimaryElement } from './primaryElement.model';

export interface AddSyndromDto {
  value: string;
  primaryElements: PrimaryElement[];
  interpretations: Interpretation[];
}
