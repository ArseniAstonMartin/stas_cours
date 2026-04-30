import apiClient from './client'
import type { Dashboard, WeaknessRecord } from '../types'

export const dashboardApi = {
  get: () =>
    apiClient.get<Dashboard>('/api/v1/dashboard').then((r) => r.data),

  getWeaknesses: () =>
    apiClient.get<WeaknessRecord[]>('/api/v1/weaknesses').then((r) => r.data),

  getCriticalWeaknesses: () =>
    apiClient.get<WeaknessRecord[]>('/api/v1/weaknesses/critical').then((r) => r.data),
}
