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
                     :buttonConfig="buttonConfig"
                     :showActionCol="true"
                     @onPageChange="onPageChange"
                     @onPageSizeChange="onPageSizeChange"
                     @onEvent="onEvent">>
        </CommonTable>
    </div>
</template>

<script lang="ts">
import {defineComponent, onMounted, reactive} from "vue";
import CommonTable from "@/components/commonTable/index.vue";
import {useCommonTable} from "@/components/commonTable/useCommonTable";
import {onlineController} from "@/api";
import {columns} from "@/views/pages/monitor/online/columns";
import {IButtonType} from "@/interface/modules/system";

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
        const buttonConfig = reactive<IButtonType>({
            addButton: {
                type: "add",
                title: "新增数据",
                actionType: 0,
                show: false,
                colorType: "primary",
            },
            editButton: {
                type: "edit",
                title: "编辑",
                actionType: 1,
                show: false,
                colorType: "primary",
            },
            deleteButton: {
                type: "delete",
                title: "强制退出",
                actionType: 2,
                show: true,
                colorType: "error",
            },
            batchDeleteButton: {
                type: "batchDelete",
                colorType: "error",
                actionType: 3,
                show: true,
                title: "批量强制退出",
            }
        })

        const onEvent = (value: any) => {
            switch (value.type) {
                // 强制退出
                case 2:
                    console.log(value.row.token)
                    window.$dialog.warning({
                        title: "温馨提醒",
                        content: "是否确认强制该用户下线？",
                        positiveText: "确定",
                        negativeText: "取消",
                        onPositiveClick: () => {
                            onlineController.removeUser(value.row.token).then(res => {
                                window.$message.success(res.msg)
                                getDataList()
                            })
                        }
                    })
                    break
                // 批量强制退出
                case 3:
                    window.$dialog.warning({
                        title: "温馨提醒",
                        content: "是否确认强制将选中的用户下线？",
                        positiveText: "确定",
                        negativeText: "取消",
                        onPositiveClick: () => {
                            onlineController.batchRemoveUser(value.data).then(res => {
                                window.$message.success(res.msg)
                                getDataList()
                            })
                        }
                    })
                    break
            }
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
            buttonConfig
        }
    }
})
</script>

<style scoped>

</style>