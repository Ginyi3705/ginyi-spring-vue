<template>
    <n-breadcrumb>
        <n-breadcrumb-item v-for="(item, index) in breadMenuList" :key="index">
            <n-icon style="margin-right: 5px">
                <Icon :icon="item.icon"/>
            </n-icon>
            <n-dropdown v-if="index !== breadMenuList.length - 1"
                        :options="breadMenuList[index].children"
                        placement="bottom-start"
                        key-field="menuId"
                        label-field="menuName"
                        :render-icon="renderDropdownIcon(breadMenuList[index].icon)"
                        @select="handleSelect"
                        trigger="click">
                {{ item.menuName }}
            </n-dropdown>
            <span v-else>{{ item.menuName }}</span>
        </n-breadcrumb-item>
    </n-breadcrumb>
</template>

<script lang="ts">
import {defineComponent, watch} from "vue";
import {ApertureOutline, Bicycle, BulbOutline} from "@vicons/ionicons5";
import {useRoute} from "vue-router";
import {useFindParentNodes} from "@/hooks/useTree";
import {useDeepClone} from "@/hooks/useObject";
import {storeToRefs} from "pinia";
import {useSystemStore} from "@/store/modules/useSystemStore";
import {useLoadIcon, useRenderIcon} from "@/plugins/naive-ui/common";
import {DropdownOption} from "naive-ui";
import {useCommonRouter} from "@/router";


export default defineComponent({
    name: "Breadcrumb ",
    setup() {
        const currentRoute = useRoute();
        const {menuList, breadMenuList} = storeToRefs(useSystemStore());

        const renderDropdownIcon = (icon: string) => {
            return useRenderIcon(useLoadIcon(icon))
        }
        const handleSelect = (key: string, option: DropdownOption) => {
            useCommonRouter(option.name as string)
        }

        watch(() => currentRoute.name, () => {
            const list = useFindParentNodes(currentRoute.name as string, useDeepClone(menuList?.value as Array<any>)) as Array<any>
            useSystemStore().setBreadMenuList(list)
        })

        return {
            breadMenuList,
            handleSelect,
            renderDropdownIcon,
            ApertureOutline, Bicycle, BulbOutline
        }
    }
})
</script>