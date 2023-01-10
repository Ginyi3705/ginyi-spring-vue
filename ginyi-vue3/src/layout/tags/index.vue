<template>
    <div class="tagsView" :style="{backgroundColor: getTheme ? '#18181c' : 'white'}">
        <div :id="'tagsView' + item.id" :class="item.id === index ? 'tabs-active' : 'tabs'" v-for="item in list"
             :key="item.id"
             :style="{color: getTheme ? 'white' : null}"
             @click="onClickTag(item)">
            <div class="tabs-title">
                <n-icon>
                    <GameController/>
                </n-icon>
                <span>{{ item.name }}</span>
                <n-icon>
                    <CloseOutline/>
                </n-icon>
            </div>
            <div :class="item.id === index ? 'tabs-active-divider' : 'tabs-divider'"></div>
            <svg :class="item.id === index ? 'tabs-active-before' : 'tabs-before'" width="7" height="7"
                 :style="{fill: item.id === index ? '#e6e5f2' : ''}">
                <path d="M 0 7 A 7 7 0 0 0 7 0 L 7 7 Z"/>
            </svg>
            <svg :class="item.id === index ? 'tabs-active-after' : 'tabs-after'" width="7" height="7"
                 :style="{fill: item.id === index ? '#e6e5f2' : ''}">
                <path d="M 0 7 A 7 7 0 0 0 7 0 L 7 7 Z"></path>
            </svg>
        </div>
    </div>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from "vue";
import {CloseOutline, GameController} from '@vicons/ionicons5'
import {storeToRefs} from "pinia";
import {useSystemStore} from "@/store/modules/useSystemStore";

export default defineComponent({
    name: "TagsView",
    components: {
        CloseOutline, GameController
    },
    setup() {
        const {getTheme} = storeToRefs(useSystemStore());
        // 选中的tag
        const index = ref<number | undefined>(1);
        // 选中的tag颜色
        const activeBackgroundColor = ref<string | undefined | null>("#18a058");
        // 初始化未选中的颜色
        const initBackgroundColor = () => {
            const tabs = document.getElementsByClassName("tabs")[0];
            const tabsBefore = document.getElementsByClassName("tabs-before")[0];
            const tabsAfter = document.getElementsByClassName("tabs-after")[0];

            if (tabs instanceof HTMLElement) {
                tabs.style.background = getTheme !== undefined ? "#18181c" : "white";
            }
            if (tabsBefore instanceof SVGElement) {
                tabsBefore.style.fill = getTheme !== undefined ? "#18181c" : "white";
            }
            if (tabsAfter instanceof SVGElement) {
                tabsAfter.style.fill = getTheme !== undefined ? "#18181c" : "white";
            }
        }
        // 初始化选中的颜色
        const initActiveBackgroundColor = () => {
            const tabsActive = document.getElementsByClassName("tabs-active")[0];
            const tabsBeforeActive = document.getElementsByClassName("tabs-active-before")[0];
            const tabsAfterActive = document.getElementsByClassName("tabs-active-after")[0];

            if (tabsActive instanceof HTMLElement) {
                tabsActive.style.background = activeBackgroundColor.value as string;
            }
            if (tabsBeforeActive instanceof SVGElement) {
                tabsBeforeActive.style.fill = activeBackgroundColor.value as string;
            }
            if (tabsAfterActive instanceof SVGElement) {
                tabsAfterActive.style.fill = activeBackgroundColor.value as string;
            }
        }
        const onClickTag = (item: any) => {
            index.value = item.id
        }
        const list = ref<Array<{ id: number, name: string }>>([
            {
                id: 1,
                name: '工作台'
            },
            {
                id: 2,
                name: '上传图片'
            },
            {
                id: 3,
                name: '弹窗扩展'
            },
            {
                id: 4,
                name: '海外完工清单录入'
            },
            {
                id: 5,
                name: '在线文档'
            }
        ])

        onMounted(() => {
            initBackgroundColor();
            initActiveBackgroundColor();
            list.value.forEach(item => {
                const tagsView = document.getElementById("tagsView" + item.id);
                if (tagsView) {
                    // 给 tagsView 添加鼠标监听
                    const childNodes = tagsView.childNodes;
                    tagsView.onmouseover = () => {
                        tagsView.style.background = activeBackgroundColor.value as string;
                        childNodes.forEach(child => {
                            if (child.nodeName === "svg" && child instanceof SVGElement) {
                                child.style.fill = activeBackgroundColor.value as string;
                            }
                        })
                    }
                    tagsView.onmouseout = () => {
                        tagsView.style.background = "";
                        childNodes.forEach(child => {
                            if (child.nodeName === "svg" && child instanceof SVGElement) {
                                child.style.fill = "";
                            }
                        })
                    }
                    if (childNodes.length > 0) {
                        // 给 tagsView 里边的内容元素添加鼠标监听
                        childNodes.forEach(child => {
                            if (child instanceof HTMLElement) {
                                child.onmouseover = () => {
                                    child.style.background = activeBackgroundColor.value as string;
                                }
                                child.onmouseout = () => {
                                    child.style.background = "";
                                }
                            }
                        })
                    }
                }
            })

        })

        return {
            getTheme,
            index,
            activeBackgroundColor,
            list,
            onClickTag
        }
    }
})
</script>

<style lang="less">
.tagsView {
    padding: 0 10px 0 15px;
    display: flex;

    .tabs {
        height: 30px;
        position: relative;
        border-radius: 8px 8px 0 0;
        display: flex;
        align-items: center;
        justify-content: space-between;

        &-title {
            width: 100%;
            height: 14px;
            display: flex;
            align-items: center;
            padding: 0 6px 0 10px;

            span {
                margin: 0 10px 0 5px;
            }
        }

        &-divider {
            width: 1px;
            height: 15px;
            position: absolute;
            right: -1px;
            z-index: 9999;
            background-color: #8a8a8a;
        }

        &-before {
            position: absolute;
            left: -6px;
            bottom: 0;
        }

        &-after {
            position: absolute;
            right: -6px;
            bottom: 0;
            transform: rotate(90deg);
        }
    }

    .tabs-active {
        height: 30px;
        position: relative;
        border-radius: 8px 8px 0 0;
        display: flex;
        align-items: center;
        justify-content: space-between;
        z-index: 9999;

        &-title {
            width: 100%;
            height: 14px;
            display: flex;
            align-items: center;
            padding: 0 6px 0 10px;

            span {
                margin: 0 10px 0 5px;
            }
        }

        &-divider {
            opacity: 0;
        }

        &-before {
            position: absolute;
            left: -6px;
            bottom: 0;
        }

        &-after {
            position: absolute;
            right: -6px;
            bottom: 0;
            transform: rotate(90deg);
        }
    }

    .tabs:hover {
        cursor: pointer;
        z-index: 999999;

        .tabs-before {
            position: absolute;
            left: -6px;
            bottom: 0;
        }

        .tabs-after {
            position: absolute;
            right: -6px;
            bottom: 0;
            transform: rotate(90deg);
        }

        .tabs-divider {
            opacity: 0;
        }
    }

}
</style>
