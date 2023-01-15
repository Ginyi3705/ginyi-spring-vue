<template>
    <div>
        <n-button type="primary" @click="getRouterList">获取路由菜单列表</n-button>
        <n-button type="primary" @click="addTag({id: new Date().valueOf(), tagName: '测' + new Date().valueOf()})">
            添加一个Tag
        </n-button>
    </div>
</template>

<script lang="ts">
import {defineComponent, ref} from "vue";
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

        const getInitialItems = () => [1, 2, 3, 4, 5]
        const items = ref(getInitialItems())
        let id = items.value.length + 1

        function insert() {
            const i = Math.round(Math.random() * items.value.length)
            items.value.splice(i, 0, id++)
        }

        function reset() {
            items.value = getInitialItems()
        }

        function shuffle() {
            items.value = _shuffle(items.value)
        }

        function remove(item: any) {
            const i = items.value.indexOf(item)
            if (i > -1) {
                items.value.splice(i, 1)
            }
        }

        return {
            addTag,
            tagList,
            items,
            shuffle,
            reset,
            insert,
            remove,
            getRouterList,
            useSystemStore
        }
    }
})
</script>

<style scoped>

</style>