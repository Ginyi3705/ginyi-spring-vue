import {ref} from "vue";

enum actionEnum {
    ADD = "新增",
    EDIT = "编辑",
    QUERY = "查询"
}

export const useCommonForm = (formDefaultValue: any = {},
                              addApi?: Function,
                              editApi?: Function,) => {

    const formRef = ref(undefined)
    const formActionType = ref<string | undefined>(undefined)
    const formValue = ref<any>({...formDefaultValue})

    /**
     * 新增
     */
    const onAdd = () => {
        formActionType.value = actionEnum.ADD
        formValue.value = {...formDefaultValue}
    }

    /**
     * 编辑
     * @param row
     */
    const onEdit = (row: any) => {
        formActionType.value = actionEnum.EDIT
        formValue.value = {...row}
    }

    /**
     * 查询
     */
    const onQuery = () => {
        formActionType.value = actionEnum.QUERY
        return new Promise((resolve, reject) => {
            resolve(formValue.value)
        })
    }


    /**
     * 保存
     */
    const onSubmit = () => {
        // @ts-ignore
        formRef.value?.validate(err => {
            if (!err) {

            } else {

            }
        })
    }

    /**
     * 重置
     */
    const onReset = () => {
        return new Promise((resolve, reject) => {
            const formDefaultKeys = Object.keys(formDefaultValue)
            const formKeys = Object.keys(formValue.value)
            formKeys.map(key => {
                if (formDefaultKeys.includes(key)) {
                    formValue.value[key] = formDefaultValue[key]
                } else {
                    formValue.value[key] = null
                }
            })
            resolve(formValue.value)
        })
    }


    return {
        onAdd,
        onEdit,
        onQuery,
        onSubmit,
        onReset,
        formValue,
        formRef
    }
}
