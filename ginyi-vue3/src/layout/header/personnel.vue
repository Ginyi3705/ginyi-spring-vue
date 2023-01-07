<template>
    <div style="display: flex; align-items: center">
        <span style="margin-right: 15px">{{ username }}</span>
        <n-dropdown :options="options" @select="handleSelect" :show-arrow="true">
            <n-avatar round size="medium" :src="logo" style="margin-right: 10px"/>
        </n-dropdown>
    </div>
</template>

<script lang="ts">
import {defineComponent, ref} from "vue";
import {storeToRefs} from "pinia";
import {LogOutOutline as LogoutIcon, PersonCircleOutline as UserIcon} from '@vicons/ionicons5'
import {renderIcon} from "@/plugins/naive-ui/common";
import {useUserStore} from "@/store/modules/useUserStore";
import {useCommonRouter} from "@/router";
import {useProjectStore} from "@/store/modules/useProjectStore";


export default defineComponent({
    name: "Personnel",
    setup() {
        const {username} = storeToRefs(useUserStore());
        const {logo} = storeToRefs(useProjectStore());
        const options = ref<Array<{ label: string, key: string, icon?: Function }>>([
            {
                label: '个人中心',
                key: 'person',
                icon: renderIcon(UserIcon)
            },
            {
                label: '退出登录',
                key: 'logout',
                icon: renderIcon(LogoutIcon)
            }
        ])
        const handleSelect = (key: string) => {
            if (key === "logout") {
                useCommonRouter("login")
            }
        }

        return {
            logo,
            options,
            username,
            handleSelect
        }
    }
})
</script>

<style scoped>

</style>