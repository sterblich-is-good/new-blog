<script setup lang="js">
import { ref, onMounted } from 'vue';
import { currentGET } from '../api';
import { useRouter } from 'vue-router';

//归档页数据
let data = ref();
onMounted(async () => {
    // 必须使用 await，否则拿到的是 Promise 对象
    const res = await currentGET('url1');
    data.value = res.data.array;
});

const router = useRouter();
function jump(id) {
    router.push({
        path: '/articles',
        query: { id: id }
    })
}
</script>
<template>
    <div class="timeline-container">
        <div class="content-limit-wrapper" v-for="value in data" :key="value.id">
            <h1 class="year-label">{{ value.year }}</h1>
            <div class="post-list">
                <article v-for="i in value.articlesList" :key="i.id" class="post-item">
                    <div class="post-meta">
                        <span class="date">{{ i.time }}</span>
                        <h2 class="title" @click="jump(i.id)">{{ i.title }}</h2>
                    </div>
                    <p class="excerpt">{{ i.desc }}</p>
                </article>
            </div>
        </div>
    </div>
</template>

<style scoped>
.title {
    cursor: pointer;
    transition: color 0.2s ease;
}

.title:hover {
    color: var(--accent-color);
    /* 悬浮时变个色，反馈更明显 */
    text-decoration: underline;
    /* 可选：加下划线 */
}

/* 核心容器：窄屏宽度，居中 */
.archive-container {
    max-width: 700px;
    margin: 0 auto;
    padding: 40px 20px;
    background-color: transparent;
    /* 纯净背景 */
    color: #1d1d1f;
    /* 接近苹果的深灰色，不是纯黑 */
    font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
}

/* 年份样式 */
.archive-year {
    font-size: 2.2rem;
    font-weight: 700;
    margin: 50px 0 20px 0;
}

/* 文章条目：关键在于间距和去掉修饰 */
.archive-entry {
    position: relative;
    padding: 20px 0;
    /* 上下间距增大 */
    margin-bottom: 10px;
    transition: all 0.2s;
}

/* 模拟 PaperMod 的悬浮位移感 */
.archive-entry:hover .archive-entry-title {
    color: #42b883;
    /* 悬浮时标题变色 */
    transform: translateX(4px);
}

.archive-entry-title {
    margin: 0 0 8px 0;
    font-size: 1.4rem;
    font-weight: 600;
    line-height: 1.3;
    transition: transform 0.2s ease;
}

.archive-entry-description {
    margin: 0 0 10px 0;
    font-size: 1.05rem;
    line-height: 1.6;
    color: #666;
    /* 描述文字调浅 */
}

/* 元数据（日期） */
.archive-meta {
    font-size: 0.9rem;
    color: #999;
}

/* 伪链接：让整个 article 区域可点击 */
.entry-link {
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    cursor: pointer;
}

/* 响应式调整 */
@media (max-width: 600px) {
    .archive-year {
        font-size: 1.8rem;
    }

    .archive-entry-title {
        font-size: 1.2rem;
    }
}
</style>