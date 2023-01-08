import useRequest from "@/api/useRequest";

export class userController {
    /**
     * 用户登录
     * @param data
     */
    static login(data: any) {
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
    static logout() {
        return useRequest({
            url: "/api/user/logout",
            method: "post",
        })
    }

    /**
     * 获取验证码
     */
    static captcha() {
        return useRequest({
            url: "/api/verify/captcha",
            method: "get"
        })
    }
}