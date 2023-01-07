import {defineStore} from "pinia";
import {storeKeyEnums} from "@/enums/storeKeyEnums";
import {IUser} from "@/interface/modules/system";

export const useUserStore = defineStore(storeKeyEnums.USER, {
    state: (): IUser => ({
        username: undefined,
        tokenKey: undefined,
        authorization: undefined
    }),
    getters: {
        getName(): string | undefined {
            return this.username
        },
    },
    actions: {
        setUsername(data: string | undefined) {
            this.username = data
        },
        setTokenKey(data: string | undefined) {
            this.tokenKey = data
        },
        setAuthorization(data: string | undefined) {
            this.authorization = data
        }
    }
})