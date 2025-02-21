export interface Listen {
  id: string;
  content: string;
  primaryElement?: string;
}

export const getFakeListens = (): Listen[] => {
  return [
    {
      id: 'id1',
      content: 'Je suis malade'
    },
    {
      id: 'id2',
      content: 'Je suis fatigué'
    },
    {
      id: 'id3',
      content: 'Je suis en colère'
    },
    {
      id: 'id4',
      content: 'Je suis triste'
    },
    {
      id: 'id5',
      content: 'Je suis heureux'
    },
    {
      id: 'id6',
      content: 'Je suis stressé'
    },
    {
      id: 'id7',
      content: 'Je suis anxieux'
    },
    {
      id: 'id8',
      content: 'Je suis déprimé'
    },
    {
      id: 'id9',
      content: 'Je suis énervé'
    },
    {
      id: 'id10',
      content: 'Je suis joyeux'
    },
    {
      id: 'id11',
      content: 'Je suis excité'
    },
    {
      id: 'id12',
      content: 'Je suis détendu'
    }
  ];
};
