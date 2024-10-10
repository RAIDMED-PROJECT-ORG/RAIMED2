import type { Role } from './role.enum';

/**
 * Interface for the JWT payload which represents the data of the authentication token
 */
export interface JwtPayload {
  iss: string;
  iat: number;
  exp: number;
  sub: string;
  userId: string;
  firstname: string;
  lastname: string;
  email: string;
  role: Role;
}
