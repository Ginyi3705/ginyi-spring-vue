<template>
    <div>
        <n-button type="primary" @click="addTag({id: new Date().valueOf(), tagName: '测' + new Date().valueOf()})">
            添加一个Tag
        </n-button>
    </div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {useSystemStore} from "@/store/modules/useSystemStore";

export default defineComponent({
    setup() {
        const addTag = (data: any) => {
            useSystemStore().addTag(data)
        }

        return {
            addTag,
            useSystemStore
        }
    }
})
</script>

<style scoped>

</style>