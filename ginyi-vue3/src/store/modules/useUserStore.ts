import {defineStore} from "pinia";
import {storeKeyEnums} from "@/enums/storeKeyEnums";
import {IUser} from "@/interface/modules/system";

export const useUserStore = defineStore(storeKeyEnums.USER, {
    state: (): IUser => ({
        username: undefined
    }),
    getters: {
        getName(): string | undefined {
            return this.username
        },
    },
    actions: {
        setName(data: string | undefined) {
            this.username = data
        }
    }
})