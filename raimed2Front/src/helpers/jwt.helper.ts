import type { JwtPayload } from '@/models/auth/JwtPayload.model';
import type { User } from '@/models/user/user.model';
import { jwtDecode } from 'jwt-decode';

/**
 * This helper function extracts the user from the JWT token.
 * @param token the JWT token
 * @returns the user object retrieved from the JWT token claims
 */
export const extractUserFromJwt = (token: string): User => {
  const decodedToken = jwtDecode<JwtPayload>(token);
  return {
    id: decodedToken.userId,
    username: decodedToken.sub,
    firstname: decodedToken.firstname,
    lastname: decodedToken.lastname,
    email: decodedToken.email,
    role: decodedToken.role
  };
};
