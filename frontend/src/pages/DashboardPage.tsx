import { useQuery } from '@tanstack/react-query'
import { dashboardApi } from '../api/dashboard'
import { FiCheckCircle, FiPercent, FiHelpCircle, FiAlertTriangle } from 'react-icons/fi'

export default function DashboardPage() {
  const { data: dashboard, isLoading } = useQuery({
    queryKey: ['dashboard'],
    queryFn: dashboardApi.get,
  })

  if (isLoading) return <div className="text-center py-16 text-gray-500">Загрузка...</div>
  if (!dashboard) return <div className="text-center py-16 text-gray-500">Нет данных</div>

  const stats = [
    { icon: FiCheckCircle, label: 'Тестов пройдено', value: dashboard.totalTestsTaken, color: 'text-green-600' },
    { icon: FiPercent, label: 'Средний балл', value: `${dashboard.averageScore.toFixed(1)}%`, color: 'text-indigo-600' },
    { icon: FiHelpCircle, label: 'Вопросов решено', value: dashboard.totalQuestionsAnswered, color: 'text-blue-600' },
    { icon: FiAlertTriangle, label: 'Общая точность', value: `${dashboard.overallAccuracy.toFixed(1)}%`, color: 'text-amber-600' },
  ]

  return (
    <div className="space-y-8">
      <h1 className="text-3xl font-bold text-gray-900">Мой прогресс</h1>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
        {stats.map(({ icon: Icon, label, value, color }) => (
          <div key={label} className="bg-white rounded-xl p-6 shadow-sm border border-gray-100">
            <Icon className={`text-2xl ${color} mb-2`} />
            <p className="text-sm text-gray-500">{label}</p>
            <p className="text-2xl font-bold text-gray-900">{value}</p>
          </div>
        ))}
      </div>

      {dashboard.topWeaknesses.length > 0 && (
        <div className="bg-white rounded-xl p-6 shadow-sm border border-gray-100">
          <h2 className="text-lg font-semibold text-gray-900 mb-4">Слабые места</h2>
          <div className="space-y-3">
            {dashboard.topWeaknesses.map((w) => (
              <div key={w.id} className="flex items-center justify-between">
                <div>
                  <p className="font-medium text-gray-900">{w.topicName}</p>
                  <p className="text-sm text-gray-500">{w.subjectName}</p>
                </div>
                <div className="flex items-center gap-3">
                  <div className="w-32 bg-gray-200 rounded-full h-2">
                    <div className="bg-indigo-600 h-2 rounded-full"
                      style={{ width: `${Math.min(w.accuracyRate, 100)}%` }} />
                  </div>
                  <span className="text-sm font-medium text-gray-700 w-12 text-right">
                    {w.accuracyRate.toFixed(0)}%
                  </span>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}

      {dashboard.recentAttempts.length > 0 && (
        <div className="bg-white rounded-xl p-6 shadow-sm border border-gray-100">
          <h2 className="text-lg font-semibold text-gray-900 mb-4">Последние попытки</h2>
          <div className="space-y-2">
            {dashboard.recentAttempts.map((a) => (
              <div key={a.attemptId}
                className="flex items-center justify-between py-2 border-b border-gray-50 last:border-0">
                <span className="text-gray-900">{a.testTitle}</span>
                <span className={`font-medium ${a.percentage >= 70 ? 'text-green-600' : a.percentage >= 40 ? 'text-amber-600' : 'text-red-600'}`}>
                  {a.percentage?.toFixed(0)}%
                </span>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  )
}
