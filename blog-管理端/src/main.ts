import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
// import 'ant-design-vue/dist/reset.css';
import router from './router'
import STable from '@surely-vue/table';

createApp(App).use(router).use(STable).mount('#app');



