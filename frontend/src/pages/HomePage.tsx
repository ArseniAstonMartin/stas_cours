import { Link } from 'react-router-dom'
import { useAuthStore } from '../store/authStore'
import { FiTarget, FiTrendingUp, FiBookOpen, FiAward } from 'react-icons/fi'

const features = [
  { icon: FiTarget, title: 'Адаптивные тесты', desc: 'Сложность подстраивается под ваш уровень' },
  { icon: FiTrendingUp, title: 'Отслеживание прогресса', desc: 'Детальная статистика по каждой теме' },
  { icon: FiBookOpen, title: 'Учебные материалы', desc: 'Теория, формулы и примеры' },
  { icon: FiAward, title: 'Выявление слабых мест', desc: 'Фокус на темах, требующих внимания' },
]

export default function HomePage() {
  const { isAuthenticated } = useAuthStore()

  return (
    <div className="space-y-16">
      <section className="text-center py-16">
        <h1 className="text-5xl font-bold text-gray-900 mb-6">
          Подготовка к <span className="text-indigo-600">ЦТ/ЦЭ</span>
        </h1>
        <p className="text-xl text-gray-600 max-w-2xl mx-auto mb-8">
          Адаптивное тестирование с выявлением слабых мест. Система подстраивается под ваш уровень
          знаний и помогает эффективно подготовиться к экзаменам.
        </p>
        {!isAuthenticated && (
          <div className="flex gap-4 justify-center">
            <Link to="/register"
              className="bg-indigo-600 text-white px-8 py-3 rounded-lg text-lg font-medium hover:bg-indigo-700 transition-colors">
              Начать подготовку
            </Link>
            <Link to="/login"
              className="bg-white text-indigo-600 px-8 py-3 rounded-lg text-lg font-medium border-2 border-indigo-600 hover:bg-indigo-50 transition-colors">
              Войти
            </Link>
          </div>
        )}
        {isAuthenticated && (
          <Link to="/subjects"
            className="bg-indigo-600 text-white px-8 py-3 rounded-lg text-lg font-medium hover:bg-indigo-700 transition-colors inline-block">
            Перейти к предметам
          </Link>
        )}
      </section>

      <section className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        {features.map(({ icon: Icon, title, desc }) => (
          <div key={title} className="bg-white rounded-xl p-6 shadow-sm border border-gray-100 hover:shadow-md transition-shadow">
            <Icon className="text-3xl text-indigo-600 mb-4" />
            <h3 className="font-semibold text-gray-900 mb-2">{title}</h3>
            <p className="text-gray-600 text-sm">{desc}</p>
          </div>
        ))}
      </section>
    </div>
  )
}
