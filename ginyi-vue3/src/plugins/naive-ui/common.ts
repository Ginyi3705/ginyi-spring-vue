import {Component, h} from "vue";
import {NIcon} from "naive-ui";

/**
 * 渲染icon
 * @param icon
 */
export const renderIcon = (icon: Component) => {
    return () => {
        return h(NIcon, null, {
            default: () => h(icon)
        })
    }
}