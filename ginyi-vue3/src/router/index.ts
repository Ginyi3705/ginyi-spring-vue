import {createRouter, createWebHashHistory, RouteRecordRaw, useRouter} from "vue-router";
import {systemRoute} from "@/router/modules/system";
import {useSystemStore} from "@/store/modules/useSystemStore";

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
    ...systemRoute
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    document.title = `${useSystemStore().title} - ${to.meta.title as string}`
    next()
})

/**
 * 封装路由跳转
 * @param name 路由name
 */
export const useCommonRouter = (name: string) => {
    router.push({name})
}

export default router