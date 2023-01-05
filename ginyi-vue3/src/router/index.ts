import {createRouter, createWebHashHistory, RouteRecordRaw} from "vue-router";

import {systemRoute} from "@/router/modules/system";

const routes: Array<RouteRecordRaw> = [
    ...systemRoute
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

router.beforeEach((to, from, next) =>{
    document.title = "Ginyi - " + to.meta.title as string
    next()
})

export default router