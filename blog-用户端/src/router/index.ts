import { createRouter, createWebHistory } from 'vue-router';

const routes = [
    {
        path: '/',
        name: 'Home',
        component: () => import('../views/Home.vue')
    },
    {
        path: '/archive',
        name: 'Archive',
        component: () => import('../views/Archive.vue')
    },
    {
        path: '/about',
        name: 'About',
        component: () => import('../views/About.vue')
    },
    {
        path: '/articles',
        name: 'Articles',
        component: () => import('../views/Articles.vue')
    }
];

const router = createRouter({
    history: createWebHistory(), // 使用 HTML5 模式
    routes
});

export default router;