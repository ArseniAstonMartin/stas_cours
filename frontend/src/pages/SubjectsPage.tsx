import { useQuery } from '@tanstack/react-query'
import { Link } from 'react-router-dom'
import { subjectsApi } from '../api/subjects'
import { FiBook } from 'react-icons/fi'

export default function SubjectsPage() {
  const { data: subjects, isLoading } = useQuery({
    queryKey: ['subjects'],
    queryFn: subjectsApi.getAll,
  })

  if (isLoading) {
    return <div className="text-center py-16 text-gray-500">Загрузка предметов...</div>
  }

  return (
    <div>
      <h1 className="text-3xl font-bold text-gray-900 mb-8">Предметы</h1>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {subjects?.map((subject) => (
          <Link key={subject.id} to={`/subjects/${subject.id}`}
            className="bg-white rounded-xl p-6 shadow-sm border border-gray-100 hover:shadow-md transition-all hover:border-indigo-200">
            <div className="flex items-start gap-4">
              <div className="bg-indigo-50 p-3 rounded-lg">
                <FiBook className="text-2xl text-indigo-600" />
              </div>
              <div className="flex-1">
                <h3 className="font-semibold text-lg text-gray-900">{subject.name}</h3>
                <p className="text-gray-600 text-sm mt-1">{subject.description}</p>
                <p className="text-indigo-600 text-sm mt-2">
                  {subject.topics?.length || 0} тем
                </p>
              </div>
            </div>
          </Link>
        ))}
      </div>
    </div>
  )
}
