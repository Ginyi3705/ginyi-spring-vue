import useRequest from "@/api/useRequest";

export class menuController {
    /**
     * 获取路由列表
     * @param data
     */
    static getRouterList() {
        return useRequest({
            url: "/api/menu/getRouterList",
            method: "get",
        })
    }

}