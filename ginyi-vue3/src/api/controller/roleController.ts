import useRequest from "@/api/useRequest";
import {IPage} from "@/interface/modules/system";

export class roleController {
    /**
     * 获取角色列表
     * @param data
     * @param pagination
     */
    static list(data: any, pagination?: IPage): Promise<any> {
        return useRequest({
            url: "/api/role/list",
            method: "post",
            params: pagination,
            data
        })
    }

}