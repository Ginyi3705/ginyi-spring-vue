<template>
    <div style="display: flex; align-items: center;height: 100%">
        <Breadcrumb/>
    </div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import Breadcrumb from "@/layout/header/breadcrumb.vue";

export default defineComponent({
    name: "Headers",
    components: {
        Breadcrumb
    },
    setup() {

        return {
            Breadcrumb
        }
    }
})
</script>