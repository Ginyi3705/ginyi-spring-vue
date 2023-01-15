<template>
    <div>
        <n-button type="primary" @click="getRouterList">获取路由菜单列表</n-button>
        <n-button type="primary" @click="addTag({id: new Date().valueOf(), tagName: '测' + new Date().valueOf()})">
            添加一个Tag
        </n-button>
        <n-button type="primary" @click="toFirst">
            跳转到第一个
        </n-button>
    </div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {menuController} from "@/api";
import {useSystemStore} from "@/store/modules/useSystemStore";
import {storeToRefs} from "pinia";

export default defineComponent({
    setup() {
        const {tagList} = storeToRefs(useSystemStore());
        const getRouterList = () => {
            menuController.getRouterList().then(res => {
                console.log('=====>>', res)
            })
        }

        const addTag = (data: any) => {
            useSystemStore().addTag(data)
        }

        const toFirst = () => {
            useSystemStore().toFirstTag()
        }

        return {
            addTag,
            toFirst,
            tagList,
            getRouterList,
            useSystemStore
        }
    }
})
</script>

<style scoped>

</style>