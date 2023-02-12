import {computed, ref} from "vue";

enum actionEnum {
    ADD = "新增",
    EDIT = "编辑",
}

export const useCommonModal = (name: string,
                               formDefaultValue: any = {},
                               addApi: Function,
                               editApi: Function,
                               deleteApi?: Function,
                               batchDeleteApi?: Function) => {

    const modalShow = ref<boolean>(false)
    const modalLoading = ref<boolean>(false)
    const modalTitle = ref<string | undefined>(undefined)
    const modalFormRef = ref(undefined)
    const modalForm = ref<any>({...formDefaultValue})

    /**
     * 新增
     */
    const onAdd = () => {
        modalShow.value = true
        modalTitle.value = actionEnum.ADD
        modalForm.value = {...formDefaultValue}
    }

    /**
     * 编辑
     * @param row
     */
    const onEdit = (row: any) => {
        modalShow.value = true
        modalTitle.value = actionEnum.EDIT
        modalForm.value = {...row}
    }


    /**
     * 保存
     */
    const onSubmit = () => {
        // @ts-ignore
        modalFormRef.value?.validate(err => {
            if(!err) {
                if (modalTitle.value === actionEnum.ADD) {
                    addApi(modalForm.value).then((res: any) => {
                        window.$message.success(res.msg)
                        modalLoading.value = true
                    })
                }
                if (modalTitle.value === actionEnum.EDIT) {
                    editApi(modalForm.value).then((res: any) => {
                        window.$message.success(res.msg)
                        modalLoading.value = true
                    })
                }
            }else {
                modalShow.value = true
            }
        })
    }

    /**
     * 删除
     */
    const onDeleteById = (id: number | string) => {
        window.$dialog.error({
            title: "温馨提醒",
            content: "删除操作不可逆，是否继续？",
            positiveText: "确定",
            negativeText: "取消",
            onPositiveClick: () => {
                deleteApi && deleteApi(id).then((res: any) => {
                    window.$message.success(res.msg)
                    modalLoading.value = true
                })
            }
        })
    }

    /**
     * 批量删除
     */
    const onDeleteByIds = (ids: Array<number | string>) => {
        window.$dialog.error({
            title: "温馨提醒",
            content: "删除操作不可逆，是否继续？",
            positiveText: "确定",
            negativeText: "取消",
            onPositiveClick: () => {
                batchDeleteApi && batchDeleteApi(ids).then((res: any) => {
                    window.$message.success(res.msg)
                    modalLoading.value = true
                })
            }
        })
    }

    return {
        onAdd,
        onEdit,
        onSubmit,
        onDeleteById,
        onDeleteByIds,
        modalForm,
        modalFormRef,
        modalShow,
        modalLoading,
        modalTitle: computed(() => {
            return modalTitle.value + name
        }),
    }
}
