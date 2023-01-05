<template>
    <n-config-provider :theme="getTheme">
        <router-view/>
    </n-config-provider>
</template>

<script setup lang="ts">
import {useSystemStore} from "@/store/modules/useSystemStore";
import {storeToRefs} from "pinia";

const {getTheme} = storeToRefs(useSystemStore())

</script>
