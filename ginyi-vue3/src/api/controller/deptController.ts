import useRequest from "@/api/useRequest";
import {IPage} from "@/interface/modules/system";

export class deptController {
    /**
     * 获取部门列表
     * @param data
     * @param pagination
     */
    static list(data: any, pagination?: IPage): Promise<any> {
        return useRequest({
            url: "/api/dept/list",
            method: "post",
            params: pagination,
            data
        })
    }

}