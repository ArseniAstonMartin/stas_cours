import apiClient from './client'
import type { Test, TestAttempt, Question, Page } from '../types'

export const testsApi = {
  getAll: (params?: { subjectId?: number; page?: number; size?: number }) =>
    apiClient.get<Page<Test>>('/api/v1/tests', { params }).then((r) => r.data),

  getById: (id: number) =>
    apiClient.get<Test>(`/api/v1/tests/${id}`).then((r) => r.data),

  getQuestions: (params?: { topicId?: number; difficulty?: string; page?: number; size?: number }) =>
    apiClient.get<Page<Question>>('/api/v1/questions', { params }).then((r) => r.data),

  startAttempt: (testId: number) =>
    apiClient.post<TestAttempt>(`/api/v1/attempts/start/${testId}`).then((r) => r.data),

  submitAnswer: (attemptId: number, data: { questionId: number; selectedAnswerId?: number; textAnswer?: string; timeSpentSeconds?: number }) =>
    apiClient.post<TestAttempt>(`/api/v1/attempts/${attemptId}/answer`, data).then((r) => r.data),

  completeAttempt: (attemptId: number) =>
    apiClient.post<TestAttempt>(`/api/v1/attempts/${attemptId}/complete`).then((r) => r.data),

  getMyAttempts: (params?: { page?: number; size?: number }) =>
    apiClient.get<Page<TestAttempt>>('/api/v1/attempts/my', { params }).then((r) => r.data),

  getAttemptById: (id: number) =>
    apiClient.get<TestAttempt>(`/api/v1/attempts/${id}`).then((r) => r.data),
}
