<template>
    <div>
        <div>这是首页</div>
        <n-button type="primary" @click="addTab({id: new Date().valueOf(), tagName: '测' + new Date().valueOf()})">
            添加一个Tag
        </n-button>
        <n-button @click="useCommonRouter('test1')">跳转到test1</n-button>
        <n-button @click="useCommonRouter('test2')">跳转到test2</n-button>
        <n-button @click="useCommonRouter('test4')">跳转到test4</n-button>
        <n-button @click="useCommonRouter('test5')">跳转到test5</n-button>
        <n-button @click="useCommonRouter('test8')">跳转到test8</n-button>
        <n-button @click="useCommonRouter('test9')">跳转到test9</n-button>
        <n-button @click="getRoutes">获取路由</n-button>
    </div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {useSystemStore} from "@/store/modules/useSystemStore";
import {useCommonRouter} from "@/router";
import {useRouter} from "vue-router";

export default defineComponent({
    setup() {
        const router = useRouter()

        const addTab = (data: any) => {
            useSystemStore().addTab(data)
        }
        const getRoutes = () => {
            console.log(router.getRoutes())
        }

        return {
            addTab,
            useSystemStore,
            useCommonRouter,
            getRoutes
        }
    }
})
</script>

<style scoped>

</style>