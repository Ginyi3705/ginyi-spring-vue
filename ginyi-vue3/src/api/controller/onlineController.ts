import useRequest from "@/api/useRequest";
import {IPage} from "@/interface/modules/system";

export class onlineController {
    /**
     * 获取在线用户
     * @param data
     * @param pagination
     */
    static getOnlineUserList(data: any, pagination?: IPage): Promise<any> {
        return useRequest({
            url: "/api/online/getOnlineUserList",
            method: "get",
            params: pagination,
        })
    }

}