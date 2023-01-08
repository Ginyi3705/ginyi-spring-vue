<template>
    <n-config-provider :theme="getTheme">
        <n-loading-bar-provider>
            <n-dialog-provider>
                <n-notification-provider>
                    <n-message-provider>
                        <router-view/>
                    </n-message-provider>
                </n-notification-provider>
            </n-dialog-provider>
        </n-loading-bar-provider>
    </n-config-provider>
</template>

<script setup lang="ts">
import {useSystemStore} from "@/store/modules/useSystemStore";
import {useThrottle} from "@/hooks/useDebthro";
import {storeToRefs} from "pinia";

const {getTheme} = storeToRefs(useSystemStore())

// 节流，可以适当增加时间
window.addEventListener("resize", useThrottle(() => {
    useSystemStore().setClientWidth(document.body.clientWidth)
    useSystemStore().setClientHeight(document.body.clientHeight)
}, 0))


</script>
