import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
        path: '/login',
        component: () => import('../views/Login.vue'),
        meta: { public: true } // ✅ 标记为公共路由
    },
    {
        path: '/',
        component: () => import('../views/Frame.vue'),
        meta: { requiresAuth: true }, // ✅ 需要登录
        children: [
            {
                path: 'home',
                component: () => import('../components/home.vue'),
                meta: { requiresAuth: true }
            },
            {
                path: 'publish',
                component: () => import('../components/publish.vue'),
                meta: { requiresAuth: true }
            },
            {
                path: 'archive',
                component: () => import('../components/archive.vue'),
                meta: { requiresAuth: true }
            },
            {
                path: 'edit/:id',
                component: () => import('../components/edit-article.vue'),
                meta: { requiresAuth: true }
            },
            {
                path: 'miscellaneous',
                component: () => import('../components/miscellaneous.vue'),
                meta: { requiresAuth: true }
            },
            {
                path: 'profile',
                component: () => import('../components/profile.vue'),
                meta: { requiresAuth: true }
            },
            {
                path: '',
                redirect: 'home',
                meta: { requiresAuth: true }
            }
        ]
    }
]

const router = createRouter({
    history: createWebHistory('/admin/'),
    routes
})


// ⭐⭐⭐ 核心：全局路由守卫
router.beforeEach((to, _from, next) => {
    const token = localStorage.getItem('token')

    // ✅ 未登录 + 想进后台
    if (to.meta.requiresAuth && !token) {
        return next('/login')
    }

    // ✅ 已登录 + 访问 login
    if (to.path === '/login' && token) {
        return next('/')
    }

    next()
})

export default router
