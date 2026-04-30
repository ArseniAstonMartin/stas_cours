import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useForm } from 'react-hook-form'
import axios from 'axios'
import { authApi } from '../api/auth'
import { useAuthStore } from '../store/authStore'

interface LoginForm {
  email: string
  password: string
}

export default function LoginPage() {
  const navigate = useNavigate()
  const { setAuth } = useAuthStore()
  const [error, setError] = useState('')
  const { register, handleSubmit, formState: { errors, isSubmitting } } = useForm<LoginForm>()

  const onSubmit = async (data: LoginForm) => {
    try {
      setError('')
      const res = await authApi.login(data)
      setAuth(res.user, res.accessToken, res.refreshToken)
      navigate('/dashboard')
    } catch (err) {
      if (axios.isAxiosError(err) && err.response?.data) {
        const data = err.response.data
        if (data.errors && typeof data.errors === 'object') {
          const fieldErrors = Object.values(data.errors).join('. ')
          setError(fieldErrors)
        } else if (data.message) {
          setError(data.message)
        } else {
          setError('Ошибка входа. Попробуйте позже.')
        }
      } else {
        setError('Ошибка сети. Проверьте подключение и попробуйте снова.')
      }
    }
  }

  return (
    <div className="max-w-md mx-auto mt-16">
      <div className="bg-white rounded-xl shadow-sm border border-gray-100 p-8">
        <h2 className="text-2xl font-bold text-gray-900 mb-6 text-center">Вход</h2>
        {error && (
          <div className="bg-red-50 text-red-600 p-3 rounded-lg mb-4 text-sm">{error}</div>
        )}
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Email</label>
            <input type="email" {...register('email', { required: 'Email обязателен' })}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent" />
            {errors.email && <p className="text-red-500 text-xs mt-1">{errors.email.message}</p>}
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Пароль</label>
            <input type="password" {...register('password', { required: 'Пароль обязателен' })}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent" />
            {errors.password && <p className="text-red-500 text-xs mt-1">{errors.password.message}</p>}
          </div>
          <button type="submit" disabled={isSubmitting}
            className="w-full bg-indigo-600 text-white py-2 rounded-lg hover:bg-indigo-700 transition-colors disabled:opacity-50">
            {isSubmitting ? 'Вход...' : 'Войти'}
          </button>
        </form>
        <p className="text-center text-sm text-gray-600 mt-4">
          Нет аккаунта? <Link to="/register" className="text-indigo-600 hover:underline">Зарегистрироваться</Link>
        </p>
      </div>
    </div>
  )
}
