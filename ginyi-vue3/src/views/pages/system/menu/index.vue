<template>
    <div>
        <CommonTable :columns="columns"
                     :pagination="tablePagination"
                     :loading="tableLoading"
                     :dataList="tableDataList"
                     :labelField="'title'"
                     :actionWidth="220"
                     :rowKey="(row) => row.menuId"
                     :scrollX="2000"
                     :actionColData="actionCol"
                     @onPageChange="onPageChange"
                     @onPageSizeChange="onPageSizeChange"
                     @onEvent="onEvent">
            <template #query>
                <MenuQueryForm @onQuery="onQuery" @onReset="onReset"/>
            </template>
        </CommonTable>
        <MenuEditForm ref="menuEditFormRef"/>
    </div>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref, watch} from "vue";
import CommonTable from "@/components/commonTable/index.vue";
import {useCommonTable} from "@/components/commonTable/useCommonTable";
import {menuController} from "@/api";
import {columns} from "@/views/pages/system/menu/columns";
import MenuQueryForm from "@/views/pages/system/menu/menuQueryForm.vue";
import MenuEditForm from "@/views/pages/system/menu/menuEditForm.vue";

export default defineComponent({
    components: {
        CommonTable, MenuQueryForm, MenuEditForm
    },
    setup() {
        const {
            tableDataList,
            tablePagination,
            tableLoading,
            onPageChange,
            onPageSizeChange,
            getDataList
        } = useCommonTable(menuController.list)
        const menuEditFormRef = ref(null)
        // 操作列
        const actionCol = ref<Array<any>>([
            {
                title: "新增子项",
                colorType: "success",
                actionType: 4,
            }
        ])

        // @ts-ignore
        watch(() => menuEditFormRef?.value?.modalLoading, () => getDataList(), {deep: true})

        const onEvent = (value: any) => {
            switch (value.type) {
                // 新增
                case 0:
                    // @ts-ignore
                    menuEditFormRef?.value?.onAdd()
                    break
                // 编辑
                case 1:
                    // @ts-ignore
                    menuEditFormRef?.value?.onEdit(value.row)
                    break
                // 删除
                case 2:
                    // @ts-ignore
                    menuEditFormRef?.value?.onDeleteById(value.row.menuId)
                    break
                // 批量删除
                case 3:
                    // @ts-ignore
                    menuEditFormRef?.value?.onDeleteByIds(value.data)
                    break
                // 新增子项
                case 4:
                    if(value.row.menuType === "F"){
                        window.$message.error("按钮无法新增子项！")
                        return
                    }
                    // @ts-ignore
                    menuEditFormRef?.value?.addChildMenu(value.row)
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
            actionCol,
            onEvent,
            onQuery,
            onReset,
            menuEditFormRef
        }
    }
})
</script>
