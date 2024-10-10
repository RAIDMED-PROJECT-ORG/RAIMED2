import type { Interpretation } from './interpretation.model';
import type { PrimaryElement } from './primaryElement.model';

export interface Syndrom {
  id: string;
  value: string;
  primaryElements: PrimaryElement[];
  interpretations: Interpretation[];
}

export interface SyndromElements {
  primaryElements: PrimaryElement[];
  interpretations: Interpretation[];
}
