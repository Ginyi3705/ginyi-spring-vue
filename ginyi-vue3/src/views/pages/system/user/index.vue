<template>
    <div>
        <CommonTable :columns="userListColumns"
                     :dataList="tableDataList"
                     :labelField="'title'"
                     :rowKey="(row) => row.userId"
                     :actionColData="actionCol"
                     :actionWidth="310"
                     :pagination="tablePagination"
                     :loading="tableLoading"
                     @onPageChange="onPageChange"
                     @onPageSizeChange="onPageSizeChange"
                     @onEvent="onEvent">
            <template #query>
                <UserQueryForm @onQuery="onQuery" @onReset="onReset"/>
            </template>
        </CommonTable>
        <UserEditForm ref="userEditFormRef"/>
    </div>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref, watch} from "vue";
import CommonTable from "@/components/commonTable/index.vue"
import {userListColumns} from "@/views/pages/system/user/userListColumns";
import {userController} from "@/api";
import {SelectGroupOption, SelectOption} from "naive-ui";
import {useStatusDict} from "@/dictionary/useSystemDict";
import UserEditForm from "@/views/pages/system/user/userEditForm.vue";
import UserQueryForm from "@/views/pages/system/user/userQueryForm.vue";
import {useCommonTable} from "@/components/commonTable/useCommonTable";

export default defineComponent({
    components: {
        UserEditForm, CommonTable, UserQueryForm
    },
    setup() {

        const {
            tableDataList,
            tablePagination,
            tableLoading,
            onPageChange,
            onPageSizeChange,
            getDataList
        } = useCommonTable(userController.getUserList)

        const userEditFormRef = ref(null)
        const userId = ref<number | undefined>(undefined)

        // 状态下拉配置项
        const options = ref<Array<SelectOption | SelectGroupOption>>(useStatusDict)
        // 操作列
        const actionCol = ref<Array<any>>([
            {
                title: "重置密码",
                colorType: "success",
                actionType: 4,
            }
        ])

        // @ts-ignore
        watch(() => {userEditFormRef?.value?.modalLoading}, () => getDataList(), {deep: true})

        const onEvent = (value: any) => {
            switch (value.type) {
                // 新增
                case 0:
                    // @ts-ignore
                    userEditFormRef?.value?.onAdd()
                    break
                // 编辑
                case 1:
                    // @ts-ignore
                    userEditFormRef?.value?.onEdit(value.row)
                    break
                // 删除
                case 2:
                    // @ts-ignore
                    userEditFormRef?.value?.onDeleteById(value.row.userId)
                    break
                // 批量删除
                case 3:
                    // @ts-ignore
                    userEditFormRef?.value?.onDeleteByIds(value.data)
                    break
                // 重置密码
                case 4:
                    window.$message.warning("您点击了重置密码")
                    break
            }
        }

        const onQuery = (value: any) => {
            getDataList(value)
        }

        const onReset = (value: any) => {
            getDataList(value)
        }

        onMounted(() => {
            getDataList()
        })

        return {
            userListColumns,
            tableDataList,
            tablePagination,
            tableLoading,
            actionCol,
            onPageChange,
            onPageSizeChange,
            onEvent,
            options,
            userEditFormRef,
            userId,
            onQuery,
            onReset
        }
    }
})
</script>
