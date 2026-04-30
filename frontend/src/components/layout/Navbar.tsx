import { Link, useNavigate } from 'react-router-dom'
import { useAuthStore } from '../../store/authStore'
import { FiLogOut, FiUser, FiBookOpen } from 'react-icons/fi'

export default function Navbar() {
  const { isAuthenticated, user, logout } = useAuthStore()
  const navigate = useNavigate()

  const handleLogout = () => {
    logout()
    navigate('/login')
  }

  return (
    <nav className="bg-white border-b border-gray-200 shadow-sm">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-16 items-center">
          <Link to="/" className="flex items-center gap-2 text-xl font-bold text-indigo-600">
            <FiBookOpen className="text-2xl" />
            ЦТ/ЦЭ Подготовка
          </Link>

          <div className="flex items-center gap-6">
            {isAuthenticated ? (
              <>
                <Link to="/subjects" className="text-gray-600 hover:text-indigo-600 transition-colors">
                  Предметы
                </Link>
                <Link to="/tests" className="text-gray-600 hover:text-indigo-600 transition-colors">
                  Тесты
                </Link>
                <Link to="/dashboard" className="text-gray-600 hover:text-indigo-600 transition-colors">
                  Мой прогресс
                </Link>
                <Link to="/weaknesses" className="text-gray-600 hover:text-indigo-600 transition-colors">
                  Слабые места
                </Link>
                <div className="flex items-center gap-3 ml-4 pl-4 border-l border-gray-200">
                  <FiUser className="text-gray-500" />
                  <span className="text-sm text-gray-700">{user?.firstName}</span>
                  <button onClick={handleLogout}
                    className="text-gray-400 hover:text-red-500 transition-colors">
                    <FiLogOut />
                  </button>
                </div>
              </>
            ) : (
              <>
                <Link to="/login"
                  className="text-gray-600 hover:text-indigo-600 transition-colors">
                  Войти
                </Link>
                <Link to="/register"
                  className="bg-indigo-600 text-white px-4 py-2 rounded-lg hover:bg-indigo-700 transition-colors">
                  Регистрация
                </Link>
              </>
            )}
          </div>
        </div>
      </div>
    </nav>
  )
}
