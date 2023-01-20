<template>
    <n-scrollbar>
        <n-menu key-field="menuId"
                label-field="menuName"
                :collapsed-width="64"
                :inverted="!darkTheme"
                :collapsed-icon-size="22"
                :options="menuOptions"
                :expand-icon="useRenderIcon(CaretDownOutline)"
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

export default defineComponent({
    name: "Menu",
    setup() {
        const {darkTheme, getMenuList} = storeToRefs(useSystemStore());
        const menuOptions = ref<any>(useMenuFormat(getMenuList?.value ?? []))
        const handleClickMenu = (key: string, item: MenuOption) => {
            useCommonRouter(item.name as string)
        }
        watchEffect(() => {
            menuOptions.value = useMenuFormat(getMenuList?.value ?? [])
        })

        return {
            darkTheme,
            menuOptions,
            useRenderIcon,
            handleClickMenu,
            CaretDownOutline
        }
    }
})
</script>