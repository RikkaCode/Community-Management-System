import { createApp } from 'vue'
import router from './router/index'
//引入element plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import myConfirm from './utils/myConfirm'
import piniaPersist from 'pinia-plugin-persist'
// echarts
import * as echarts from 'echarts'
// 引入Pinia构造函数
import { createPinia } from 'pinia'

// 实例化 Pinia
const pinia = createPinia()
//使用持久化插件
pinia.use(piniaPersist)

import App from './App.vue'

// createApp(App).mount('#app')
const app = createApp(App);
app.use(router).use(ElementPlus, {
    locale: zhCn,
  }).use(pinia).mount('#app')

// 全局注册图标组件
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

// 全局注册
app.config.globalProperties.$myconfirm = myConfirm
app.config.globalProperties.$echarts = echarts;

