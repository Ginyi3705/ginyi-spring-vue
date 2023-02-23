<template>
    <div>
        <div>这是首页</div>
        <button @click="getOperationLogList">获取操作日志</button>
        <button @click="getLoginLogList">获取登录日志</button>
        <button @click="getOnlineUserList">获取在线用户</button>
    </div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {logController, onlineController} from "@/api";

export default defineComponent({
    setup() {
        const getOperationLogList = () => {
            logController.getOperationLogList({}).then(res => {
                console.log('操作日志', res)
            })
        }
        const getLoginLogList = () => {
            logController.getLoginLogList({}).then(res => {
                console.log('登录日志', res)
            })
        }
        const getOnlineUserList = () => {
            onlineController.getOnlineUserList({}, {page: 1, pageSize: 20}).then(res => {
                console.log('在线用户', res)
            })
        }
        return {
            getOperationLogList,
            getLoginLogList,
            getOnlineUserList
        }
    }
})
</script>

<style scoped>

</style>