export const Color = {
  Blue: '#9ccded',
  Orange: '#f1bf6d',
  Purple: '#d0b1dd',
  Green: '#2db784',
  Grey: '#d9d9d9',
  Red: '#ff9a9b'
} as const;

export type ColorType = (typeof Color)[keyof typeof Color];
