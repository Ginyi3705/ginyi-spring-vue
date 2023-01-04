import {defineStore} from "pinia";
import {storeKeyEnums} from "@/enums/storeKeyEnums";

export interface ISystemState {
    theme: string | undefined;
}

export const useSystemStore = defineStore(storeKeyEnums.SYSTEM, {
    state: (): ISystemState => ({
        theme: undefined
    }),
    getters: {
        getTheme(): string | undefined {
            return this.theme
        }
    },
    actions: {
        setToken(data: string | undefined) {
            this.theme = data
        }
    }
})