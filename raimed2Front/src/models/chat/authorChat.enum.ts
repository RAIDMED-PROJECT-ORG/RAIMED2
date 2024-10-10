export enum AuthorChat {
  VIRTUAL_PATIENT = 'VIRTUAL_PATIENT',
  STUDENT = 'STUDENT'
}

export const getAuthorChatDisplayName = (authorChat: AuthorChat): string => {
  switch (authorChat) {
    case AuthorChat.VIRTUAL_PATIENT:
      return 'Patient virtuel';
    case AuthorChat.STUDENT:
      return 'Étudiant';
    default:
      return '';
  }
};
