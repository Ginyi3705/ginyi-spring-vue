import axios, {AxiosRequestConfig, AxiosResponse} from "axios";
import {useUserStore} from "@/store/modules/useUserStore";
import {useProjectStore} from "@/store/modules/useProjectStore";
import {h} from "vue";
import {useFormatTime} from "@/hooks/useFormat";

const {tokenKey, authorization} = useUserStore();
const {devBaseURL, prodBaseURL} = useProjectStore();

// 创建axios示例
const service = axios.create({
    baseURL: import.meta.env.DEV ? devBaseURL : prodBaseURL,
    timeout: 5000
});

// 请求拦截
service.interceptors.request.use((config: AxiosRequestConfig<any>) => {
    if (config.headers) {
        typeof config.headers.set === 'function' && config.headers.set(tokenKey, authorization)
    }
    return config
}, (error: any) => {
    window.$notification.error({
        title: "网络请求错误",
        description: `From ${import.meta.env.DEV ? devBaseURL : prodBaseURL}`,
        meta: useFormatTime(new Date().valueOf()),
        content: () => h("span", {},
            typeof error.message === "string" ? error.message : JSON.stringify(error.message)),
        duration: 5000,
        keepAliveOnHover: true
    })
})

// 响应拦截
service.interceptors.response.use(
    (response: AxiosResponse<any, any>) => {
        const res = response.data;
        if (res.code !== 200) {
            window.$notification.error({
                title: res.msg,
                description: `From ${import.meta.env.DEV ? devBaseURL : prodBaseURL}`,
                meta: useFormatTime(new Date().valueOf()),
                content: () => h("span", {},
                    typeof res.data === "string" ? res.data : JSON.stringify(res.data)),
                duration: 5000,
                keepAliveOnHover: true
            })
            return Promise.reject(res);
        } else {
            return res;
        }
    },
    (error: any) => {
        /* 响应拦截失败的情况 */
        window.$notification.error({
            title: "请求响应错误",
            description: `From ${import.meta.env.DEV ? devBaseURL : prodBaseURL}`,
            meta: useFormatTime(new Date().valueOf()),
            content: () => h("span", {},
                typeof error.message === "string" ? error.message : JSON.stringify(error.message)),
            duration: 5000,
            keepAliveOnHover: true
        })
        return Promise.reject(error);
    }
);

export default service



