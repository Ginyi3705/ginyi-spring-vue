import {defineStore} from "pinia";
import {storeKeyEnums} from "@/enums/storeKeyEnums";
import {darkTheme} from "naive-ui";
import {BuiltInGlobalTheme} from "naive-ui/es/themes/interface";
import {ISystemState} from "@/interface/modules/system";

export const useSystemStore = defineStore(storeKeyEnums.SYSTEM, {
    state: (): ISystemState => ({
        darkTheme: false,
        themeColor: themeColorList[0],
        themeColorList: themeColorList,
        clientHeight: document.body.clientHeight,
        clientWidth: document.body.clientWidth,
        layoutHeaderHeight: 60,
        layoutFooterHeight: 40,
        tabsHeight: 30,
        collapsed: false,
        tagIndex: tagsList[0].id,
        tagList: tagsList,
        isRemoveFlag: false,
        prevTagsViewWidth: 0
    }),
    getters: {
        getTheme(): BuiltInGlobalTheme | undefined {
            return this.darkTheme ? darkTheme : undefined
        },
        getCollapsed(): boolean | undefined {
            return this.collapsed
        }
    },
    actions: {
        setTheme(data: boolean | undefined) {
            this.darkTheme = data
        },
        setThemeColor(data: string | undefined) {
            this.themeColor = data
        },
        setClientHeight(data: number | undefined) {
            this.clientHeight = data
        },
        setClientWidth(data: number | undefined) {
            this.clientWidth = data
        },
        setCollapsed(data: boolean | undefined) {
            this.collapsed = data
        },
        setTagIndex(data: number | undefined) {
            this.tagIndex = data
        },
        setTagList(data: Array<{ id: number, tagName: string }>) {
            this.tagList = data
        },
        addTag(data: { id: number, tagName: string }) {
            this.tagIndex = data.id
            this.tagList?.push(data)

            const tabsView = document.getElementById("tabsView");
            const tabsTransition = document.getElementById("tabsTransition");
            if (tabsView && tabsTransition) {
                if (tabsTransition.offsetWidth > tabsView.offsetWidth) {
                    setTimeout(() => {
                        tabsView.scrollTo({left: tabsTransition.offsetWidth, behavior: 'smooth'});
                    }, 50)
                    tabsView.style.overflow = "auto"
                }
                this.prevTagsViewWidth = tabsTransition.offsetWidth
            }
        },
        removeTag(tagId: number) {
            this.tagList = this.tagList?.filter(tag => {
                return tagId !== tag.id
            })
        },
        locateCurrent(): any {
            if (this.tagList !== undefined && this.tagIndex !== undefined) {
                // // 是tag显示在可视区域
                const tabsList = document.getElementsByClassName("tabs-transition")[0];
                if (tabsList && tabsList instanceof HTMLElement) {
                    (tabsList.childNodes || [])?.forEach(tag => {
                        if (tag instanceof HTMLElement && (tag.id === `tagsView_${this.tagIndex}`)) {
                            tag.scrollIntoView && tag.scrollIntoView({
                                inline: "center",
                                behavior: "smooth",  // 平滑过渡
                            });
                        }
                    })
                }
            }
        },
        setIsRemoveFlag(data: boolean): Promise<any> {
            this.isRemoveFlag = data
            return new Promise((resolve) => {
                resolve(null)
            })
        },
        setPrevTagsViewWidth(data: number) {
            this.prevTagsViewWidth = data
        }
    }
})

const themeColorList: Array<string> = [
    '#9A53FE',
    '#e88080',
    '#0084f4',
    '#009688',
    '#536dfe',
    '#ff5c93',
    '#ee4f12',
    '#0096c7',
    '#9c27b0',
    '#ff9800',
    '#FF3D68',
    '#00C1D4',
    '#71EFA3',
    '#78DEC7',
    '#1768AC',
    '#63e2b7',
    '#A8EA3E',
    '#E03D3D',
    '#E63AAF',
    '#40EA9A',
]

const tagsList = [
    {
        id: 1,
        tagName: '工作台'
    },
    {
        id: 2,
        tagName: '上传图片'
    },
    {
        id: 3,
        tagName: '弹窗扩展'
    },
    {
        id: 4,
        tagName: '海外完工清单录入'
    },
    {
        id: 5,
        tagName: '在线文档'
    }
]