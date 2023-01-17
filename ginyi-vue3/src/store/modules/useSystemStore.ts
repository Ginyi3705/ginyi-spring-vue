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
        layoutHeaderHeight: 58,
        layoutFooterHeight: 40,
        collapsed: false,
        tabsHeight: 20,
        tabIndex: tabsList[0].id,
        tabsList: tabsList,
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
            this.tabIndex = data
        },
        addTag(data: { id: number, tagName: string }) {
            this.tabIndex = data.id
            this.tabsList?.push(data)

            setTimeout(() => {
                const tabsView = document.getElementById("tabsView");
                const tabsTransition = document.getElementById("tabsTransition");
                if (tabsView && tabsTransition) {
                    if (tabsTransition.offsetWidth > tabsView.offsetWidth) {
                        tabsView.scrollTo({left: tabsTransition.offsetWidth, behavior: 'smooth'});
                    }
                }
            }, 50)
        },
        removeTab(tagId: number) {
            this.tabsList = this.tabsList?.filter(tag => {
                return tagId !== tag.id
            })
            if (this.tabsList && tagId === this.tabIndex) {
                this.tabIndex = this.tabsList[0].id
            }
        },
        removeLeftTabs(index: number) {
            this.tabsList?.splice(1, index - 1)
            const closeSelf = this.tabsList?.some(item => {
                return item.id === this.tabIndex
            })
            if (!closeSelf && this.tabsList) {
                this.tabIndex = this.tabsList[0].id
            }
        },
        removeRightTabs(index: number) {
            this.tabsList?.splice(index + 1, this.tabsList?.length - 1)
            const closeSelf = this.tabsList?.some(item => {
                return item.id === this.tabIndex
            })
            if (!closeSelf && this.tabsList) {
                this.tabIndex = this.tabsList[0].id
            }
        },
        removeOtherTabs(index: number) {
            this.tabsList = this.tabsList?.filter((item, key) => {
                return key === 0 || key === index
            })
            const closeSelf = this.tabsList?.some(item => {
                return item.id === this.tabIndex
            })
            if (!closeSelf && this.tabsList) {
                this.tabIndex = this.tabsList[0].id
            }
        },
        removeAllTabs() {
            this.tabsList = this.tabsList?.filter((item, key) => {
                return key === 0
            })
            if (this.tabsList) {
                this.tabIndex = this.tabsList[0].id
            }
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

const tabsList: Array<{ id: number, tagName: string }> = [
    {
        id: 1,
        tagName: '首页'
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