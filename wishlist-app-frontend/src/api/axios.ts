// src/api/axios.ts
import axios, { type AxiosError, type AxiosInstance, type AxiosRequestConfig, type AxiosResponse } from 'axios';
import { type ApiError, type ApiResponse, HttpMethod } from './types.ts';

const AUTH_BASE_URL = import.meta.env.VITE_API_AUTH_URL;

const createApiClient = (baseUrl: string): AxiosInstance =>
  axios.create({
    baseURL: baseUrl,
    withCredentials: true,
    headers: {
      'Content-Type': 'application/json',
      Accept: 'application/json',
    },
    timeout: 15000,
  });

const authApi = createApiClient(AUTH_BASE_URL);

// REQUEST INTERCEPTOR – np. do podpinania tokena
function attachCommonInterceptors(client: AxiosInstance) {
  client.interceptors.request.use(
    config => {
      // miejsce na CSRF, trace-id itd.
      return config;
    },
    error => Promise.reject(error),
  );

  client.interceptors.response.use(
    response => response,
    error => {
      if (error.response?.status === 401) {
        // tu możesz np. globalnie reagować na wygaśnięcie sesji
        // window.location.href = '/login';
      }
      return Promise.reject(error);
    },
  );
}

attachCommonInterceptors(authApi);

const toApiResponse = <T>(response: AxiosResponse<T>): ApiResponse<T> => ({
  data: response.data,
  error: null,
  status: response.status,
  success: response.status >= 200 && response.status < 300,
});

export interface ApiErrorBody {
  errorField?: string;
  errorMessage?: string;
}

const toErrorResponse = <T = never>(error: unknown): ApiResponse<T> => {
  // sprawdzamy, czy to błąd z axiosa (HTTP, sieć itd.)
  if (axios.isAxiosError<ApiErrorBody | string>(error)) {
    const axiosError = error as AxiosError<ApiErrorBody | string>;
    const status = axiosError.response?.status ?? 0;
    const body = axiosError.response?.data;

    let apiError: ApiError = {
      message: 'Unexpected error',
    };

    if (typeof body === 'string') {
      // na wszelki wypadek, gdyby backend kiedyś zwrócił sam string
      apiError = { message: body };
    } else if (body && typeof body === 'object') {
      apiError = {
        field: body.errorField,
        message: body.errorMessage ?? 'Unexpected error',
      };
    } else if (axiosError.message) {
      apiError = { message: axiosError.message };
    }

    return {
      data: null,
      error: apiError,
      status,
      success: false,
    };
  }

  // inne błędy (np. runtime error w kodzie frontu)
  return {
    data: null,
    error: {
      message: 'Unknown error',
    },
    status: 0,
    success: false,
  };
};

async function requestWithClient<T = unknown>(
  client: AxiosInstance,
  config: AxiosRequestConfig,
): Promise<ApiResponse<T>> {
  try {
    const response = await client.request<T>(config);
    return toApiResponse(response);
  } catch (error) {
    return toErrorResponse<T>(error);
  }
}

// Helpery dla najczęstszych metod

export const authRequest = <T = unknown, B = unknown>(
  method: HttpMethod,
  url: string,
  data?: B,
  config?: AxiosRequestConfig,
): Promise<ApiResponse<T>> =>
  requestWithClient<T>(authApi, {
    ...config,
    method,
    data,
    url,
  });

export default authApi;
