<template>
    <div>
        <CommonTable :columns="columns"
                     :pagination="tablePagination"
                     :loading="tableLoading"
                     :dataList="tableDataList"
                     :labelField="'title'"
                     :actionWidth="140"
                     :rowKey="(row) => row.token"
                     :scrollX="1200"
                     @onPageChange="onPageChange"
                     @onPageSizeChange="onPageSizeChange"
                     @onEvent="onEvent">>
        </CommonTable>
    </div>
</template>

<script lang="ts">
import {defineComponent, onMounted} from "vue";
import CommonTable from "@/components/commonTable/index.vue";
import {useCommonTable} from "@/components/commonTable/useCommonTable";
import {onlineController} from "@/api";
import {columns} from "@/views/pages/monitor/online/columns";

export default defineComponent({
    name: "OnlineUser",
    components: {CommonTable},
    setup() {
        const {
            tableDataList,
            tablePagination,
            tableLoading,
            onPageChange,
            onPageSizeChange,
            getDataList
        } = useCommonTable(onlineController.getOnlineUserList)

        const onEvent = (value: any) => {

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
            onEvent
        }
    }
})
</script>

<style scoped>

</style>