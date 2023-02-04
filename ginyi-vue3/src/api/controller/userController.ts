import useRequest from "@/api/useRequest";
import {IPage} from "@/interface/modules/system";

export class userController {
    /**
     * 用户登录
     * @param data
     */
    static login(data: any): Promise<any> {
        return useRequest({
            url: "/api/user/login",
            method: "post",
            data
        })
    }

    /**
     * 退出登录
     * @param data
     */
    static logout(): Promise<any> {
        return useRequest({
            url: "/api/user/logout",
            method: "post",
        })
    }

    /**
     * 获取验证码
     */
    static captcha(): Promise<any> {
        return useRequest({
            url: "/api/verify/captcha",
            method: "get"
        })
    }

    /**
     * 用户列表
     */
    static getUserList(data: any, pagination?: IPage): Promise<any> {
        return useRequest({
            url: "/api/user/list",
            method: "post",
            params: pagination,
            data
        })
    }
}