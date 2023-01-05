import {createApp} from 'vue'
import App from './App.vue'

import router from "./router";
import {createPinia} from "pinia";
import {piniaPlugin} from "@/plugins/pinia/piniaPlugin";
import naive from 'naive-ui';

const pinia = createPinia()
pinia.use(piniaPlugin())

const app = createApp(App)
app.use(router)
app.use(pinia)
app.use(naive)
app.mount('#app')
