<template>
    <div style="display: flex; align-items: center">
        <n-icon :component="ChevronBack" size="25" style="cursor: pointer"/>
        <div class="tagsView" id="tagsView" ref="tagsView">
            <div ref="tabsTransitionWidth">
                <transition-group name="tag" tag="div" class="tabs-transition">
                    <div :id="'tagsView_' + item.id" :class="item.id === tagIndex ? 'tabs-active' : 'tabs'"
                         v-for="item in tagList"
                         :key="item.id"
                         :style="{color: getTheme ||  item.id === tagIndex ? activeFontColor :  null,backgroundColor: item.id === tagIndex ? useHexToRgba(activeBackgroundColor) : null}"
                         @click="onClickTag(item)">
                        <div class="tabs-title">
                            <n-icon>
                                <GameController/>
                            </n-icon>
                            <span>{{ item.tagName }}</span>
                            <n-icon style="border-radius: 50%" :component="CloseOutline"
                                    @click="() => removeTag(item.id)"
                                    :id="`tabs-close-${item.id}`"/>
                        </div>
                        <svg :class="item.id === tagIndex ? 'tabs-active-before' : 'tabs-before'" width="7" height="7"
                             :style="{fill: item.id === tagIndex ? useHexToRgba(activeBackgroundColor) : 'transparent'}">
                            <path d="M 0 7 A 7 7 0 0 0 7 0 L 7 7 Z"/>
                        </svg>
                        <svg :class="item.id === tagIndex ? 'tabs-active-after' : 'tabs-after'" width="7" height="7"
                             :style="{fill: item.id === tagIndex ? useHexToRgba(activeBackgroundColor) : 'transparent'}">
                            <path d="M 0 7 A 7 7 0 0 0 7 0 L 7 7 Z"></path>
                        </svg>
                    </div>
                </transition-group>
            </div>
        </div>
        <n-icon :component="ChevronForward" size="25" style="cursor: pointer"/>
    </div>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref, watch, watchEffect} from "vue";
import {ChevronBack, ChevronForward, CloseOutline, GameController} from '@vicons/ionicons5'
import {storeToRefs} from "pinia";
import {useSystemStore} from "@/store/modules/useSystemStore";
import {useHexToRgba} from "@/hooks/useColor";
// @ts-ignore
import elementResizeDetectorMaker from "element-resize-detector";

export default defineComponent({
    name: "TagsView",
    components: {
        CloseOutline, GameController,
        ChevronBack, ChevronForward
    },
    setup() {
        const tabsTransitionWidth = ref<HTMLElement | undefined>(undefined)
        const tagsView = ref<HTMLElement | undefined>(undefined)
        const {getTheme, themeColor, tagIndex, tagList} = storeToRefs(useSystemStore());
        // 选中的tag颜色
        const activeBackgroundColor = ref<string | undefined | null>(useHexToRgba(themeColor?.value as string));
        // 选中的文字颜色
        const activeFontColor = themeColor

        watchEffect(() => {
            activeBackgroundColor.value = themeColor?.value
        })

        watch(() => tagIndex?.value, () => {
            tagToView();
        })

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
            tagList?.value?.forEach((item, key) => {
                const tagsView = document.getElementById(`tagsView_${item.id}`);
                if (tagsView) {
                    const childNodes = tagsView.childNodes;
                    // 给 tagsView 添加鼠标【到达】事件监听
                    tagsView.onmouseover = () => {
                        tagsView.style.backgroundColor = useHexToRgba(activeBackgroundColor.value as string);
                        tagsView.style.color = activeFontColor?.value as string
                        childNodes.forEach(child => {
                            if (child.nodeName.toUpperCase() === "SVG" && child instanceof SVGElement) {
                                child.style.fill = useHexToRgba(activeBackgroundColor.value as string);
                            }
                        })
                    }
                    // 给 tagsView 添加鼠标【离开】事件监听
                    tagsView.onmouseout = () => {
                        if (tagIndex?.value && tagsView.id.indexOf(tagIndex?.value.toString()) === -1) {
                            tagsView.style.backgroundColor = "";
                            tagsView.style.color = getTheme.value !== undefined ? activeFontColor?.value as string : "";
                        }
                        childNodes.forEach(child => {
                            if (child.nodeName === "svg" && child instanceof SVGElement) {
                                if (tagIndex?.value && tagsView.id.indexOf(tagIndex?.value.toString()) === -1) {
                                    child.style.fill = 'transparent';
                                }
                            }
                        })
                    }

                    // 关闭图片的鼠标监听事件
                    const closeIcon = document.getElementById(`tabs-close-${item.id}`);
                    if (closeIcon) {
                        closeIcon.onmouseover = () => {
                            closeIcon.style.backgroundColor = useHexToRgba(activeBackgroundColor.value as string, 0.3);
                            closeIcon.style.opacity = "0.5"
                        }
                        closeIcon.onmouseout = () => {
                            closeIcon.style.backgroundColor = ""
                            closeIcon.style.opacity = "1"
                        }
                    }

                }
            })
        }

        const onClickTag = (item: any) => {
            useSystemStore().setTagIndex(item.id);
            initMouseEvent(tagIndex?.value);
            tagToView();
        }

        const removeTag = (tagId: number) => {
            useSystemStore().removeTag(tagId)
        }

        const onElementResize = () => {
            elementResizeDetectorMaker().listenTo(tabsTransitionWidth.value, () => {
                if (tagsView.value && tabsTransitionWidth.value) {
                    if (tabsTransitionWidth.value.offsetWidth > tagsView.value.offsetWidth) {
                        tagsView.value.scrollTo({left: tagsView.value.scrollWidth, behavior: 'smooth'});
                        tagsView.value.style.overflow = "hidden"
                    }
                }
            })
        }

        const tagToView = () => {
            const tagsBox = document.getElementsByClassName("tabs-transition")[0];
            if (tagsBox && tagsBox instanceof HTMLElement) {
                (tagsBox.childNodes || [])?.forEach(tag => {
                    if (tag instanceof HTMLElement && (tag.id === `tagsView_${tagIndex?.value}`)) {
                        tag.scrollIntoView && tag.scrollIntoView({
                            behavior: "smooth",  // 平滑过渡
                        });
                    }
                })
            }
        }

        onMounted(() => {
            initActiveBackgroundColor();
            initMouseEvent(tagIndex?.value);
            onElementResize();
        })

        return {
            tabsTransitionWidth,
            getTheme,
            activeBackgroundColor,
            activeFontColor,
            CloseOutline,
            ChevronBack, ChevronForward,
            onClickTag,
            useHexToRgba,
            removeTag,
            tagIndex,
            tagList,
            tagsView
        }
    }
})
</script>

<style lang="less">
.tagsView {
    width: 100%;
    display: flex;
    overflow-x: auto;
    overflow-y: hidden;
    padding: 0 5px 0 5px;

    .tabs-transition {
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
                height: 15px;
                display: flex;
                align-items: center;
                padding: 0 6px 0 10px;

                span {
                    margin: 0 10px 0 5px;
                    user-select: none;
                    white-space: nowrap;
                }
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

        .tabs-active:hover {
            cursor: pointer;
        }

        .tabs:hover {
            cursor: pointer;
        }

        .tag-move,
        .tag-enter-active,
        .tag-leave-active {
            transition: all 0.3s ease;
        }

        .tag-enter-from,
        .tag-leave-to {
            opacity: 0;
            transform: translateX(-30px);
        }

        .tag-leave-active {
            position: absolute;
        }
    }
}


</style>
