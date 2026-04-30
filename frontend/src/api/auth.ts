import apiClient from './client'
import type { AuthResponse } from '../types'

export const authApi = {
  register: (data: { email: string; password: string; firstName: string; lastName: string }) =>
    apiClient.post<AuthResponse>('/api/v1/auth/register', data).then((r) => r.data),

  login: (data: { email: string; password: string }) =>
    apiClient.post<AuthResponse>('/api/v1/auth/login', data).then((r) => r.data),

  refresh: (refreshToken: string) =>
    apiClient.post<AuthResponse>('/api/v1/auth/refresh', { refreshToken }).then((r) => r.data),
}
