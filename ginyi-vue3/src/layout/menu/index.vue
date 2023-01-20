<template>
    <n-scrollbar>
        <n-menu key-field="name"
                label-field="menuName"
                :collapsed-width="64"
                :accordion="true"
                :inverted="!darkTheme"
                :collapsed-icon-size="22"
                :options="menuOptions"
                :expand-icon="useRenderIcon(CaretDownOutline)"
                :expanded-keys="openKeys"
                v-model:value="activeMenuKey"
                @update:expanded-keys="handleUpdateExpandedKeys"
                @update:value="handleClickMenu"/>
    </n-scrollbar>
</template>

<script lang="ts">
import {defineComponent, ref, watchEffect} from "vue";
import {useRenderIcon} from "@/plugins/naive-ui/common";
import {CaretDownOutline} from "@vicons/ionicons5";
import {useSystemStore} from "@/store/modules/useSystemStore";
import {storeToRefs} from "pinia";
import {useMenuFormat} from "@/hooks/useMenu";
import {MenuOption} from "naive-ui";
import {useCommonRouter} from "@/router";
import {useRoute} from "vue-router";
import {useFindParentName} from "@/hooks/useTree";

export default defineComponent({
    name: "Menu",
    setup() {
        const currentRoute = useRoute();
        const {darkTheme, getMenuList} = storeToRefs(useSystemStore());
        const menuOptions = ref<any>(useMenuFormat(getMenuList?.value ?? []))
        const activeMenuKey = ref<string | undefined>(undefined)
        const openKeys = ref<Array<string>>([])
        const {menuList} = storeToRefs(useSystemStore())

        const handleClickMenu = (key: string, item: MenuOption) => {
            useCommonRouter(item.name as string)
        }
        const handleUpdateExpandedKeys = (keys: string[]) => {
            openKeys.value = keys
        }
        watchEffect(() => {
            activeMenuKey.value = currentRoute.name as string
            menuOptions.value = useMenuFormat(getMenuList?.value ?? [])
            openKeys.value = useFindParentName(currentRoute.name as string, menuList?.value as Array<any>) as Array<string>
        })

        return {
            darkTheme,
            menuOptions,
            useRenderIcon,
            handleClickMenu,
            activeMenuKey,
            handleUpdateExpandedKeys,
            openKeys,
            CaretDownOutline
        }
    }
})
</script>