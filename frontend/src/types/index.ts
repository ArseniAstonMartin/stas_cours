export interface User {
  id: number
  email: string
  firstName: string
  lastName: string
  avatarUrl?: string
  role: string
  createdAt: string
}

export interface AuthResponse {
  accessToken: string
  refreshToken: string
  tokenType: string
  expiresIn: number
  user: User
}

export interface Subject {
  id: number
  name: string
  description: string
  iconUrl?: string
  topics: Topic[]
}

export interface Topic {
  id: number
  name: string
  description: string
  orderIndex: number
  subjectId: number
  parentTopicId?: number
}

export interface Answer {
  id: number
  text: string
  isCorrect?: boolean
  orderIndex: number
}

export interface Question {
  id: number
  text: string
  explanation?: string
  questionType: string
  difficulty: string
  points: number
  imageUrl?: string
  topicId: number
  topicName: string
  answers: Answer[]
}

export interface Test {
  id: number
  title: string
  description: string
  timeLimitMinutes: number
  testType: string
  isAdaptive: boolean
  questionCount: number
  subjectId: number
  subjectName: string
  createdAt: string
}

export interface UserAnswer {
  id: number
  questionId: number
  questionText: string
  selectedAnswerId?: number
  textAnswer?: string
  isCorrect?: boolean
  timeSpentSeconds?: number
}

export interface TestAttempt {
  id: number
  testId: number
  testTitle: string
  score?: number
  maxScore?: number
  percentage?: number
  timeSpentSeconds?: number
  status: string
  startedAt: string
  completedAt?: string
  userAnswers: UserAnswer[]
}

export interface WeaknessRecord {
  id: number
  topicId: number
  topicName: string
  subjectName: string
  totalAttempts: number
  correctAttempts: number
  accuracyRate: number
  weaknessLevel: string
  lastPracticedAt: string
}

export interface Dashboard {
  totalTestsTaken: number
  averageScore: number
  totalQuestionsAnswered: number
  overallAccuracy: number
  topWeaknesses: WeaknessRecord[]
  subjectAccuracy: Record<string, number>
  recentAttempts: {
    attemptId: number
    testTitle: string
    percentage: number
    status: string
    startedAt: string
  }[]
}

export interface StudyMaterial {
  id: number
  title: string
  content: string
  materialType: string
  externalUrl?: string
  topicId: number
  topicName: string
  orderIndex: number
  createdAt: string
}

export interface Page<T> {
  content: T[]
  totalElements: number
  totalPages: number
  size: number
  number: number
}
