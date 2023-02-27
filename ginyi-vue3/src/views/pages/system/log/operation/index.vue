<template>
    <div>
        <CommonTable :columns="columns"
                     :pagination="tablePagination"
                     :loading="tableLoading"
                     :dataList="tableDataList"
                     :labelField="'title'"
                     :actionWidth="140"
                     :actionColData="actionCol"
                     :rowKey="(row) => row.id"
                     :scrollX="1200"
                     :buttonConfig="buttonConfig"
                     @onPageChange="onPageChange"
                     @onPageSizeChange="onPageSizeChange"
                     @onEvent="onEvent">
        </CommonTable>
        <n-drawer v-model:show="show" width="50%">
            <n-drawer-content title="数据详情" closable>
                <MonacoEditor v-model="rowData"
                              :language="language"
                              width="100%"
                              height="100%"
                              @editorMounted="editorMounted"/>
            </n-drawer-content>
        </n-drawer>
    </div>
</template>

<script lang="ts">
import {defineComponent, onMounted, reactive, ref} from "vue";
import CommonTable from "@/components/commonTable/index.vue";
import {useCommonTable} from "@/components/commonTable/useCommonTable";
import {logController} from "@/api";
import {IButtonConfig, IButtonType} from "@/interface/modules/system";
import {columns} from "@/views/pages/system/log/operation/columns";
import MonacoEditor from "@/components/monacoEditor/index.vue";
import * as monaco from "monaco-editor";

export default defineComponent({
    name: "LogLogin",
    components: {
        CommonTable, MonacoEditor
    },
    setup() {
        const show = ref<boolean>(false)
        const rowData = ref<any>(undefined)
        const language = ref<any>("json")
        const editorMounted = (editor: monaco.editor.IStandaloneCodeEditor) => {
        }
        const {
            tableDataList,
            tablePagination,
            tableLoading,
            onPageChange,
            onPageSizeChange,
            getDataList
        } = useCommonTable(logController.getOperationLogList)
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
                show: false,
                colorType: "error",
            },
            batchDeleteButton: {
                type: "batchDelete",
                colorType: "error",
                actionType: 3,
                show: false,
                title: "批量强制退出",
            }
        })
        // 操作列
        const actionCol = ref<Array<IButtonConfig>>([
            {
                type: "details",
                title: "查看详情",
                colorType: "primary",
                show: true,
                actionType: 4,
            }
        ])

        const onEvent = (value: any) => {
            show.value = true
            value.row.operationParam = JSON.parse(value.row.operationParam ?? "{}")
            value.row.jsonResult = JSON.parse(value.row.jsonResult ?? "{}")
            rowData.value = JSON.stringify(value.row, null, "\t")
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
            buttonConfig,
            actionCol,
            onEvent,
            show,
            rowData,
            language,
            editorMounted
        }
    }
})

</script>

<style>
.n-drawer .n-drawer-content .n-drawer-body-content-wrapper {
    box-sizing: border-box;
    padding: 0;
}
</style>
