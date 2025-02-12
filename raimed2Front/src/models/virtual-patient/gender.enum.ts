export enum Gender {
  MALE = 'MALE',
  FEMALE = 'FEMALE'
}

export const GenderDisplayNames: Record<Gender, string> = {
  [Gender.MALE]: 'Homme',
  [Gender.FEMALE]: 'Femme'
}