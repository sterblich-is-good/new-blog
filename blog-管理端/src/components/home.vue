<template>
    <div class="admin-page">
        <header class="page-header">
            <h1>首页</h1>
            <p>博客管理后台</p>
        </header>
        <main class="main-content">
            <div class="container">
                <!-- 欢迎信息 -->
                <section class="card">
                    <div class="card-header">
                        <div class="card-icon blue"></div>
                        <h2 class="card-title">欢迎信息</h2>
                    </div>
                    <div class="card-body">
                        <div class="welcome-content">
                            <p>欢迎使用博客管理后台！</p>
                            <p>在这里你可以：</p>
                            <ul>
                                <li>发布新文章</li>
                                <li>管理归档记录</li>
                                <li>配置杂项设置</li>
                                <li>查看个人中心</li>
                            </ul>
                        </div>
                    </div>
                </section>

                <!-- 快捷操作 -->
                <section class="card highlight">
                    <div class="card-header">
                        <div class="card-icon purple"></div>
                        <h2 class="card-title">快捷操作</h2>
                    </div>
                    <div class="card-body">
                        <div class="quick-actions">
                            <a-button type="primary" @click="$router.push('/publish')">
                                发布文章
                            </a-button>
                            <a-button @click="$router.push('/archive')">
                                查看归档
                            </a-button>
                            <a-button @click="$router.push('/miscellaneous')">
                                配置设置
                            </a-button>
                        </div>
                    </div>
                </section>
            </div>
        </main>
    </div>
</template>

<script setup>
// 首页组件
</script>

<style scoped>
/* 页面容器 */
.admin-page {
    min-height: 100vh;
    background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
    padding: 40px 20px;
}

/* 页面头部 */
.page-header {
    text-align: center;
    margin-bottom: 40px;
    padding: 20px 0;
}

.page-header h1 {
    margin: 0;
    font-size: 32px;
    font-weight: 700;
    color: #1f2937;
}

.page-header p {
    margin-top: 8px;
    font-size: 16px;
    color: #6b7280;
}

/* 主内容区 */
.main-content {
    max-width: 1200px;
    margin: 0 auto;
}

/* 容器 */
.container {
    display: grid;
    grid-template-columns: 1fr;
    gap: 30px;
}

/* 卡片样式 */
.card {
    background: white;
    border-radius: 12px;
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
    transition: all 0.3s ease;
    overflow: hidden;
}

.card:hover {
    box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
    transform: translateY(-2px);
}

/* 卡片头部 */
.card-header {
    display: flex;
    align-items: center;
    gap: 12px;
    padding: 20px 24px;
    border-bottom: 1px solid #e5e7eb;
    background: #f9fafb;
}

.card-icon {
    width: 12px;
    height: 12px;
    border-radius: 50%;
}

.card-title {
    margin: 0;
    font-size: 18px;
    font-weight: 600;
    color: #1f2937;
}

/* 卡片内容 */
.card-body {
    padding: 24px;
}

/* 高亮卡片 */
.card.highlight {
    border: 1px solid #e0e7ff;
    background: linear-gradient(145deg, #fefcfe 0%, #f0f9ff 100%);
}

/* 颜色类 */
.blue {
    background: #3b82f6;
}

.purple {
    background: #7c3aed;
}

/* 欢迎内容 */
.welcome-content {
    line-height: 1.6;
}

.welcome-content ul {
    margin-top: 10px;
    padding-left: 20px;
}

/* 快捷操作 */
.quick-actions {
    display: flex;
    gap: 16px;
    flex-wrap: wrap;
}

/* 响应式设计 */
@media (max-width: 768px) {
    .admin-page {
        padding: 20px 16px;
    }

    .page-header h1 {
        font-size: 24px;
    }

    .card-body {
        padding: 20px;
    }

    .quick-actions {
        flex-direction: column;
    }
}
</style>