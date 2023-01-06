import {RouteRecordRaw} from "vue-router";

/**
 * 系统路由
 */
export const systemRoute: Array<RouteRecordRaw> = [
    {
        path: "/login",
        name: "login",
        meta: {title: "登录"},
        component: () => import("@/views/login/index.vue")
    }
]