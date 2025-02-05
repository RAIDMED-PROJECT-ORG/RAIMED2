export interface Characteristics {
  sex: Sex;
  age: number;
  diagnostic: string;
}

export enum Sex {
  MEN = 'MEN',
  WOMAN = 'WOMAN'
}

export const SexDisplayNames: Record<Sex, string> = {
  [Sex.MEN]: 'Homme',
  [Sex.WOMAN]: 'Femme'
}