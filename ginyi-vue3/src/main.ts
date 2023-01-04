import {createApp} from 'vue'
import App from './App.vue'

/**
 * 路由
 */
import router from "./router";

/**
 * 状态管理pinia
 */
import {createPinia} from "pinia";
import {piniaPlugin} from "@/store/piniaPlugin";

const pinia = createPinia()
pinia.use(piniaPlugin())

const app = createApp(App)
app.use(router)
app.use(pinia)
app.mount('#app')
