import {defineStore} from "pinia";
import {storeKeyEnums} from "@/enums/storeKeyEnums";
import {darkTheme} from "naive-ui";
import {BuiltInGlobalTheme} from "naive-ui/es/themes/interface";

export interface ISystemState {
    // 系统深色主题
    darkTheme: boolean | undefined;
}

export const useSystemStore = defineStore(storeKeyEnums.SYSTEM, {
    state: (): ISystemState => ({
        darkTheme: undefined
    }),
    getters: {
        getTheme(): BuiltInGlobalTheme | undefined {
            return this.darkTheme ? darkTheme : undefined
        }
    },
    actions: {
        setTheme(data: boolean | undefined) {
            this.darkTheme = data
        }
    }
})