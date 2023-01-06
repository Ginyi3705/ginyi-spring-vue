<template>
    <n-breadcrumb>
        <n-breadcrumb-item>
            一级菜单
        </n-breadcrumb-item>
        <n-breadcrumb-item>
            二级菜单
        </n-breadcrumb-item>
        <n-breadcrumb-item>
            三级菜单
        </n-breadcrumb-item>
    </n-breadcrumb>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {ApertureOutline, Bicycle, BulbOutline} from "@vicons/ionicons5";

export default defineComponent({
    name: "Breadcrumb ",
    setup() {

        return {
            ApertureOutline, Bicycle, BulbOutline
        }
    }
})
</script>