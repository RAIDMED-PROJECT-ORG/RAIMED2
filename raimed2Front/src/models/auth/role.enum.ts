export enum Role {
  STUDENT = 'STUDENT',
  TEACHER = 'TEACHER',
  ADMIN = 'ADMIN'
}

export const getRoleDisplayName = (role: Role | undefined): string => {
  switch (role) {
    case Role.STUDENT:
      return 'Étudiant';
    case Role.TEACHER:
      return 'Professeur';
    case Role.ADMIN:
      return 'Administrateur';
    default:
      return '';
  }
};
