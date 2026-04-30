import apiClient from './client'
import type { Subject, StudyMaterial } from '../types'

export const subjectsApi = {
  getAll: () =>
    apiClient.get<Subject[]>('/api/v1/subjects').then((r) => r.data),

  getById: (id: number) =>
    apiClient.get<Subject>(`/api/v1/subjects/${id}`).then((r) => r.data),

  getMaterials: (topicId: number) =>
    apiClient.get<StudyMaterial[]>('/api/v1/materials', { params: { topicId } }).then((r) => r.data),
}
