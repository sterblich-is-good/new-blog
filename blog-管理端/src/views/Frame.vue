<template>
    <a-layout style="min-height: 100vh">
        <a-layout-header class="header">
            <a-button type="primary" danger @click="exit">退出登录</a-button>
        </a-layout-header>
        <a-layout>
            <a-layout-sider width="200" style="background: #fff">
                <a-menu v-model:selectedKeys="selectedKeys2" v-model:openKeys="openKeys" mode="inline"
                    :style="{ height: '100%', borderRight: 0 }" @click="handleMenuClick">
                    <a-menu-item key="1">首页</a-menu-item>
                    <a-menu-item key="2">发布文章</a-menu-item>
                    <a-menu-item key="3">归档记录</a-menu-item>
                    <a-menu-item key="4">杂项配置</a-menu-item>
                    <a-menu-item key="5">修改密码</a-menu-item>
                </a-menu>
            </a-layout-sider>
            <a-layout style="padding: 0 24px 24px">
                <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">
                    <router-view v-slot="{ Component }">
                        <transition name="fade" mode="out-in">
                            <component :is="Component" />
                        </transition>
                    </router-view>
                </a-layout-content>
            </a-layout>
        </a-layout>
    </a-layout>
</template>
<script lang="ts" setup>
import { ref, watch } from 'vue';
import { currentGET } from '../api'
import router from '../router/index'
import { refresh } from '../util/common'

const selectedKeys2 = ref<string[]>(['1']);
const openKeys = ref<string[]>(['sub1']);

//检查登录状态
refresh();

const exit = async () => {
    await currentGET('url4');
    // 跳转（replace 防止后退）
    router.replace('/login')
    // 清理登录态
    localStorage.removeItem('token')
}

const handleMenuClick = (e: any) => {
    const key = e.key;
    switch (key) {
        case '1':
            router.push('/home');
            break;
        case '2':
            router.push('/publish');
            break;
        case '3':
            router.push('/archive');
            break;
        case '4':
            router.push('/miscellaneous');
            break;
        case '5':
            router.push('/profile');
            break;
    }
}

// 监听路由变化，更新选中的菜单项
watch(
    () => router.currentRoute.value.path,
    (newPath) => {
        const pathMap: Record<string, string> = {
            '/home': '1',
            '/publish': '2',
            '/archive': '3',
            '/miscellaneous': '4',
            '/profile': '5'
        };
        // 处理编辑页面的路由
        if (newPath.startsWith('/edit/')) {
            selectedKeys2.value = ['3']; // 选中归档记录菜单项
        } else {
            selectedKeys2.value = [pathMap[newPath] || '1'];
        }
    },
    { immediate: true }
);
</script>
<style scoped>
#components-layout-demo-top-side-2 .logo {
    float: left;
    width: 120px;
    height: 31px;
    margin: 16px 24px 16px 0;
    background: rgba(255, 255, 255, 0.3);
}

.ant-row-rtl #components-layout-demo-top-side-2 .logo {
    float: right;
    margin: 16px 0 16px 24px;
}

.site-layout-background {
    background: #fff;
}

.header {
    padding: 0 24px;
    /* 如果 header 两头有白边，检查这里 */
}

.logo {
    float: left;
    width: 120px;
    height: 31px;
    margin: 16px 24px 16px 0;
    background: rgba(255, 255, 255, 0.3);
}

/* 路由切换动画 */
.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
    opacity: 0;
}
</style>