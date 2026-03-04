<template>
    <div class="markdown-preview">
        <h1>{{ title }}</h1>
        <div v-html="markdownText"></div>
    </div>
</template>

<script setup lang="js">
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { currentGET, currentPOST } from '../api';
import hljs from 'highlight.js'; //需要 npm install highlight.js
import 'highlight.js/styles/brown-paper.css';
import 'highlight.js/styles/public.css';
import MarkdownIt from 'markdown-it'; //需要 npm install markdown-it

const markdownText = ref('');
const title = ref('');
const route = useRoute();

onMounted(async () => {
    const md = new MarkdownIt({
        html: true, // 允许在 Markdown 中使用 HTML
        linkify: true, // 自动转换链接为可点击的链接形式
        typographer: true, // 启用智能引号等文本转换功能
        highlight: function (str, lang) {
            if (lang && hljs.getLanguage(lang)) {
                try {
                    return `<pre class="hljs"><code>${hljs.highlight(lang, str, true).value}</code></pre>`;
                } catch (error) {
                    console.log(error)
                }
            }
            return `<pre class="hljs"><code>${md.utils.escapeHtml(str)}</code></pre>`;
        },
    });

    const id = route.query.id;

    if (id) {
        const res = await currentGET('url2', { id: id });
        markdownText.value = md.render(res.data.text)
        title.value = res.data.title
    }
})
</script>
<style scoped>
/* 1. 整体背景和外层容器 */
.markdown-container {
    background-color: #f9f9f9;
    /* 稍微深一点的背景，突出内容区 */
    min-height: 100vh;
    padding: 40px 20px;
}

/* 2. 核心内容区域：限制宽度并居中 */
.markdown-preview {
    max-width: 800px;
    /* 黄金阅读宽度 */
    margin: 0 auto;
    /* 居中 */
    background-color: #ffffff;
    /* 内容区纯白 */
    padding: 40px;
    /* 内部留白 */
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
    /* 淡淡的阴影提升质感 */
    border-radius: 8px;
    /* 圆角 */
    font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Helvetica, Arial, sans-serif;
    line-height: 1.6;
    /* 增加行高，阅读更舒适 */
    color: #333;
}

/* 3. 标题美化 */
h1 {
    font-size: 2.5em;
    margin-bottom: 20px;
    text-align: center;
    /* 标题居中 */
}

hr {
    border: 0;
    border-top: 1px solid #eee;
    margin: 20px 0 40px;
}

/* 4. 针对图片和代码块的适配 */
:deep(img) {
    max-width: 100%;
    /* 防止图片溢出容器 */
    display: block;
    margin: 0 auto;
}

:deep(pre) {
    padding: 16px;
    overflow: auto;
    border-radius: 6px;
}
</style>