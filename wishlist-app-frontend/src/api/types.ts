export enum HttpMethod {
  GET = 'get',
  POST = 'post',
  PUT = 'put',
  PATCH = 'patch',
  DELETE = 'delete',
}
export interface ApiError {
  field?: string;
  message: string;
}

export interface ApiResponse<T> {
  data: T | null;
  error: ApiError | null;
  status: number;
  success: boolean;
}
