<template>
    <div class="edit-article-page">
        <!-- 页面头部 -->
        <header class="page-header">
            <div class="container">
                <h1 class="page-title">编辑文章</h1>
                <p class="page-subtitle">修改文章内容和设置</p>
            </div>
        </header>

        <!-- 主要内容 -->
        <main class="main-content">
            <div class="container">
                <!-- 文章信息 -->
                <section class="card">
                    <div class="card-header">
                        <div class="card-icon blue"></div>
                        <h2 class="card-title">文章信息</h2>
                    </div>
                    <div class="card-body">
                        <div class="form-group">
                            <label class="form-label">文章标题</label>
                            <input v-model="article.title" placeholder="请输入文章标题" class="form-input" />
                        </div>
                        <div class="form-group">
                            <label class="form-label">文章摘要</label>
                            <textarea v-model="article.desc" placeholder="请输入文章摘要" rows="3"
                                class="form-textarea"></textarea>
                        </div>
                    </div>
                </section>

                <!-- 发布设置 -->
                <section class="card">
                    <div class="card-header">
                        <div class="card-icon green"></div>
                        <h2 class="card-title">发布设置</h2>
                    </div>
                    <div class="card-body">
                        <div class="form-group">
                            <label class="form-label">发布状态</label>
                            <select v-model="article.status" class="form-select">
                                <option :value="'0'">未发布</option>
                                <option :value="'1'">已发布</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label class="form-label">发布时间</label>
                            <input type="date" v-model="article.time" class="form-input" />
                        </div>
                    </div>
                </section>

                <!-- 文章内容 -->
                <section class="card highlight">
                    <div class="card-header">
                        <div class="card-icon purple"></div>
                        <h2 class="card-title">文章内容</h2>
                    </div>
                    <div class="card-body">
                        <div class="editor-preview-wrapper" ref="editorPreviewWrapper">
                            <!-- 预览区域 -->
                            <div class="preview-section">
                                <div class="section-header">
                                    <h3 class="section-title">实时预览</h3>
                                </div>
                                <div class="preview-container">
                                    <div class="preview-content" v-html="previewContent"></div>
                                </div>
                            </div>

                            <!-- 编辑器区域 -->
                            <div class="editor-section">
                                <div class="section-header">
                                    <h3 class="section-title">Markdown 编辑器</h3>
                                    <button @click="toggleFullscreen" class="toolbar-btn" title="全屏编辑和预览">
                                        🖥️
                                    </button>
                                </div>
                                <div class="editor-container">
                                    <!-- 工具栏 -->
                                    <div class="editor-toolbar">
                                        <button @click="insertMarkdown('**', '**')" class="toolbar-btn" title="粗体">
                                            <strong>B</strong>
                                        </button>
                                        <button @click="insertMarkdown('*', '*')" class="toolbar-btn" title="斜体">
                                            <em>I</em>
                                        </button>
                                        <button @click="insertMarkdown('`', '`')" class="toolbar-btn" title="行内代码">
                                            <code>&lt;/&gt;</code>
                                        </button>
                                        <button @click="insertMarkdown('```\n', '\n```')" class="toolbar-btn"
                                            title="代码块">
                                            代码块
                                        </button>
                                        <button @click="insertMarkdown('# ', '')" class="toolbar-btn" title="标题 1">
                                            H1
                                        </button>
                                        <button @click="insertMarkdown('## ', '')" class="toolbar-btn" title="标题 2">
                                            H2
                                        </button>
                                        <button @click="insertMarkdown('- ', '')" class="toolbar-btn" title="无序列表">
                                            • 列表
                                        </button>
                                        <button @click="insertMarkdown('[', '](url)')" class="toolbar-btn" title="链接">
                                            链接
                                        </button>
                                        <button @click="uploadImage" class="toolbar-btn" title="图片">
                                            图片
                                        </button>
                                        <input type="file" ref="fileInput" style="display: none" accept="image/*"
                                            @change="handleFileUpload" />
                                    </div>
                                    <!-- 编辑区域 -->
                                    <textarea v-model="article.text" placeholder="请输入文章正文，支持 Markdown 格式"
                                        class="markdown-editor"></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <!-- 操作按钮 -->
                <div class="action-buttons">
                    <button class="btn save-btn" @click="handleSave">
                        <span class="btn-icon">💾</span>
                        保存修改
                    </button>
                    <button class="btn cancel-btn" @click="handleCancel">
                        <span class="btn-icon">❌</span>
                        取消
                    </button>
                </div>
            </div>
        </main>
    </div>
</template>

<script setup>
import { reactive, ref, onMounted, computed } from 'vue'
import { currentGET, currentPOST } from '../api'
import { message } from 'ant-design-vue'
import { useRoute, useRouter } from 'vue-router'
import { marked } from 'marked'

const route = useRoute()
const router = useRouter()
const articleId = route.params.id

const article = ref({
    id: articleId,
    title: '',
    desc: '',
    text: '',
    status: '1',
    time: ''
})

// 实时预览内容
const previewContent = computed(() => {
    return marked(article.value.text || '', {
        breaks: true,
        gfm: true
    })
})

// 插入 Markdown 标记
const insertMarkdown = (prefix, suffix) => {
    const textarea = document.querySelector('.markdown-editor')
    if (!textarea) return

    const start = textarea.selectionStart
    const end = textarea.selectionEnd
    const selectedText = article.value.text.substring(start, end)
    const newText = article.value.text.substring(0, start) + prefix + selectedText + suffix + article.value.text.substring(end)

    article.value.text = newText

    // 聚焦到插入后的位置
    setTimeout(() => {
        textarea.focus()
        const newPosition = start + prefix.length + selectedText.length
        textarea.setSelectionRange(newPosition, newPosition)
    }, 0)
}

// 上传图片
const fileInput = ref(null)

const uploadImage = () => {
    fileInput.value.click()
}

// 全屏功能
const editorPreviewWrapper = ref(null)

const toggleFullscreen = () => {
    const element = editorPreviewWrapper.value

    if (!element) return

    if (!document.fullscreenElement) {
        // 进入全屏
        if (element.requestFullscreen) {
            element.requestFullscreen()
        } else if (element.mozRequestFullScreen) {
            element.mozRequestFullScreen()
        } else if (element.webkitRequestFullscreen) {
            element.webkitRequestFullscreen()
        } else if (element.msRequestFullscreen) {
            element.msRequestFullscreen()
        }
    } else {
        // 退出全屏
        if (document.exitFullscreen) {
            document.exitFullscreen()
        } else if (document.mozCancelFullScreen) {
            document.mozCancelFullScreen()
        } else if (document.webkitExitFullscreen) {
            document.webkitExitFullscreen()
        } else if (document.msExitFullscreen) {
            document.msExitFullscreen()
        }
    }
}

const handleFileUpload = async (event) => {
    const file = event.target.files[0]
    if (!file) return

    const formData = new FormData()
    formData.append('image', file)

    try {
        const res = await currentPOST('url14', formData)
        if (res.code === 1 && res.data) {
            const imageUrl = res.data
            insertMarkdown('![', `](${imageUrl})`)
        } else {
            message.error('上传失败')
        }
    } catch (error) {
        message.error('上传失败，请稍后重试')
    }

    // 重置文件输入
    event.target.value = ''
}

// 加载文章详情
const loadArticle = async () => {
    try {
        const res = await currentGET('url11', { id: articleId })
        if (res.code === 1) {
            article.value = {
                ...article.value,
                title: res.data.title,
                desc: res.data.desc || '',
                text: res.data.text || '',
                status: res.data.status?.toString() || '0',
                time: res.data.time || ''
            }
        } else {
            message.error('加载文章失败')
        }
    } catch (error) {
        message.error('加载文章失败')
    }
}

// 保存修改
const handleSave = async () => {
    // 表单验证
    if (!article.value.title) {
        message.error('请输入文章标题')
        return
    }

    if (!article.value.text) {
        message.error('请输入文章内容')
        return
    }

    try {
        const res = await currentPOST('url12', article.value)
        if (res.code === 1) {
            message.success('保存成功')
            router.push('/archive')
        } else {
            message.error('保存失败')
        }
    } catch (error) {
        message.error('保存失败，请稍后重试')
    }
}

// 取消编辑
const handleCancel = () => {
    if (confirm('确定要取消编辑吗？未保存的修改将会丢失。')) {
        router.push('/archive')
    }
}

onMounted(() => {
    loadArticle()
})
</script>

<style>
/* 全局样式 - 确保代码块样式能够应用到 v-html 生成的内容 */
.preview-content pre {
    background-color: #1e293b !important;
    color: #f1f5f9 !important;
    padding: 16px !important;
    border-radius: 8px !important;
    overflow-x: auto !important;
    margin: 16px 0 !important;
    font-family: 'Consolas', 'Monaco', 'Courier New', monospace !important;
    font-size: 14px !important;
    line-height: 1.4 !important;
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06) !important;
    border: 1px solid #334155 !important;
}

.preview-content pre code {
    background-color: transparent !important;
    padding: 0 !important;
    color: #f1f5f9 !important;
    font-size: 14px !important;
    line-height: 1.4 !important;
}

.preview-content code {
    background-color: #f3f4f6 !important;
    padding: 2px 6px !important;
    border-radius: 4px !important;
    font-family: 'Consolas', 'Monaco', 'Courier New', monospace !important;
    font-size: 14px !important;
}

.preview-content img {
    max-width: 100% !important;
    max-height: 400px !important;
    object-fit: contain !important;
    border-radius: 8px !important;
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06) !important;
    margin: 16px 0 !important;
}

/* 全屏模式样式 */
.editor-preview-wrapper:fullscreen {
    position: fixed !important;
    top: 0 !important;
    left: 0 !important;
    width: 100vw !important;
    height: 100vh !important;
    z-index: 9999 !important;
    padding: 20px !important;
    background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%) !important;
    box-sizing: border-box !important;
}

.editor-preview-wrapper:fullscreen .preview-section,
.editor-preview-wrapper:fullscreen .editor-section {
    height: 100% !important;
}

.editor-preview-wrapper:fullscreen .preview-container,
.editor-preview-wrapper:fullscreen .editor-container {
    height: calc(100% - 40px) !important;
    border-radius: 8px !important;
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06) !important;
}

.editor-preview-wrapper:fullscreen .preview-content,
.editor-preview-wrapper:fullscreen .markdown-editor {
    min-height: 100% !important;
    max-height: 100% !important;
    height: 100% !important;
}

/* 全屏模式下的工具栏 */
.editor-preview-wrapper:fullscreen .editor-toolbar {
    position: sticky !important;
    top: 0 !important;
    z-index: 10 !important;
    background: rgba(248, 250, 252, 0.95) !important;
    backdrop-filter: blur(10px) !important;
    border-bottom: 1px solid #e2e8f0 !important;
}
</style>

<style scoped>
/* 全局样式重置 */
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

/* 页面布局 */
.edit-article-page {
    min-height: 100vh;
    background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
}

/* 容器 */
.container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 20px;
}

/* 页面头部 */
.page-header {
    background: white;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
    padding: 30px 0;
    margin-bottom: 30px;
}

.page-title {
    font-size: 28px;
    font-weight: 700;
    color: #1e293b;
    margin-bottom: 8px;
}

.page-subtitle {
    font-size: 16px;
    color: #64748b;
}

/* 主要内容 */
.main-content {
    padding-bottom: 60px;
}

/* 卡片样式 */
.card {
    background: white;
    border-radius: 12px;
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
    margin-bottom: 30px;
    overflow: hidden;
    transition: all 0.3s ease;
}

.card:hover {
    box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
    transform: translateY(-2px);
}

.card.highlight {
    background: linear-gradient(145deg, #f8faff, #f0f4ff);
    border: 1px solid #e0e7ff;
}

/* 卡片头部 */
.card-header {
    display: flex;
    align-items: center;
    gap: 12px;
    padding: 20px 24px;
    border-bottom: 1px solid #f1f5f9;
}

.card-icon {
    width: 12px;
    height: 12px;
    border-radius: 50%;
}

.card-icon.blue {
    background: #3b82f6;
}

.card-icon.green {
    background: #10b981;
}

.card-icon.purple {
    background: #8b5cf6;
}

.card-title {
    font-size: 18px;
    font-weight: 600;
    color: #1e293b;
}

/* 卡片内容 */
.card-body {
    padding: 24px;
}

/* 表单样式 */
.form-group {
    margin-bottom: 20px;
}

.form-group:last-child {
    margin-bottom: 0;
}

.form-label {
    display: block;
    font-size: 14px;
    font-weight: 500;
    color: #475569;
    margin-bottom: 8px;
}

.form-input,
.form-textarea,
.form-select {
    width: 100%;
    padding: 12px 16px;
    border: 1px solid #e2e8f0;
    border-radius: 8px;
    font-size: 16px;
    font-family: inherit;
    transition: all 0.2s ease;
    background: white;
}

.form-input:focus,
.form-textarea:focus,
.form-select:focus {
    outline: none;
    border-color: #8b5cf6;
    box-shadow: 0 0 0 3px rgba(139, 92, 246, 0.1);
}

.form-textarea {
    resize: vertical;
    min-height: 100px;
}

/* 编辑器和预览区域 */
.editor-preview-wrapper {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 20px;
    margin-top: 24px;
    width: 100%;
    overflow: hidden;
}

/* 确保编辑器和预览区域尺寸完全一致 */
.editor-section,
.preview-section {
    display: flex;
    flex-direction: column;
    height: 100%;
    width: 100%;
    overflow: hidden;
}

.editor-container,
.preview-container {
    flex: 1;
    display: flex;
    flex-direction: column;
    width: 100%;
    overflow: hidden;
}

/* 区域头部 */
.section-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 12px;
    padding-bottom: 8px;
    border-bottom: 1px solid #e2e8f0;
}

.section-title {
    font-size: 16px;
    font-weight: 600;
    color: #475569;
}

/* 编辑器容器 */
.editor-container {
    display: flex;
    flex-direction: column;
    border: 1px solid #e2e8f0;
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
    transition: all 0.3s ease;
}

.editor-container:hover {
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

/* 编辑器工具栏 */
.editor-toolbar {
    display: flex;
    gap: 6px;
    padding: 10px 16px;
    background: #f8fafc;
    border-bottom: 1px solid #e2e8f0;
    flex-wrap: wrap;
}

.toolbar-btn {
    padding: 6px 10px;
    border: 1px solid #e2e8f0;
    border-radius: 6px;
    background: white;
    cursor: pointer;
    font-size: 12px;
    font-weight: 500;
    color: #475569;
    transition: all 0.2s ease;
    display: flex;
    align-items: center;
    gap: 3px;
}

.toolbar-btn:hover {
    background: #f1f5f9;
    border-color: #cbd5e1;
    transform: translateY(-1px);
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.toolbar-btn:active {
    transform: translateY(0);
}

/* Markdown 编辑器 */
.markdown-editor {
    flex: 1;
    min-height: 500px;
    max-height: 500px;
    padding: 20px;
    border: none;
    font-family: 'Consolas', 'Monaco', 'Courier New', monospace;
    font-size: 14px;
    line-height: 1.6;
    resize: none;
    background: white;
    transition: all 0.2s ease;
    overflow-y: auto;
}

.markdown-editor:focus {
    outline: none;
}

/* 预览区域 */
.preview-container {
    border: 1px solid #e2e8f0;
    border-radius: 8px;
    overflow: hidden;
    background: white;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
    transition: all 0.3s ease;
}

.preview-container:hover {
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.preview-content {
    min-height: 500px;
    max-height: 500px;
    padding: 20px;
    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, sans-serif;
    font-size: 15px;
    line-height: 1.6;
    overflow-y: auto;
    overflow-x: auto;
    width: 100%;
    box-sizing: border-box;
}

/* 预览内容样式 */
.preview-content h1 {
    font-size: 24px;
    font-weight: 600;
    margin-top: 0;
    margin-bottom: 20px;
    padding-bottom: 8px;
    border-bottom: 2px solid #e2e8f0;
    color: #1e293b;
}

.preview-content h2 {
    font-size: 20px;
    font-weight: 600;
    margin-top: 24px;
    margin-bottom: 16px;
    padding-bottom: 6px;
    border-bottom: 1px solid #e2e8f0;
    color: #1e293b;
}

.preview-content p {
    margin-bottom: 16px;
    color: #475569;
}

.preview-content ul,
.preview-content ol {
    margin-bottom: 16px;
    padding-left: 24px;
    color: #475569;
}

.preview-content li {
    margin-bottom: 8px;
}

.preview-content a {
    color: #3b82f6;
    text-decoration: none;
}

.preview-content a:hover {
    text-decoration: underline;
}

.preview-content img {
    max-width: 100%;
    height: auto;
    border-radius: 8px;
    margin: 16px 0;
}

/* 操作按钮 */
.action-buttons {
    display: flex;
    gap: 16px;
    margin-top: 30px;
}

.btn {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 12px 24px;
    border: none;
    border-radius: 8px;
    font-size: 16px;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.2s ease;
}

.save-btn {
    flex: 1;
    background: linear-gradient(135deg, #10b981, #059669);
    color: white;
}

.save-btn:hover {
    background: linear-gradient(135deg, #059669, #047857);
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(16, 185, 129, 0.3);
}

.cancel-btn {
    background: white;
    color: #64748b;
    border: 1px solid #e2e8f0;
    padding: 12px 32px;
}

.cancel-btn:hover {
    background: #f8fafc;
    border-color: #cbd5e1;
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

.btn-icon {
    font-size: 18px;
}

/* 响应式布局 */
@media (max-width: 768px) {
    .action-buttons {
        flex-direction: column;
    }

    .cancel-btn {
        width: 100%;
    }

    .page-header {
        padding: 20px 0;
    }

    .page-title {
        font-size: 24px;
    }

    .card-body {
        padding: 20px;
    }
}
</style>