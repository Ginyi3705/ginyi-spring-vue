import {RouteRecordRaw} from "vue-router";

/**
 * 系统路由
 */
export const systemRoute: Array<RouteRecordRaw> = [
    {
        path: "/",
        name: "home",
        meta: {title: "首页"},
        component: () => import("@/views/home/index.vue")
    },
    {
        path: "/login",
        name: "login",
        meta: {title: "登录"},
        component: () => import("@/views/login/index.vue")
    }
]