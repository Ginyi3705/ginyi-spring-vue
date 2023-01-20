import {defineStore} from "pinia";
import {storeKeyEnums} from "@/enums/storeKeyEnums";
import {IRouterType} from "@/interface/modules/system";
import {RouteRecordRaw} from "vue-router";
import router from "@/router";


export const useRouterStore = defineStore(storeKeyEnums.ROUTER, {
    state: (): IRouterType => ({
        routesList: []
    }),
    actions: {
        setRoutesList(data: Array<any>, prevRoute?: RouteRecordRaw) {
            data?.forEach((menu: any) => {
                if (prevRoute) {
                    menu.path = prevRoute ? `${prevRoute.path}/${menu.path}` : menu.path
                }
                // C代表菜单，其余是目录和按钮
                if (menu.menuType.toUpperCase() === "C") {
                    menu.meta = {title: menu.menuName}
                    router.addRoute("Layout", menu)
                    this.routesList?.push(menu);
                }
                if (menu.children?.length > 0) {
                    this.setRoutesList(menu.children, menu)
                }
            })
        },
        addRoutes() {
            const modules = import.meta.glob('../../views/**/*.vue')
            this.routesList?.forEach(route => {
                const temp: RouteRecordRaw = {
                    path: route.path,
                    name: route.name,
                    meta: route.meta,
                    component: modules[`../../views/${route.component}.vue`]
                }
                router.addRoute("Layout", temp)
            })
        }
    }
})