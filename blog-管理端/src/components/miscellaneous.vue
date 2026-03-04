<template>
    <div class="admin-page">
        <header class="page-header">
            <h1>杂项配置</h1>
            <p>管理与展示信息与技术标签</p>
        </header>
        <main class="main-content">
            <div class="container">
                <!-- 基础信息 -->
                <section class="card">
                    <div class="card-header">
                        <div class="card-icon blue"></div>
                        <h2 class="card-title">首页设置</h2>
                    </div>
                    <div class="card-body">
                        <div class="grid">
                            <div class="form-item">
                                <label>首页 Title</label>
                                <input v-model="form.title" placeholder="请输入标题" />
                            </div>

                            <div class="form-item">
                                <label>首页 Subtitle</label>
                                <input v-model="form.subtitle" placeholder="请输入副标题" />
                            </div>
                        </div>
                    </div>
                </section>

                <!-- 技术选项 -->
                <section class="card highlight">
                    <div class="card-header">
                        <div class="card-icon purple"></div>
                        <h2 class="card-title">关于页技术选项</h2>
                    </div>
                    <div class="card-body">
                        <div class="add-wrapper">
                            <input v-model="newOption" placeholder="添加技术，例如 Vue / React" @keyup.enter="addOption" />
                            <button class="primary" @click="addOption">添加</button>
                        </div>

                        <div class="tags">
                            <div v-for="item in options" :key="item.id" :class="['tag', { active: item.status === 1 }]"
                                @click="toggleSelect(item.id)">
                                {{ item.tech }}
                                <span class="remove" @click.stop="removeOption(item.id)">×</span>
                            </div>
                        </div>
                    </div>
                </section>

                <!-- 保存栏 -->
                <div class="action-buttons">
                    <button class="btn save-btn" @click="handleSave">
                        <span class="btn-icon">💾</span>
                        保存配置
                    </button>
                </div>
            </div>
        </main>
    </div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue'
import { currentGET, currentPOST } from '../api'
import { message } from 'ant-design-vue'

const form = ref({})

const options = ref([
])

select();

async function select() {
    let data1 = await currentGET('url5');
    form.value = { title: data1.data.title, subtitle: data1.data.subtitle }

    let data2 = await currentGET('url6');
    options.value = data2.data.array
}

async function update() {
    const data1 = await currentPOST('url7', options.value);
    const data2 = await currentPOST('url8', form.value);

    if (data1.code === 1 && data2.code === 1) {
        return true;
    } else {
        return false;
    }
}

const selected = ref([])
const newOption = ref('')

const toggleSelect = (id) => {
    const target = options.value.find(o => o.id === id)
    if (!target) return

    target.status = target.status === 1 ? 0 : 1
}

//添加按钮
const addOption = () => {
    const value = newOption.value.trim()
    if (!value) return

    // 防重复
    if (options.value.some(o => o.tech.toLowerCase() === value.toLowerCase())) {
        newOption.value = ''
        return
    }

    options.value.push({
        id: Date.now(),
        tech: value,
        status: 0
    })

    newOption.value = ''
}

const removeOption = (id) => {
    options.value = options.value.filter(o => o.id !== id)
    selected.value = selected.value.filter(s => s !== id)
}

const handleSave = async () => {
    const res = await update();   // 等待接口完成
    await select();   // 再重新拉数据

    if (res) {
        message.success("保存成功")
    } else {
        message.console.error("保存失败");
    }
}
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

/* 网格布局 */
.grid {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 20px;
}

/* 表单项 */
.form-item {
    display: flex;
    flex-direction: column;
    gap: 8px;
}

label {
    font-size: 14px;
    font-weight: 500;
    color: #4b5563;
}

input {
    height: 42px;
    padding: 0 16px;
    border-radius: 8px;
    border: 1px solid #e5e7eb;
    transition: .3s ease;
    font-size: 14px;
}

input:focus {
    border-color: #7c3aed;
    box-shadow: 0 0 0 3px rgba(124, 58, 237, .12);
    outline: none;
}

/* 添加标签 */
.add-wrapper {
    display: flex;
    gap: 12px;
    margin-bottom: 16px;
}

.primary {
    padding: 0 20px;
    border: none;
    border-radius: 8px;
    background: #7c3aed;
    color: white;
    cursor: pointer;
    font-weight: 600;
    transition: .3s ease;
}

.primary:hover {
    background: #6d28d9;
    transform: translateY(-1px);
    box-shadow: 0 4px 12px rgba(124, 58, 237, 0.3);
}

/* 标签列表 */
.tags {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
}

.tag {
    display: flex;
    align-items: center;
    gap: 6px;
    padding: 8px 16px;
    border-radius: 20px;
    background: #f3f4f6;
    cursor: pointer;
    transition: .3s ease;
    font-size: 14px;
    border: 1px solid #e5e7eb;
}

.tag:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

.tag.active {
    background: #7c3aed;
    color: white;
    border-color: #7c3aed;
}

.remove {
    font-weight: bold;
    cursor: pointer;
    opacity: .7;
    transition: .2s ease;
}

.remove:hover {
    opacity: 1;
    color: #ef4444;
}

/* 操作按钮 */
.action-buttons {
    display: flex;
    gap: 16px;
    margin-top: 20px;
    justify-content: flex-end;
}

.btn {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 12px 24px;
    border: none;
    border-radius: 8px;
    font-size: 14px;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.3s ease;
}

.btn-icon {
    font-size: 16px;
}

.save-btn {
    background: linear-gradient(135deg, #22c55e, #16a34a);
    color: white;
}

.save-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 20px rgba(34, 197, 94, .25);
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

    .grid {
        grid-template-columns: 1fr;
    }

    .action-buttons {
        flex-direction: column;
    }

    .btn {
        justify-content: center;
    }
}
</style>
