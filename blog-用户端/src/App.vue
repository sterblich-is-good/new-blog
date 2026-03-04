<template>
  <div class="app-container">
    <header class="top-nav">
      <div class="nav-inner-container">
        <div class="brand">数字工具管理</div>

        <nav class="links">
          <router-link to="/" class="nav-link">首页</router-link>
          <router-link to="/archive" class="nav-link">归档</router-link>
          <router-link to="/about" class="nav-link">关于</router-link>
          <button @click="toggleDark" class="dark-mode-btn">
            {{ isDark ? '🌙' : '☀️' }}
          </button>
        </nav>
      </div>
    </header>

    <main class="main-body">
      <router-view></router-view>
    </main>

    <footer class="site-footer">
      <span class="footer-item">© 2026 数字工具管理</span>
      <span class="divider"></span> <a class="footer-item icon-link"
        href="https://beian.mps.gov.cn/#/query/webSearch?code=22240402222517" rel="noreferrer" target="_blank">
        吉公网安备 22240402222517号
      </a>
    </footer>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

//暗黑模式
const isDark = ref(false)
const toggleDark = () => {
  isDark.value = !isDark.value
  if (isDark.value) {
    document.documentElement.classList.add('dark')
  } else {
    document.documentElement.classList.remove('dark')
  }
}
</script>

<style>
/* --- 1. 核心修正：确保根容器占满全部宽度 --- */
html,
body,
#app {
  margin: 0;
  padding: 0;
  width: 100%;
  min-height: 100vh;
}

.app-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  /* 关键：强制所有子元素在水平方向居中 */
}

/* --- 2. 导航栏修正 --- */
.top-nav {
  width: 100%;
  /* 导航栏背景要铺满全宽 */
  position: sticky;
  top: 0;
  background: var(--nav-bg);
  backdrop-filter: blur(10px);
  border-bottom: 1px solid var(--border-color);
  display: flex;
  justify-content: center;
  /* 确保内部容器居中 */
  z-index: 1000;
}

.nav-inner-container {
  width: 100%;
  max-width: 900px;
  /* 限制最大宽度 */
  height: 70px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 40px;
  /* 你要求的 MyBlog 向右靠，就调这里 */
}

/* --- 3. 内容区修正 --- */
.main-body {
  width: 100%;
  display: flex;
  justify-content: center;
  /* 即使缩放到33%，也会把内容推向中间 */
  padding: 60px 20px;
}

.content-limit-wrapper {
  width: 100%;
  max-width: 700px;
  /* 内容区宽度，TripleZ 风格 */
}

/* --- 基础样式保持不变 --- */
.brand {
  font-size: 1.4rem;
  font-weight: 800;
  margin-left: 10px;
  color: var(--text-color);
}

.nav-link {
  text-decoration: none;
  cursor: pointer;
  color: var(--post-excerpt);
  margin-left: 30px;
}

.nav-link:hover {
  color: var(--accent-color);
}

.nav-link.active {
  color: #ff66b2;
}

.post-item {
  margin-bottom: 70px;
}

.title {
  font-size: 1.8rem;
  margin: 10px 0;
}

.site-footer {
  display: flex;
  /* 核心：变为一行 */
  justify-content: center;
  /* 居中对齐 */
  align-items: center;
  /* 垂直居中 */
  gap: 16px;
  /* 元素之间的间距 */

  padding: 40px 0;
  color: #888;
  /* 统一淡灰色，不抢主体内容视线 */
  font-size: 0.85rem;
  /* 稍微缩小字体，更显精致 */
  font-family: -apple-system, "Noto Sans SC", sans-serif;
}

/* 统一样式：文字和链接 */
.footer-item {
  text-decoration: none;
  color: inherit;
  /* 继承父元素的灰色 */
  transition: color 0.3s ease;
}

/* 链接悬停效果：变黑一点，并加个淡淡的下划线 */
.footer-item:hover {
  color: #333;
}

/* 中间的分割线（比直接打 "|" 更精致） */
.divider {
  width: 1px;
  height: 12px;
  background-color: #e0e0e0;
}

/* 适配移动端：如果屏幕太窄就换回两行 */
@media (max-width: 600px) {
  .site-footer {
    flex-direction: column;
    gap: 8px;
  }

  .divider {
    display: none;
    /* 竖屏时隐藏分割线 */
  }
}


.dark-mode-btn {
  background: none;
  border: 1px solid var(--border-color);
  padding: 5px 10px;
  cursor: pointer;
  border-radius: 20px;
  font-size: 1.2rem;
  margin-left: 20px;
  transition: all 0.3s;
}

.dark-mode-btn:hover {
  background: var(--border-color);
}
</style>