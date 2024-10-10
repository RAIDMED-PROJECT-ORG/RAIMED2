import type { Role } from '../auth/role.enum';

export interface User {
  id: string;
  username: string;
  firstname: string;
  lastname: string;
  email: string;
  role: Role;
}
