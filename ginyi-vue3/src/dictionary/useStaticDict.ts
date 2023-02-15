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

    return {
        statusDict,
        sexDict
    }
}

