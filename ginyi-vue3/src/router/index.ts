import {createRouter, createWebHashHistory, RouteRecordRaw} from "vue-router";
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

export default router