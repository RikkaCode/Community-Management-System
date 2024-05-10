import { createApp } from 'vue'
import router from './router/index'
// import './style.css'
import App from './App.vue'
import myConfirm from './utils/myConfirm'

// 引入element-plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

// 引入中文
import zhCn from 'element-plus/es/locale/lang/zh-cn'

// 引入Pinia构造函数
import { createPinia } from 'pinia'
import piniaPersist from 'pinia-plugin-persist'

// 实例化Pinia
const pinia = createPinia()
pinia.use(piniaPersist)

const app = createApp(App);
app.use(ElementPlus, {
    locale: zhCn
}).use(router).use(pinia).mount('#app')

// 全局注册图标组件
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

//全局注册
app.config.globalProperties.$myconfirm = myConfirm