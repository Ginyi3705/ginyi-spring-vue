<template>
    <div style="height: 50px; display: flex; align-items: center; margin-left: 15px;">
        <n-avatar v-if="collapsed" round size="medium" :src="logo" style="margin-right: 10px"/>
        <div v-else style="display: flex; align-items: center; margin-left: 15px;">
            <n-avatar round size="medium" :src="logo" style="margin-right: 10px"/>
            <h3 style="white-space: nowrap">{{ `${title} ${name}` }}</h3>
        </div>
    </div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {useSystemStore} from "@/store/modules/useSystemStore";
import {storeToRefs} from "pinia";

export default defineComponent({
    name: "Logo",
    setup() {
        const {logo, title, name, collapsed} = storeToRefs(useSystemStore());
        return {
            logo, title, name, collapsed
        }
    }
})
</script>