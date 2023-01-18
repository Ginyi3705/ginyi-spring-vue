import {loadIcon, renderIcon} from "@/plugins/naive-ui/common";

/**
 * 重新格式菜单列表，主要是为了渲染 icon 和 删除空children
 * @param menuList
 */
export const useMenuFormat = (menuList: any): any => {
    return menuList.map((menu: any) => {
        menu.icon = renderIcon(loadIcon("PersonOutline"))
        if (menu.children?.length > 0) {
            useMenuFormat(menu.children)
        }else {
            delete menu.children
        }
        return menu
    })
}