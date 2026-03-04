<template>
    <div class="admin-page">
        <header class="page-header">
            <h1>修改密码</h1>
            <p>修改您的账户密码</p>
        </header>
        <main class="main-content">
            <div class="container">
                <!-- 修改密码 -->
                <section class="card highlight">
                    <div class="card-header">
                        <div class="card-icon purple"></div>
                        <h2 class="card-title">修改密码</h2>
                    </div>
                    <div class="card-body">
                        <div class="form-item">
                            <label>当前密码</label>
                            <input v-model="passwordInfo.oldPassword" placeholder="请输入当前密码" type="password" />
                        </div>

                        <div class="form-item" style="margin-top: 20px;">
                            <label>新密码</label>
                            <input v-model="passwordInfo.newPassword" placeholder="请输入新密码" type="password" />
                        </div>

                        <div class="form-item" style="margin-top: 20px;">
                            <label>确认新密码</label>
                            <input v-model="passwordInfo.confirmPassword" placeholder="请确认新密码" type="password" />
                        </div>
                    </div>
                </section>

                <!-- 保存栏 -->
                <div class="action-buttons">
                    <button class="btn save-btn" @click="handleSave">
                        <span class="btn-icon">🔒</span>
                        修改密码
                    </button>
                </div>
            </div>
        </main>
    </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { currentPOST } from '../api'
import { message } from 'ant-design-vue'

const passwordInfo = ref({
    oldPassword: '',
    newPassword: '',
    confirmPassword: ''
})

const handleSave = async () => {
    // 表单验证
    if (!passwordInfo.value.oldPassword) {
        message.error('请输入当前密码')
        return
    }

    if (!passwordInfo.value.newPassword) {
        message.error('请输入新密码')
        return
    }

    if (passwordInfo.value.newPassword !== passwordInfo.value.confirmPassword) {
        message.error('两次输入的密码不一致')
        return
    }

    try {
        // 这里应该调用修改密码的接口
        const res = await currentPOST('url2', passwordInfo.value)
        if (res.code === 1) {
            message.success('修改密码成功')
        } else {
            message.error('修改密码失败')
        }
        // 清空表单
        passwordInfo.value = {
            oldPassword: '',
            newPassword: '',
            confirmPassword: ''
        }
    } catch (error) {
        message.error('修改密码失败，请稍后重试')
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

input,
textarea {
    padding: 12px 16px;
    border-radius: 8px;
    border: 1px solid #e5e7eb;
    transition: .3s ease;
    font-family: inherit;
    font-size: 14px;
}

input:focus,
textarea:focus {
    border-color: #7c3aed;
    box-shadow: 0 0 0 3px rgba(124, 58, 237, .12);
    outline: none;
}

textarea {
    resize: vertical;
    min-height: 100px;
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

    .action-buttons {
        flex-direction: column;
    }

    .btn {
        justify-content: center;
    }
}
</style>