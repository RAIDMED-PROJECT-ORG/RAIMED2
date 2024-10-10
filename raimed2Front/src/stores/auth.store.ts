import axiosInstance from '@/service/httpClient/axios.config';
import { extractUserFromJwt } from '@/helpers/jwt.helper';
import { defineStore } from 'pinia';
import type { User } from '@/models/user/user.model';
import type { Role } from '@/models/auth/role.enum';

// Define the authentication store state interface
interface AuthState {
  user: User | null;
  accessToken: string | null;
  isAuthenticated: boolean;
  isInitialized: boolean;
}

export const useAuthStore = defineStore('auth', {
  state: (): AuthState => ({
    user: null,
    accessToken: null,
    isAuthenticated: false,
    isInitialized: false
  }),

  getters: {
    // Getter to check if the current user is authenticated
    isCurrentUserAuthenticated(): boolean {
      return this.isAuthenticated && this.user !== null && this.accessToken !== null;
    },

    // Getter to get the current user info
    getUserInfo(): Partial<User> {
      if (!this.user) return { firstname: '', lastname: '' };
      return {
        id: this.user.id,
        firstname: this.user.firstname,
        lastname: this.user.lastname,
        role: this.user.role
      };
    },

    // Getter to get the current user role
    getUserRole(): Role | null {
      return this.user?.role ?? null;
    }
  },

  actions: {
    /**
     * This function initializes the store by refreshing the token
     * @returns Promise<void> a promise representing the initialization of the store
     */
    async initialize(): Promise<void> {
      return new Promise((resolve, reject) => {
        this.refresh()
          .then(() => resolve())
          .catch(() => reject())
          .finally(() => (this.isInitialized = true));
      });
    },

    /**
     * This function performs the login request to the API and stores the user data and the access token in the store if successful
     * @param username
     * @param password
     * @returns Promise<void> a promise representing the login request
     */
    async login(username: string, password: string): Promise<void> {
      return axiosInstance.post('/auth/login', { username, password }).then((res) => {
        if (res && res.status === 200) {
          this.user = extractUserFromJwt(res.data.accessToken);
          this.accessToken = res.data.accessToken;
          this.isAuthenticated = true;
        }
      });
    },

    /**
     * This function performs the refresh token request to the API and stores the user data and the access token in the store if successful
     * @returns Promise<void> a promise representing the refresh token request
     */
    async refresh(): Promise<void> {
      return new Promise((resolve, reject) => {
        axiosInstance
          .post('/auth/refresh')
          .then((res) => {
            if (res && res.status === 200) {
              this.user = extractUserFromJwt(res.data.accessToken);
              this.accessToken = res.data.accessToken;
              this.isAuthenticated = true;
            }
            resolve();
          })
          .catch(() => {
            this.user = null;
            this.accessToken = null;
            this.isAuthenticated = false;
            reject();
          });
      });
    },

    /**
     * This function performs the logout request to the API and reset the state of the store
     * @returns Promise<void> a promise representing the logout request
     */
    async logout(): Promise<void> {
      return axiosInstance
        .post('/auth/logout')
        .then(() => {
          this.user = null;
          this.accessToken = null;
          this.isAuthenticated = false;
        })
        .catch(() => {
          this.user = null;
          this.accessToken = null;
          this.isAuthenticated = false;
        });
    }
  }
});
