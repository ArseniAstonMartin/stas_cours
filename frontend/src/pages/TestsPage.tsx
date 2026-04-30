import { useQuery } from '@tanstack/react-query'
import { Link } from 'react-router-dom'
import { testsApi } from '../api/tests'
import { FiClock, FiList } from 'react-icons/fi'

export default function TestsPage() {
  const { data, isLoading } = useQuery({
    queryKey: ['tests'],
    queryFn: () => testsApi.getAll({ page: 0, size: 20 }),
  })

  if (isLoading) return <div className="text-center py-16 text-gray-500">Загрузка тестов...</div>

  return (
    <div>
      <h1 className="text-3xl font-bold text-gray-900 mb-8">Тесты</h1>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {data?.content?.map((test) => (
          <Link key={test.id} to={`/tests/${test.id}`}
            className="bg-white rounded-xl p-6 shadow-sm border border-gray-100 hover:shadow-md transition-all">
            <div className="flex justify-between items-start mb-3">
              <h3 className="font-semibold text-lg text-gray-900">{test.title}</h3>
              {test.isAdaptive && (
                <span className="bg-purple-100 text-purple-700 text-xs px-2 py-1 rounded-full">
                  Адаптивный
                </span>
              )}
            </div>
            <p className="text-gray-600 text-sm mb-4">{test.description}</p>
            <div className="flex items-center gap-4 text-sm text-gray-500">
              <span className="flex items-center gap-1">
                <FiClock /> {test.timeLimitMinutes} мин
              </span>
              <span className="flex items-center gap-1">
                <FiList /> {test.questionCount} вопросов
              </span>
              <span className="text-indigo-600 font-medium">{test.subjectName}</span>
            </div>
          </Link>
        ))}
        {(!data?.content || data.content.length === 0) && (
          <p className="text-gray-500 col-span-2 text-center py-8">Тесты пока не добавлены</p>
        )}
      </div>
    </div>
  )
}
