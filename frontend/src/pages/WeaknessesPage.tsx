import { useQuery } from '@tanstack/react-query'
import { dashboardApi } from '../api/dashboard'

const levelColors: Record<string, string> = {
  CRITICAL: 'bg-red-100 text-red-700',
  WEAK: 'bg-orange-100 text-orange-700',
  MODERATE: 'bg-yellow-100 text-yellow-700',
  STRONG: 'bg-green-100 text-green-700',
  UNKNOWN: 'bg-gray-100 text-gray-700',
}

const levelLabels: Record<string, string> = {
  CRITICAL: 'Критический',
  WEAK: 'Слабый',
  MODERATE: 'Средний',
  STRONG: 'Сильный',
  UNKNOWN: 'Не определён',
}

export default function WeaknessesPage() {
  const { data: weaknesses, isLoading } = useQuery({
    queryKey: ['weaknesses'],
    queryFn: dashboardApi.getWeaknesses,
  })

  if (isLoading) return <div className="text-center py-16 text-gray-500">Загрузка...</div>

  return (
    <div>
      <h1 className="text-3xl font-bold text-gray-900 mb-2">Слабые места</h1>
      <p className="text-gray-600 mb-8">Анализ ваших результатов по темам</p>

      {(!weaknesses || weaknesses.length === 0) ? (
        <div className="bg-white rounded-xl p-12 shadow-sm border border-gray-100 text-center">
          <p className="text-gray-500 text-lg">Пройдите несколько тестов, чтобы увидеть анализ</p>
        </div>
      ) : (
        <div className="space-y-4">
          {weaknesses.map((w) => (
            <div key={w.id} className="bg-white rounded-xl p-5 shadow-sm border border-gray-100">
              <div className="flex items-center justify-between mb-3">
                <div>
                  <h3 className="font-semibold text-gray-900">{w.topicName}</h3>
                  <p className="text-sm text-gray-500">{w.subjectName}</p>
                </div>
                <span className={`text-xs px-3 py-1 rounded-full font-medium ${levelColors[w.weaknessLevel]}`}>
                  {levelLabels[w.weaknessLevel]}
                </span>
              </div>
              <div className="flex items-center gap-4">
                <div className="flex-1">
                  <div className="w-full bg-gray-200 rounded-full h-2.5">
                    <div className={`h-2.5 rounded-full ${
                      w.accuracyRate >= 80 ? 'bg-green-500' :
                      w.accuracyRate >= 60 ? 'bg-yellow-500' :
                      w.accuracyRate >= 40 ? 'bg-orange-500' : 'bg-red-500'
                    }`} style={{ width: `${Math.min(w.accuracyRate, 100)}%` }} />
                  </div>
                </div>
                <span className="text-sm font-medium text-gray-700 min-w-[3rem] text-right">
                  {w.accuracyRate.toFixed(0)}%
                </span>
              </div>
              <p className="text-xs text-gray-400 mt-2">
                {w.correctAttempts} из {w.totalAttempts} правильных ответов
              </p>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}
