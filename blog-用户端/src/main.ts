import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router';

// 1. 引入 v-md-editor 的预览组件和样式
import VMdPreview from '@kangc/v-md-editor/lib/preview';
import '@kangc/v-md-editor/lib/style/preview.css';

// 2. 引入你使用的主题 (例如 github 主题)
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';

// 3. 引入代码高亮 (可选)
import hljs from 'highlight.js';

VMdPreview.use(githubTheme, {
    Hljs: hljs,
});

const app = createApp(App);

// 4. 关键：注册组件
app.use(VMdPreview);

app.use(router)
app.mount('#app')
