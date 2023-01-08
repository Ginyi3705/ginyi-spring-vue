<template>
    <div>
        <n-button type="primary" @click="getRouterList">获取路由菜单列表</n-button>
    </div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {menuController} from "@/api";

export default defineComponent({
    setup() {
        const getRouterList = () => {
            menuController.getRouterList().then(res => {
                console.log('=====>>', res)
            })
        }
        return {
            getRouterList
        }
    }
})
</script>

<style scoped>

</style>