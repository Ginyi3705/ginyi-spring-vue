import {Component, computed} from "vue";
import {createDiscreteApi, NIcon} from "naive-ui";
import {useSystemStore} from "@/store/modules/useSystemStore";
import {storeToRefs} from "pinia";
import {useLighten} from "@/hooks/useColor";

/**
 * 加延时是为了让 pinia 完成初始化，时间可适当调长些
 */
setTimeout(() => {
    const {getTheme, themeColor} = storeToRefs(useSystemStore())
    const configProviderPropsRef = computed(() => ({
        theme: getTheme.value,
        themeOverrides: {
            common: {
                primaryColor: themeColor?.value,
                primaryColorHover: useLighten(themeColor?.value as string, 6),
                primaryColorPressed: useLighten(themeColor?.value as string, 6),
            },
            LoadingBar: {
                colorLoading: themeColor?.value as string,
            },
        },
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