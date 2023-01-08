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
            <n-menu :collapsed-width="64"
                    :inverted="true"
                    :collapsed-icon-size="22"
                    :options="menuOptions"
                    :expand-icon="renderIcon(CaretDownOutline)"/>
        </n-layout-sider>
        <n-layout>
            <n-layout-header :style="{height: layoutHeaderHeight + 'px', padding: '10px'}">
                <Headers/>
            </n-layout-header>
            <n-layout-content :content-style="{padding: '15px', height: clientHeight - (layoutHeaderHeight + layoutFooterHeight) + 'px',
            backgroundColor: darkTheme ? null : '#f5f7f9'}">
                <router-view/>
            </n-layout-content>
            <n-layout-footer
                :style="{height: layoutFooterHeight + 'px', display: 'flex', alignItems: 'center', justifyContent: 'center'}">
                {{ `Copyright © 2023-Now ${author}. All rights reserved.` }}
            </n-layout-footer>
        </n-layout>
    </n-layout>

</template>

<script lang="ts">
import {defineComponent} from 'vue'
import {
    BookOutline as BookIcon,
    CaretDownOutline,
    PersonOutline as PersonIcon,
    WineOutline as WineIcon
} from '@vicons/ionicons5'
import Logo from "@/layout/logo/index.vue";
import {useSystemStore} from "@/store/modules/useSystemStore";
import Headers from "@/layout/header/index.vue";
import {renderIcon} from "@/plugins/naive-ui/common";
import {storeToRefs} from "pinia";
import {setting} from "@/config/setting";

export default defineComponent({
    name: "Layout",
    components: {
        Logo,
        Headers
    },
    setup() {
        const {
            clientHeight,
            layoutHeaderHeight,
            layoutFooterHeight,
            collapsed,
            darkTheme
        } = storeToRefs(useSystemStore());
        const {title, author} = setting;
        const {setCollapsed} = useSystemStore();

        const menuOptions = [
            {
                label: '且听风吟',
                key: 'hear-the-wind-sing',
                icon: renderIcon(BookIcon)
            },
            {
                label: '1973年的弹珠玩具',
                key: 'pinball-1973',
                icon: renderIcon(BookIcon),
                disabled: true,
                children: [
                    {
                        label: '鼠',
                        key: 'rat'
                    }
                ]
            },
            {
                label: '寻羊冒险记',
                key: 'a-wild-sheep-chase',
                disabled: true,
                icon: renderIcon(BookIcon)
            },
            {
                label: '舞，舞，舞',
                key: 'dance-dance-dance',
                icon: renderIcon(BookIcon),
                children: [
                    {
                        type: 'group',
                        label: '人物',
                        key: 'people',
                        children: [
                            {
                                label: '叙事者',
                                key: 'narrator',
                                icon: renderIcon(PersonIcon)
                            },
                            {
                                label: '羊男',
                                key: 'sheep-man',
                                icon: renderIcon(PersonIcon)
                            }
                        ]
                    },
                    {
                        label: '饮品',
                        key: 'beverage',
                        icon: renderIcon(WineIcon),
                        children: [
                            {
                                label: '威士忌',
                                key: 'whisky'
                            }
                        ]
                    },
                    {
                        label: '食物',
                        key: 'food',
                        children: [
                            {
                                label: '三明治',
                                key: 'sandwich'
                            }
                        ]
                    },
                    {
                        label: '过去增多，未来减少',
                        key: 'the-past-increases-the-future-recedes'
                    }
                ]
            }
        ]

        return {
            menuOptions,
            renderIcon,
            title, author, clientHeight, layoutHeaderHeight, layoutFooterHeight, collapsed, setCollapsed, darkTheme,
            Logo, Headers, CaretDownOutline
        }
    }
})
</script>
