import {defineStore} from "pinia";
import {storeKeyEnums} from "@/enums/storeKeyEnums";
import {darkTheme} from "naive-ui";
import {BuiltInGlobalTheme} from "naive-ui/es/themes/interface";
import {ISystemState} from "@/interface/modules/system";
import {store} from "@/store";

export const useSystemStore = defineStore(storeKeyEnums.SYSTEM, {
    state: (): ISystemState => ({
        darkTheme: false,
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

export const useUserStoreOut = () => {
    return useSystemStore(store);
}