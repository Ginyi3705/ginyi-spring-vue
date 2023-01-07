import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import {resolve} from 'path'

export default defineConfig({
    envDir: ".",
    plugins: [vue()],
    server: {
        host: '0.0.0.0',    // 主机地址
        open: true,         // 自动打开浏览器
        port: 3800          // 服务端口
    },
    // 路径别名
    resolve: {
        alias: {
            '@': resolve(__dirname, './src')
        }
    },
})