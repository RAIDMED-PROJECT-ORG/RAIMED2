import axios, { AxiosError, type AxiosRequestConfig, type AxiosResponse } from 'axios';
import { useAuthStore } from '@/stores/auth.store';
import { useRouter } from 'vue-router';

/**
 * Create Axios instance to be used for all HTTP requests
 */
const axiosInstance = axios.create({
  baseURL: `${import.meta.env.VITE_API_BASE_URL}`,
  withCredentials: true
});

/**
 * Set Axios request interceptor that is called before each HTTP request
 */
axiosInstance.interceptors.request.use(
  (config) => {
    config.headers['Authorization'] = `Bearer ${useAuthStore().accessToken}`;
    return config;
  },
  (error) => Promise.reject(error)
);

// Define a type for the retry request queue item
interface RetryQueueItem {
  resolve: (res?: AxiosResponse) => void;
  reject: (error?: AxiosError) => void;
  config: AxiosRequestConfig;
}

// Create the list to hold the retry request queue
let requestQueue: RetryQueueItem[] = [];

// Create the flag to control the refresh status
let isRefreshing = false;

/**
 * Set Axios response interceptor that is called after each HTTP response
 */
axiosInstance.interceptors.response.use(
  (response) => response,
  async (error) => {
    const authStore = useAuthStore();

    const originalRequest = error.config;

    if (
      error.response.status === 401 &&
      (error.response.data.details === 'Expired access token' ||
        error.response.data.details === 'Invalid access token') &&
      !originalRequest._retry &&
      originalRequest.url !== '/auth/login' &&
      originalRequest.url !== '/auth/refresh'
    ) {
      // Add the retry flag to the request config as it is going to be retried only once
      originalRequest._retry = true;

      if (isRefreshing) {
        // If the refresh token request is already running, add the request to the queue
        return new Promise((resolve, reject) =>
          requestQueue.push({ resolve, reject, config: originalRequest })
        );
      } else {
        // If the refresh token request is not running, set the flag to true
        isRefreshing = true;

        // Call the refresh token request
        return authStore
          .refresh()
          .then(() => {
            // Retry all requests in the queue with the new token
            requestQueue.forEach(({ resolve, reject, config }) => {
              axiosInstance
                .request(config)
                .then((response) => resolve(response))
                .catch((err) => reject(err));
            });

            // Retry the original request
            return axiosInstance(originalRequest);
          })
          .catch(() => Promise.reject(error))
          .finally(() => {
            // Reset the queue array
            requestQueue = [];
            // Reset the refresh flag
            isRefreshing = false;
          });
      }
    }
    return Promise.reject(error);
  }
);

export default axiosInstance;
