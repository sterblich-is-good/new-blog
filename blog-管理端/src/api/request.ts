import router from '../router/index'
import axios from 'axios'
import type { AxiosResponse } from 'axios'

// 创建 axios 实例
const request = axios.create({
    baseURL: '/user/',
    timeout: 5000
})

// 请求拦截器：把 token 放在 headers.token
request.interceptors.request.use(
    (config: any) => {
        // 如果是登录接口，不加 token
        if (config.url?.includes('/login')) {
            return config
        }
        const token = localStorage.getItem('token')

        if (token) {
            // 确保 headers 存在
            config.headers = config.headers ?? {} as Record<string, string>
            // 注意这里的 key 改成 'token'
            config.headers['token'] = token
        }
        return config
    },
    (error) => Promise.reject(error)
)

// 响应拦截器
request.interceptors.response.use(
    (response: AxiosResponse) => {
        const data = response.data

        const url = response.config.url

        // ✅ 如果是登录接口
        if (url?.includes('/login')) {
            // 先检查登录是否成功
            if (data.code === 1) {
                const token = data?.data.token

                if (token) {
                    localStorage.setItem('token', token)
                }
            }
        }

        // 如果 msg 是 NOT_LOGIN，直接跳转登录页
        if (data.msg === 'NOT_LOGIN') {
            // 清空 token
            localStorage.removeItem('token')
            // 跳转登录页
            router.replace('/login')

            // 可以阻止后续代码执行
            return new Promise(() => { }) // 永远pending
        }
        return data
    },
    (error) => Promise.reject(error)
)

export default request
