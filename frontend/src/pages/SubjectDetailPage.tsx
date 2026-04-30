import { useQuery } from '@tanstack/react-query'
import { useParams, Link } from 'react-router-dom'
import { subjectsApi } from '../api/subjects'
import { FiChevronRight } from 'react-icons/fi'

export default function SubjectDetailPage() {
  const { id } = useParams<{ id: string }>()
  const { data: subject, isLoading } = useQuery({
    queryKey: ['subject', id],
    queryFn: () => subjectsApi.getById(Number(id)),
    enabled: !!id,
  })

  if (isLoading) return <div className="text-center py-16 text-gray-500">Загрузка...</div>
  if (!subject) return <div className="text-center py-16 text-gray-500">Предмет не найден</div>

  return (
    <div>
      <div className="mb-8">
        <Link to="/subjects" className="text-indigo-600 hover:underline text-sm">
          &larr; Все предметы
        </Link>
        <h1 className="text-3xl font-bold text-gray-900 mt-2">{subject.name}</h1>
        <p className="text-gray-600 mt-1">{subject.description}</p>
      </div>

      <h2 className="text-xl font-semibold text-gray-900 mb-4">Темы</h2>
      <div className="space-y-3">
        {subject.topics?.map((topic) => (
          <div key={topic.id}
            className="bg-white rounded-lg p-4 shadow-sm border border-gray-100 flex items-center justify-between hover:shadow-md transition-shadow">
            <div>
              <h3 className="font-medium text-gray-900">{topic.name}</h3>
              {topic.description && (
                <p className="text-gray-500 text-sm mt-1">{topic.description}</p>
              )}
            </div>
            <FiChevronRight className="text-gray-400" />
          </div>
        ))}
      </div>
    </div>
  )
}
