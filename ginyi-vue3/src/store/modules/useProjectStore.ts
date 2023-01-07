import {defineStore} from "pinia";
import {storeKeyEnums} from "@/enums/storeKeyEnums";
import {IProject} from "@/interface/modules/system";
import Logo from "@/assets/img/logo.jpg";

export const useProjectStore = defineStore(storeKeyEnums.PROJECT, {
    state: (): IProject => ({
        devBaseURL: "http://127.0.0.1:8066",
        prodBaseURL: "http://127.0.0.1",
        logo: Logo,
        title: "Ginyi",
        name: "管理系统",
        author: "Ginyi@aliyun.com",
    }),
    getters: {

    },
    actions: {

    }
})