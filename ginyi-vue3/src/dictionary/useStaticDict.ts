import {ref} from "vue";

export const useStaticDict = () => {

    // 状态字典
    const statusDict = ref<Array<any>>([
        {
            label: "正常",
            value: "0",
        },
        {
            label: "禁用",
            value: "1"
        }
    ])

    // 成功与否字典
    const successDict = ref<Array<any>>([
        {
            label: "成功",
            value: "0",
        },
        {
            label: "失败",
            value: "1"
        }
    ])

    // 性别字典
    const sexDict = ref<Array<any>>([
        {
            label: "男",
            value: "0",
        },
        {
            label: "女",
            value: "1"
        },
        {
            label: "未知",
            value: "2"
        }
    ])

    // 菜单类型字典
    const menuTypeDict = ref<Array<any>>([
        {
            label: "目录",
            value: "M",
        },
        {
            label: "菜单",
            value: "C"
        },
        {
            label: "按钮",
            value: "F"
        }
    ])

    // 显示隐藏字典
    const showDict = ref<Array<any>>([
        {
            label: "显示",
            value: "0",
        },
        {
            label: "隐藏",
            value: "1"
        }
    ])

    // 是否字典
    const whetherDict = ref<Array<any>>([
        {
            label: "是",
            value: "0",
        },
        {
            label: "否",
            value: "1"
        }
    ])

    // 操作类型
    const operationType = ref<Array<any>>([
        {
            label: "其他",
            value: "0",
        },
        {
            label: "新增",
            value: "1",
        },
        {
            label: "修改",
            value: "2",
        },
        {
            label: "删除",
            value: "3",
        }
    ])

    return {
        statusDict,
        successDict,
        sexDict,
        menuTypeDict,
        showDict,
        whetherDict,
        operationType
    }
}

