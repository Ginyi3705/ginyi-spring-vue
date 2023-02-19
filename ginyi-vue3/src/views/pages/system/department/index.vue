<template>
    <div>
        <CommonTable :columns="columns"
                     :pagination="tablePagination"
                     :loading="tableLoading"
                     :dataList="tableDataList"
                     :labelField="'title'"
                     :actionWidth="220"
                     :rowKey="(row) => row.deptId"
                     :scrollX="1000"
                     :actionColData="actionCol"
                     @onPageChange="onPageChange"
                     @onPageSizeChange="onPageSizeChange"
                     @onEvent="onEvent">
            <template #query>
                <DeptQueryForm @onQuery="onQuery" @onReset="onReset"/>
            </template>
        </CommonTable>
        <DeptEditForm ref="deptEditFormRef"/>
    </div>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref, watch} from "vue";
import {useCommonTable} from "@/components/commonTable/useCommonTable";
import {deptController} from "@/api";
import {columns} from "@/views/pages/system/department/columns";
import CommonTable from "@/components/commonTable/index.vue";
import DeptQueryForm from "@/views/pages/system/department/deptQueryForm.vue";
import DeptEditForm from "@/views/pages/system/department/deptEditForm.vue";

export default defineComponent({
    components: {
        DeptQueryForm, CommonTable, DeptEditForm
    },
    setup() {
        const {
            tableDataList,
            tablePagination,
            tableLoading,
            onPageChange,
            onPageSizeChange,
            getDataList
        } = useCommonTable(deptController.list)
        const deptEditFormRef = ref(null)
        // 操作列
        const actionCol = ref<Array<any>>([
            {
                title: "新增子项",
                colorType: "success",
                actionType: 4,
            }
        ])

        // @ts-ignore
        watch(() => deptEditFormRef?.value?.modalLoading, () => getDataList(), {deep: true})

        const onEvent = (value: any) => {
            switch (value.type) {
                // 新增
                case 0:
                    // @ts-ignore
                    deptEditFormRef?.value?.onAdd()
                    break
                // 编辑
                case 1:
                    // @ts-ignore
                    deptEditFormRef?.value?.onEdit(value.row)
                    break
                // 删除
                case 2:
                    // @ts-ignore
                    deptEditFormRef?.value?.onDeleteById(value.row.deptId)
                    break
                // 批量删除
                case 3:
                    // @ts-ignore
                    deptEditFormRef?.value?.onDeleteByIds(value.data)
                    break
                // 新增子项
                case 4:
                    // @ts-ignore
                    deptEditFormRef?.value?.addChildDept(value.row)
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
            columns,
            tableDataList,
            tablePagination,
            tableLoading,
            onPageChange,
            onPageSizeChange,
            onEvent,
            onQuery,
            onReset,
            deptEditFormRef,
            actionCol
        }
    }
})
</script>
