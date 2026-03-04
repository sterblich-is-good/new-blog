<template>
    <div class="admin-page">
        <header class="page-header">
            <h1>归档记录</h1>
            <p>管理已发布的文章</p>
        </header>
        <main class="main-content">
            <div class="container">
                <!-- 搜索 -->
                <section class="card">
                    <div class="card-header">
                        <div class="card-icon blue"></div>
                        <h2 class="card-title">文章管理</h2>
                    </div>
                    <div class="card-body">
                        <div class="search-filter">
                            <input v-model="searchKeyword" placeholder="搜索文章标题" class="search-input" />
                        </div>
                    </div>
                </section>

                <!-- 文章列表 -->
                <section class="card highlight">
                    <div class="card-header">
                        <div class="card-icon purple"></div>
                        <h2 class="card-title">文章列表</h2>
                    </div>
                    <div class="card-body">
                        <div class="article-list">
                            <div v-if="yearGroups.length === 0" class="empty-state">
                                <p>暂无文章</p>
                            </div>
                            <div v-else v-for="yearGroup in yearGroups" :key="yearGroup.year" class="year-group">
                                <h2 class="year-title">{{ yearGroup.year }}年</h2>
                                <div class="year-articles">
                                    <div v-for="article in yearGroup.articlesList" :key="article.id"
                                        class="article-item">
                                        <div class="article-info">
                                            <h3 class="article-title">{{ article.title }}</h3>
                                            <div class="article-meta">
                                                <span class="article-date">{{ article.time }}</span>
                                                <span
                                                    :class="['article-status', article.status === 1 ? 'published' : 'unpublished']">
                                                    {{ article.status === 1 ? '已发布' : '未发布' }}
                                                </span>
                                            </div>
                                            <p class="article-summary">{{ article.desc }}</p>
                                        </div>
                                        <div class="article-actions">
                                            <button class="action-button edit" @click="editArticle(article)">
                                                编辑
                                            </button>
                                            <button class="action-button delete" @click="deleteArticle(article.id)">
                                                删除
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </main>
    </div>
</template>

<script setup>
import { reactive, ref, computed, onMounted } from 'vue'
import { currentGET, currentPOST } from '../api'
import { message } from 'ant-design-vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const archiveData = ref({
    array: [
    ]
})

const searchKeyword = ref('')

// 按年份分组的文章列表
const yearGroups = computed(() => {
    let result = archiveData.value.array || []

    // 按关键词搜索
    if (searchKeyword.value) {
        result = result.map(yearGroup => ({
            ...yearGroup,
            articlesList: yearGroup.articlesList.filter(article =>
                article.title.toLowerCase().includes(searchKeyword.value.toLowerCase())
            )
        })).filter(yearGroup => yearGroup.articlesList.length > 0)
    }

    return result
})

// 编辑文章
const editArticle = (article) => {
    // 跳转到编辑页面
    router.push(`/edit/${article.id}`)
}

// 删除文章
const deleteArticle = async (id) => {
    if (confirm('确定要删除这篇文章吗？')) {
        try {
            const res = await currentPOST('url13', { id })
            if (res.code === 1) {
                // 遍历所有年份，删除对应的文章
                archiveData.value.array = archiveData.value.array.map(yearGroup => ({
                    ...yearGroup,
                    articlesList: yearGroup.articlesList.filter(article => article.id !== id)
                })).filter(yearGroup => yearGroup.articlesList.length > 0)
                message.success('删除成功')
            } else {
                message.error('删除失败')
            }
        } catch (error) {
            message.error('删除失败，请稍后重试')
        }
    }
}

// 加载文章列表
const loadArticles = async () => {
    try {
        const res = await currentGET('url10')
        if (res.code === 1) {
            archiveData.value = res.data
        } else {
            message.error('加载文章列表失败')
        }
    } catch (error) {
        message.error('加载文章列表失败')
    }
}

onMounted(() => {
    loadArticles()
})
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

/* 搜索过滤 */
.search-filter {
    display: flex;
    gap: 12px;
    flex-wrap: wrap;
}

.search-input {
    flex: 1;
    min-width: 200px;
    height: 42px;
    padding: 0 12px;
    border-radius: 8px;
    border: 1px solid #e5e7eb;
    transition: .2s;
}

.search-input:focus {
    border-color: #7c3aed;
    box-shadow: 0 0 0 3px rgba(124, 58, 237, .12);
}

/* 文章列表 */
.article-list {
    margin-top: 20px;
}

/* 空状态 */
.empty-state {
    text-align: center;
    padding: 40px 0;
    color: #8b8b8b;
}

/* 年份分组 */
.year-group {
    margin-bottom: 32px;
}

.year-title {
    font-size: 20px;
    font-weight: 600;
    color: #1f2937;
    margin: 0 0 16px 0;
    padding-bottom: 8px;
    border-bottom: 2px solid #e5e7eb;
}

.year-articles {
    display: flex;
    flex-direction: column;
    gap: 16px;
}

/* 文章项 */
.article-item {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    padding: 20px;
    background: white;
    border-radius: 12px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
    transition: .3s ease;
    border: 1px solid #e5e7eb;
}

.article-item:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
    border-color: #d1d5db;
}

.article-info {
    flex: 1;
    margin-right: 20px;
}

.article-title {
    margin: 0 0 8px 0;
    font-size: 18px;
    font-weight: 600;
    color: #1f2937;
    line-height: 1.4;
}

.article-meta {
    display: flex;
    gap: 12px;
    margin-bottom: 12px;
    font-size: 14px;
    color: #6b7280;
}

.article-date {
    background: #f3f4f6;
    padding: 4px 12px;
    border-radius: 16px;
    font-size: 13px;
}

.article-status {
    padding: 4px 12px;
    border-radius: 16px;
    font-size: 13px;
    font-weight: 500;
}

.article-status.published {
    background: #d1fae5;
    color: #065f46;
}

.article-status.unpublished {
    background: #fee2e2;
    color: #991b1b;
}

.article-summary {
    margin: 0;
    font-size: 15px;
    color: #4b5563;
    line-height: 1.6;
}

/* 文章操作 */
.article-actions {
    display: flex;
    flex-direction: column;
    gap: 8px;
}

.action-button {
    padding: 8px 16px;
    border: none;
    border-radius: 8px;
    font-size: 14px;
    font-weight: 500;
    cursor: pointer;
    transition: .3s ease;
    display: flex;
    align-items: center;
    gap: 6px;
}

.action-button.edit {
    background: #3b82f6;
    color: white;
}

.action-button.edit:hover {
    background: #2563eb;
    transform: translateY(-1px);
    box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
}

.action-button.delete {
    background: #ef4444;
    color: white;
}

.action-button.delete:hover {
    background: #dc2626;
    transform: translateY(-1px);
    box-shadow: 0 4px 12px rgba(239, 68, 68, 0.3);
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

    .article-item {
        flex-direction: column;
        gap: 16px;
    }

    .article-info {
        margin-right: 0;
    }

    .article-actions {
        flex-direction: row;
        justify-content: flex-end;
    }
}
</style>