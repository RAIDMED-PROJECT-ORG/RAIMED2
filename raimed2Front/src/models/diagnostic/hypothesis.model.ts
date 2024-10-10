import type { Interpretation } from './interpretation.model';
import type { PrimaryElement } from './primaryElement.model';
import type { Syndrom } from './syndrom.model';

export interface Hypothesis {
  id: string;
  value: string;
  primaryElements: PrimaryElement[];
  interpretations: Interpretation[];
  syndroms: Syndrom[];
}

export interface HypothesisElements {
  primaryElements: PrimaryElement[];
  interpretations: Interpretation[];
  syndroms: Syndrom[];
}
