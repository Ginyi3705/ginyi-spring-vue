import {defineStore} from "pinia";
import {storeKeyEnums} from "@/enums/storeKeyEnums";
import {darkTheme} from "naive-ui";
import {BuiltInGlobalTheme} from "naive-ui/es/themes/interface";
import {ISystemState} from "@/interface/modules/system";
import Logo from "@/assets/img/logo.jpg"

export const useSystemStore = defineStore(storeKeyEnums.SYSTEM, {
    state: (): ISystemState => ({
        darkTheme: undefined,
        logo: Logo,
        title: "Ginyi",
        name: "管理系统",
        author: "Ginyi@aliyun.com",
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
        getCollapsed(): boolean  | undefined {
            return this.collapsed
        }
    },
    actions: {
        setCollapsed(data: boolean | undefined){
            this.collapsed = data
        }
    }
})