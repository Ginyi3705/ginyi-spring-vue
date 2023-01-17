<template>
    <n-layout has-sider class="layout">
        <n-layout-sider
            bordered
            show-trigger="bar"
            collapse-mode="width"
            :collapsed-width="64"
            :collapsed="collapsed"
            :width="240"
            :inverted="true"
            :native-scrollbar="false"
            :on-update:collapsed="(val) => setCollapsed(val)">
            <Logo/>
            <Menu />
        </n-layout-sider>
        <n-layout>
            <n-layout-header :style="{height: (layoutHeaderHeight + tabsHeight) + 'px', padding: '0px 10px 10px 10px'}">
                <div>
                    <Headers :style="{height: layoutHeaderHeight - 10 + 'px'}"/>
                    <TabsView/>
                </div>
            </n-layout-header>
            <n-layout-content :content-style="{padding: '15px', height: clientHeight - (layoutHeaderHeight + layoutFooterHeight + tabsHeight) + 'px',
            backgroundColor: darkTheme ? null : '#f5f7f9'}">
                <router-view/>
            </n-layout-content>
            <n-layout-footer
                :style="{height: layoutFooterHeight + 'px', display: 'flex', alignItems: 'center', justifyContent: 'center'}">
                {{ `Copyright © 2023-Now ${author}. All rights reserved.` }}
            </n-layout-footer>
        </n-layout>
        <Theme/>
    </n-layout>
</template>

<script lang="ts">
import {defineComponent} from 'vue'
import {CaretDownOutline, SparklesOutline} from '@vicons/ionicons5'
import Logo from "@/layout/logo/index.vue";
import {useSystemStore} from "@/store/modules/useSystemStore";
import Headers from "@/layout/header/index.vue";
import {renderIcon} from "@/plugins/naive-ui/common";
import {storeToRefs} from "pinia";
import {setting} from "@/config/setting";
import TabsView from "@/layout/tabs/index.vue";
import Theme from "@/layout/theme/index.vue";
import Menu from "@/layout/menu/index.vue";

export default defineComponent({
    name: "Layout",
    components: {
        Logo,
        Headers,
        TabsView,
        Theme,
        Menu
    },
    setup() {
        const {
            clientHeight,
            layoutHeaderHeight,
            layoutFooterHeight,
            collapsed,
            darkTheme,
            tabsHeight
        } = storeToRefs(useSystemStore());
        const {title, author} = setting;
        const {setCollapsed} = useSystemStore();


        return {
            renderIcon,
            title,
            author,
            clientHeight,
            layoutHeaderHeight,
            layoutFooterHeight,
            collapsed,
            setCollapsed,
            darkTheme,
            tabsHeight,
            Logo,
            Headers,
            CaretDownOutline,
            SparklesOutline
        }
    }
})
</script>
