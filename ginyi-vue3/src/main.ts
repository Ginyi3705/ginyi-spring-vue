import {createApp} from 'vue'
import App from '@/App.vue'
import router from "@/router";
import naive from 'naive-ui';
import {initStore} from '@/store';
import "@/plugins/naive-ui/common"

const app = createApp(App)
initStore(app)

app.use(router)
app.use(naive)
app.mount('#app')
