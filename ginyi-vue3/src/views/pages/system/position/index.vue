<template>
    <div>
        <CommonTable :columns="columns"
                     :pagination="tablePagination"
                     :loading="tableLoading"
                     :dataList="tableDataList"
                     :labelField="'title'"
                     :actionWidth="140"
                     :rowKey="(row) => row.postId"
                     :scrollX="1000"
                     @onPageChange="onPageChange"
                     @onPageSizeChange="onPageSizeChange"
                     @onEvent="onEvent">
            <template #query>
                <PostQueryForm @onQuery="onQuery" @onReset="onReset"/>
            </template>
        </CommonTable>
        <PostEditForm ref="postEditFormRef"/>
    </div>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref, watch} from "vue";
import CommonTable from "@/components/commonTable/index.vue";
import {useCommonTable} from "@/components/commonTable/useCommonTable";
import {postController} from "@/api";
import {columns} from "@/views/pages/system/position/columns";
import PostQueryForm from "@/views/pages/system/position/postQueryForm.vue";
import PostEditForm from "@/views/pages/system/position/postEditForm.vue";
import {eventBus} from "@/config/eventBus";

export default defineComponent({
    components: {
        CommonTable, PostQueryForm, PostEditForm
    },
    setup() {
        const {
            tableDataList,
            tablePagination,
            tableLoading,
            onPageChange,
            onPageSizeChange,
            getDataList
        } = useCommonTable(postController.list)
        const postEditFormRef = ref(null)

        // @ts-ignore
        watch(() => postEditFormRef?.value?.modalLoading, () => getDataList(), {deep: true})


        const onEvent = (value: any) => {
            switch (value.type) {
                // 新增
                case 0:
                    // @ts-ignore
                    postEditFormRef?.value?.onAdd()
                    break
                // 编辑
                case 1:
                    // @ts-ignore
                    postEditFormRef?.value?.onEdit(value.row, value.row.roleId)
                    break
                // 删除
                case 2:
                    // @ts-ignore
                    postEditFormRef?.value?.onDeleteById(value.row.roleId)
                    break
                // 批量删除
                case 3:
                    // @ts-ignore
                    postEditFormRef?.value?.onDeleteByIds(value.data)
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
            eventBus.on("handlePostStatusSwitchClick", (row: any) => {
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
            postEditFormRef
        }
    }
})
</script>
