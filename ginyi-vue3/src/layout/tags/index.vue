<template>
    <div class="tagsView">
        <div :id="'tagsView_' + item.id" :class="item.id === index ? 'tabs-active' : 'tabs'" v-for="item in list"
             :key="item.id"
             :style="{color: getTheme ||  item.id === index ? activeFontColor :  fontColor,
             backgroundColor: item.id === index ? useHexToRgba(activeBackgroundColor) : null}"
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
                 :style="{fill: item.id === index ? useHexToRgba(activeBackgroundColor) : getTheme ? systemDarkBackgroundColor  : activeFontColor}">
                <path d="M 0 7 A 7 7 0 0 0 7 0 L 7 7 Z"/>
            </svg>
            <svg :class="item.id === index ? 'tabs-active-after' : 'tabs-after'" width="7" height="7"
                 :style="{fill: item.id === index ? useHexToRgba(activeBackgroundColor) : getTheme ? systemDarkBackgroundColor  : activeFontColor}">
                <path d="M 0 7 A 7 7 0 0 0 7 0 L 7 7 Z"></path>
            </svg>
        </div>
    </div>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref, watchEffect} from "vue";
import {CloseOutline, GameController} from '@vicons/ionicons5'
import {storeToRefs} from "pinia";
import {useSystemStore} from "@/store/modules/useSystemStore";
import {useHexToRgba} from "@/hooks/useColor";

export default defineComponent({
    name: "TagsView",
    components: {
        CloseOutline, GameController
    },
    setup() {
        const {getTheme, themeColor} = storeToRefs(useSystemStore());
        // 选中的tag
        const index = ref<number | undefined>(1);
        // 系统暗色背景色
        const systemDarkBackgroundColor = ref<string>("#18181c")
        // 选中的tag颜色
        const activeBackgroundColor = ref<string | undefined | null>(useHexToRgba(themeColor?.value as string));
        // 未选中的文字颜色
        const fontColor = ref<string>("#18181c")
        // 选中的文字颜色
        const activeFontColor = ref<string>("#ffffff")

        watchEffect(() => {
            activeBackgroundColor.value = themeColor?.value
        })

        // 初始化未选中的颜色
        const initBackgroundColor = () => {
            const tabs = document.getElementsByClassName("tabs")[0];
            const tabsBefore = document.getElementsByClassName("tabs-before")[0];
            const tabsAfter = document.getElementsByClassName("tabs-after")[0];

            if (tabs instanceof HTMLElement) {
                tabs.style.backgroundColor = getTheme.value !== undefined ? systemDarkBackgroundColor.value : activeFontColor.value;
            }
            if (tabsBefore instanceof SVGElement) {
                tabsBefore.style.fill = getTheme.value !== undefined ? systemDarkBackgroundColor.value : activeFontColor.value;
            }
            if (tabsAfter instanceof SVGElement) {
                tabsAfter.style.fill = getTheme.value !== undefined ? systemDarkBackgroundColor.value : activeFontColor.value;
            }
        }
        // 初始化选中的颜色
        const initActiveBackgroundColor = () => {
            const tabsActive = document.getElementsByClassName("tabs-active")[0];
            const tabsBeforeActive = document.getElementsByClassName("tabs-active-before")[0];
            const tabsAfterActive = document.getElementsByClassName("tabs-active-after")[0];

            if (tabsActive instanceof HTMLElement) {
                tabsActive.style.backgroundColor = useHexToRgba(activeBackgroundColor.value as string);
            }
            if (tabsBeforeActive instanceof SVGElement) {
                tabsBeforeActive.style.fill = useHexToRgba(activeBackgroundColor.value as string);
            }
            if (tabsAfterActive instanceof SVGElement) {
                tabsAfterActive.style.fill = useHexToRgba(activeBackgroundColor.value as string);
            }
        }
        // 初始化鼠标事件
        const initMouseEvent = (index?: number) => {
            list.value.forEach((item, key) => {
                const tagsView = document.getElementById("tagsView_" + item.id);

                if (tagsView) {
                    const childNodes = tagsView.childNodes;
                    // 除选中的tag外，其余的背景色进行重置
                    if (index && tagsView.id.indexOf(index.toString()) === -1) {
                        tagsView.style.backgroundColor = "";
                    }
                    // 给 tagsView 添加鼠标【到达】事件监听
                    tagsView.onmouseover = () => {
                        tagsView.style.backgroundColor = useHexToRgba(activeBackgroundColor.value as string);
                        tagsView.style.color = activeFontColor.value

                        // 隐藏当前tag和上一个tag的分割线
                        const currentNode = document.getElementById("tagsView_" + list.value[key]?.id);
                        const prevNode = document.getElementById("tagsView_" + list.value[key - 1]?.id);
                        if (currentNode instanceof HTMLElement) {
                            const currentDivider = currentNode.childNodes[1];
                            if (currentDivider instanceof HTMLElement) {
                                currentDivider.style.opacity = '0'
                            }
                        }
                        if (prevNode instanceof HTMLElement) {
                            const prevDivider = prevNode.childNodes[1];
                            if (prevDivider instanceof HTMLElement) {
                                prevDivider.style.opacity = '0'
                            }
                        }

                        childNodes.forEach(child => {
                            if (child.nodeName === "svg" && child instanceof SVGElement) {
                                child.style.fill = useHexToRgba(activeBackgroundColor.value as string);
                            }
                        })
                    }
                    // 给 tagsView 添加鼠标【离开】事件监听
                    tagsView.onmouseout = () => {
                        if (index && tagsView.id.indexOf(index.toString()) === -1) {
                            tagsView.style.backgroundColor = "";
                            tagsView.style.color = getTheme.value !== undefined ? activeFontColor.value : fontColor.value;
                        }

                        // 显示当前tag和上一个tag的分割线
                        const currentNode = document.getElementById("tagsView_" + list.value[key]?.id);
                        const prevNode = document.getElementById("tagsView_" + list.value[key - 1]?.id);
                        if (currentNode instanceof HTMLElement) {
                            const currentDivider = currentNode.childNodes[1];
                            if (currentDivider instanceof HTMLElement) {
                                currentDivider.style.opacity = '1'
                            }
                        }
                        if (prevNode instanceof HTMLElement) {
                            const prevDivider = prevNode.childNodes[1];
                            if (prevDivider instanceof HTMLElement) {
                                prevDivider.style.opacity = '1'
                            }
                        }

                        childNodes.forEach(child => {
                            if (child.nodeName === "svg" && child instanceof SVGElement) {
                                if (index && tagsView.id.indexOf(index.toString()) === -1) {
                                    child.style.fill = getTheme.value !== undefined ? systemDarkBackgroundColor.value : activeFontColor.value;
                                }
                            }
                        })
                    }
                }
            })
        }
        const onClickTag = (item: any) => {
            index.value = item.id
            initMouseEvent(index.value)
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
            initMouseEvent(index.value);
        })

        return {
            getTheme,
            index,
            systemDarkBackgroundColor,
            activeBackgroundColor,
            fontColor,
            activeFontColor,
            list,
            onClickTag,
            useHexToRgba
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
        cursor: pointer;

        &-title {
            width: 100%;
            height: 14px;
            display: flex;
            align-items: center;
            padding: 0 6px 0 10px;

            span {
                margin: 0 10px 0 5px;
                user-select: none;
            }
        }

        &-divider {
            width: 1px;
            height: 15px;
            position: absolute;
            right: -1px;
            background-color: #8a8a8a;
        }

        &-before {
            position: absolute;
            left: -7px;
            bottom: 0;
        }

        &-after {
            position: absolute;
            right: -7px;
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
            left: -7px;
            bottom: 0;
        }

        &-after {
            position: absolute;
            right: -7px;
            bottom: 0;
            transform: rotate(90deg);
        }
    }

    .tabs:hover {
        cursor: pointer;
        z-index: 9999;

        .tabs-before {
            position: absolute;
            left: -7px;
            bottom: 0;
        }

        .tabs-after {
            position: absolute;
            right: -7px;
            bottom: 0;
            transform: rotate(90deg);
        }
    }
}
</style>
