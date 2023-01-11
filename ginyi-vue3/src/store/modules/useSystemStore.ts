import {defineStore} from "pinia";
import {storeKeyEnums} from "@/enums/storeKeyEnums";
import {darkTheme} from "naive-ui";
import {BuiltInGlobalTheme} from "naive-ui/es/themes/interface";
import {ISystemState} from "@/interface/modules/system";
import {store} from "@/store";

export const useSystemStore = defineStore(storeKeyEnums.SYSTEM, {
    state: (): ISystemState => ({
        darkTheme: false,
        themeColor: themeColorList[0],
        clientHeight: document.body.clientHeight,
        clientWidth: document.body.clientWidth,
        layoutHeaderHeight: 60,
        layoutFooterHeight: 40,
        collapsed: false
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
        setClientHeight(data: number | undefined) {
            this.clientHeight = data
        },
        setClientWidth(data: number | undefined) {
            this.clientWidth = data
        },
        setCollapsed(data: boolean | undefined) {
            this.collapsed = data
        }
    }
})

const themeColorList:Array<string> = [
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