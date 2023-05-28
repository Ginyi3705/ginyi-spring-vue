<template>
    <div>
        <CommonTable :columns="columns"
                     :pagination="tablePagination"
                     :loading="tableLoading"
                     :dataList="tableDataList"
                     :labelField="'title'"
                     :actionWidth="140"
                     :rowKey="(row) => row.roleId"
                     :scrollX="1000"
                     @onPageChange="onPageChange"
                     @onPageSizeChange="onPageSizeChange"
                     @onEvent="onEvent">>
            <template #query>
                <RoleQueryForm @onQuery="onQuery" @onReset="onReset"/>
            </template>
        </CommonTable>
        <RoleEditForm ref="roleEditFormRef"/>
    </div>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref, watch} from "vue";
import CommonTable from "@/components/commonTable/index.vue";
import {columns} from "@/views/pages/system/role/columns";
import {useCommonTable} from "@/components/commonTable/useCommonTable";
import {roleController, userController} from "@/api";
import RoleQueryForm from "@/views/pages/system/role/roleQueryForm.vue";
import RoleEditForm from "@/views/pages/system/role/roleEditForm.vue";
import {eventBus} from "@/config/eventBus";

export default defineComponent({
    components: {
        CommonTable, RoleQueryForm, RoleEditForm
    },
    setup() {
        const {
            tableDataList,
            tablePagination,
            tableLoading,
            onPageChange,
            onPageSizeChange,
            getDataList
        } = useCommonTable(roleController.list)
        const roleEditFormRef = ref(null)

        // @ts-ignore
        watch(() => roleEditFormRef?.value?.modalLoading, () => getDataList(), {deep: true})


        const onEvent = (value: any) => {
            switch (value.type) {
                // 新增
                case 0:
                    // @ts-ignore
                    roleEditFormRef?.value?.onAdd()
                    break
                // 编辑
                case 1:
                    // @ts-ignore
                    roleEditFormRef?.value?.onEdit(value.row, value.row.roleId)
                    break
                // 删除
                case 2:
                    // @ts-ignore
                    roleEditFormRef?.value?.onDeleteById(value.row.roleId)
                    break
                // 批量删除
                case 3:
                    // @ts-ignore
                    roleEditFormRef?.value?.onDeleteByIds(value.data)
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
            eventBus.on("handleRoleStatusSwitchClick", (row: any) => {
                window.$message.warning("暂时没用提供单独更新的接口，不过可以使用【操作列 - 更新】按钮进行更新数据！")
            })
        })

        return {
            columns,
            tableDataList,
            tablePagination,
            tableLoading,
            onPageChange,
            onPageSizeChange,
            onEvent,
            onQuery,
            onReset,
            roleEditFormRef
        }
    }
})

</script>
