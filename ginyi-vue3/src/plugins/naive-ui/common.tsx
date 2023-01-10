import {Component, computed} from "vue";
import {createDiscreteApi, NIcon} from "naive-ui";
import {useSystemStore} from "@/store/modules/useSystemStore";
import {storeToRefs} from "pinia";

/**
 * 加延时是为了让 pinia 完成初始化，时间可适当调长些
 */
setTimeout(() => {
    const {getTheme} = storeToRefs(useSystemStore())
    const configProviderPropsRef = computed(() => ({
        theme: getTheme.value
    }));
    const {message, dialog, notification, loadingBar} = createDiscreteApi(
        ["message", "dialog", "notification", "loadingBar"],
        {
            configProviderProps: configProviderPropsRef,
        }
    );

    window.$message = message;
    window.$dialog = dialog;
    window.$notification = notification;
    window.$loading = loadingBar;
}, 100)

/**
 * 渲染icon
 * @param icon
 */
export const renderIcon = (icon: Component) => {
    return () => {
        return <NIcon component={icon}/>
    }
}