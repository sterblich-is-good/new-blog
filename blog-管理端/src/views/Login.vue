<template>
    <div class="login-wrapper">
        <a-card title="博客后台系统登录" :bordered="false" class="login-card">
            <a-form :model="formState" name="basic" autocomplete="off" @finish="onFinish"
                @finishFailed="onFinishFailed">
                <a-form-item label="账号" name="username" :rules="[{ required: true, message: '请输入账号！' }]">
                    <a-input v-model:value="formState.username" placeholder="请输入账号" />
                </a-form-item>

                <a-form-item label="密码" name="password" :rules="[{ required: true, message: '请输入密码！' }]">
                    <a-input-password v-model:value="formState.password" placeholder="请输入密码" />
                </a-form-item>

                <a-form-item>
                    <a-button block type="primary" html-type="submit">登录</a-button>
                </a-form-item>
            </a-form>

        </a-card>
    </div>
</template>
<script lang="ts" setup>
import { reactive } from 'vue';
import { type FormState } from "../types/users"
import { currentPOST } from '../api'
import { message } from 'ant-design-vue' // 如果你用的是 antd 的 message 弹框
import { useRouter } from 'vue-router'

const router = useRouter()

const formState = reactive<FormState>({
    username: '',
    password: '',
});
const onFinish = async (values: any) => {
    const res: any = await currentPOST('url1', values);

    if (res.code == 1) {
        message.success("登录成功")
        router.push('/')
    } else {
        // 其他状态处理
        message.error(res.msg || '未知错误')
    }

};

const onFinishFailed = (errorInfo: any) => {
    console.log(errorInfo);
};
</script>
<style scoped>
/* 1. 让外层容器撑满全屏 */
.login-wrapper {
    width: 100vw;
    /* 占据浏览器 100% 宽度 */
    height: 100vh;
    /* 占据浏览器 100% 高度 */
    display: flex;
    /* 开启弹性布局 */
    justify-content: center;
    /* 水平居中 */
    align-items: center;
    /* 垂直居中 */
    background-color: #f0f2f5;
    /* 设置一个背景底色，避免死白 */
}

/* 2. 登录卡片样式优化 */
.login-card {
    width: 400px;
    /* 稍微加宽一点更美观 */
    padding: 10px;
    box-shadow: 0 12px 32px rgba(0, 0, 0, 0.1);
    /* 加深阴影增加悬浮感 */
    border-radius: 8px;
    background-color: #ffffff;
    /* 确保卡片是纯白的 */
}

/* 如果你的 a-card 标题想居中，可以加这个 */
:deep(.ant-card-head-title) {
    text-align: center;
    font-size: 1.2rem;
    font-weight: bold;
}
</style>