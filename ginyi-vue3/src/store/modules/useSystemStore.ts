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
        tagList: tagsList
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
        },
        removeTag(tagId: number) {
            this.tagList = this.tagList?.filter((tag, key) => {
                return tagId !== tag.id
            })
        },
        toFirstTag(): any {
            if (this.tagList !== undefined && this.tagIndex !== undefined) {
                this.tagIndex = this.tagList[0].id
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