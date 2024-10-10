import type { Interpretation } from './interpretation.model';
import type { PrimaryElement } from './primaryElement.model';
import type { Syndrom } from './syndrom.model';

export interface AddHypothesisto {
  value: string;
  primaryElements: PrimaryElement[];
  interpretations: Interpretation[];
  syndroms: Syndrom[];
}
