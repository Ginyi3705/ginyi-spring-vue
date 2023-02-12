<template>
    <div>
        <CommonTable :columns="userListColumns"
                     :dataList="dataList"
                     :labelField="'title'"
                     :rowKey="(row) => row.userId"
                     :actionColData="actionCol"
                     :actionWidth="310"
                     :pagination="pagination"
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
import {usePagination} from "@/hooks/usePagination";
import {SelectGroupOption, SelectOption} from "naive-ui";
import {useStatusDict} from "@/dictionary/useSystemDict";
import UserEditForm from "@/views/pages/system/user/userEditForm.vue";
import UserQueryForm from "@/views/pages/system/user/userQueryForm.vue";

export default defineComponent({
    components: {
        UserEditForm, CommonTable, UserQueryForm
    },
    setup() {
        const userEditFormRef = ref(null)
        const userId = ref<number | undefined>(undefined)

        // 状态下拉配置项
        const options = ref<Array<SelectOption | SelectGroupOption>>(useStatusDict)
        // 表格数据
        const dataList = ref<Array<any>>([])
        // 总条数
        const total = ref<number>(0)
        // 操作列
        const actionCol = ref<Array<any>>([
            {
                title: "重置密码",
                colorType: "success",
                actionType: 4,
            }
        ])
        // 分页对象
        const {pagination} = usePagination()

        // todo 这里会和查询表单冲突！！！
        watch(() => pagination, () => getUserList(), {deep: true})
        watch(() => {
            // @ts-ignore
            userEditFormRef?.value?.modalLoading
        }, () => getUserList(), {deep: true})

        const onPageChange = (page: number) => {
            pagination.page = page
        }
        const onPageSizeChange = (pageSize: number) => {
            pagination.pageSize = pageSize
        }

        const onEvent = (value: any) => {
            console.log('表格事件的回调信息：', value)
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

        const getUserList = (data: any = {}) => {
            userController.getUserList(data, {
                page: pagination.page,
                pageSize: pagination.pageSize
            }).then((res) => {
                dataList.value = res.data.list
                pagination.itemCount = res.data.count
            })
        }

        const onQuery = (value: any) => {
            getUserList(value)
        }

        const onReset = (value: any) => {
            getUserList(value)
        }

        onMounted(() => {
            getUserList()
        })

        return {
            userListColumns,
            dataList,
            total,
            actionCol,
            pagination,
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
