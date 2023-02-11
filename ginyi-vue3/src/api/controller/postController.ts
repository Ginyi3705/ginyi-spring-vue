import useRequest from "@/api/useRequest";
import {IPage} from "@/interface/modules/system";

export class postController {
    /**
     * 获取岗位列表
     * @param data
     * @param pagination
     */
    static list(data: any, pagination?: IPage): Promise<any> {
        return useRequest({
            url: "/api/post/list",
            method: "post",
            params: pagination,
            data
        })
    }

}