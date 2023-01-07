import {createRouter, createWebHashHistory, RouteRecordRaw} from "vue-router";
import {useProjectStore} from "@/store/modules/useProjectStore";

/**
 * 系统路由
 */
const routes: Array<RouteRecordRaw> = [
    {
        path: "/",
        redirect: "/home",
        component: () => import("@/layout/index.vue"),
        children: [
            {
                path: "home",
                name: "home",
                meta: {title: "首页"},
                component: () => import("@/views/home/index.vue")
            },
        ]
    },
    {
        path: "/login",
        name: "login",
        meta: {title: "登录"},
        component: () => import("@/views/login/index.vue")
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    document.title = `${useProjectStore().title} - ${to.meta.title as string}`
    next()
})

/**
 * 封装路由跳转
 * @param name 路由name
 * @param query 路由参数
 */
export const useCommonRouter = (name: string, query?: any) => {
    router.push({
        name: name,
        query: query
    })
}

export default router