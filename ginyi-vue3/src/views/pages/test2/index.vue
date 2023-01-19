<template>
    <div>
        <h1>这是测试页2</h1>
        <n-button @click="useCommonRouter('home')">跳转回首页</n-button>
    </div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {useCommonRouter} from "@/router";

export default defineComponent({
    setup() {
        return {
            useCommonRouter
        }
    }
})
</script>