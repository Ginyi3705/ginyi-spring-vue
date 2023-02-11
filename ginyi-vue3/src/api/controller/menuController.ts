import useRequest from "@/api/useRequest";

export class menuController {
    /**
     * 获取路由列表
     */
    static getRouterList(): Promise<any> {
        return useRequest({
            url: "/api/menu/getRouterList",
            method: "get",
        })
    }

}