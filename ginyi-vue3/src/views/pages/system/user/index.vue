<template>
    <div>
        <CommonTable :columns="columns"
                     :dataList="dataList"
                     :labelField="'title'"
                     :rowKey="(row) => row.id"
                     :pageSizes="[10, 20, 50, 100]"
                     :total="100"
                     :actionColData="actionCol"
                     :actionWidth="300"
                     @onPageChange="onPageChange"
                     @onPageSizeChange="onPageSizeChange"
                     @onBatchDeleteEvent="onBatchDeleteEvent"
                     @onEvent="onEvent">
            <template #query>
                <n-form
                    style="display: flex; flex-wrap: wrap"
                    ref="formRef"
                    inline
                    :label-width="80"
                    :size="'small'">
                    <n-form-item label="姓名" path="user.name">
                        <n-input placeholder="输入姓名"/>
                    </n-form-item>
                    <n-form-item label="年龄" path="user.age">
                        <n-input placeholder="输入年龄"/>
                    </n-form-item>
                    <n-form-item label="电话号码" path="phone">
                        <n-input placeholder="电话号码"/>
                    </n-form-item>
                    <n-form-item>
                        <n-button attr-type="submit" type="primary">
                            查询
                        </n-button>
                    </n-form-item>
                    <n-form-item>
                        <n-button attr-type="reset">
                            重置
                        </n-button>
                    </n-form-item>
                </n-form>
            </template>
        </CommonTable>
    </div>
</template>

<script lang="ts">
import {defineComponent, ref} from "vue";
import CommonTable from "@/components/table/index.vue"
import {DataTableColumns} from "naive-ui";
import {RowData} from "naive-ui/es/data-table/src/interface";

export default defineComponent({
    components: {
        CommonTable
    },
    setup() {
        const columns = ref<DataTableColumns<RowData>>([
            {
                title: 'ID',
                key: 'id'
            },
            {
                title: '姓名',
                key: 'name'
            },
            {
                title: '年龄',
                key: 'age'
            },
            {
                title: '地址',
                key: 'address'
            }
        ])
        const dataList = ref<Array<any>>([])
        // 操作列
        const actionCol = ref<Array<any>>([
            {
                title: "自定义1",
                colorType: "info",
                actionType: 3,
            },
            {
                title: "自定义2",
                colorType: "success",
                actionType: 4,
            }
        ])
        dataList.value = Array.from({length: 46}).map((_, index) => ({
            key: index,
            name: `Edward King ${index}`,
            age: 32,
            address: `London, Park Lane no. ${index}`,
            id: index
        }))

        const onBatchDeleteEvent = (value: Array<any>) => {
            window.$message.warning("你点击了批量删除，请到控制台查看选中的数据！")
            console.log('批量删除选中的数据', value)
        }

        const onPageChange = (page: number) => {
            window.$message.warning(`你点击了第 ${page} 页`)
        }
        const onPageSizeChange = (pageSize: number) => {
            window.$message.warning(`你选了了每页 ${pageSize} 条`)
        }
        const onEvent = (value: any) => {
            window.$message.warning(`你点击了 ${value.type === 0 ? '【新增数据】'
                : value.type === 1 ? '【编辑】'
                : value.type === 2 ? '【删除】' : '【自定义的】'}`)
            console.log('表格事件的回调信息：', value)
        }
        return {
            columns,
            dataList,
            actionCol,
            onBatchDeleteEvent,
            onPageChange,
            onPageSizeChange,
            onEvent
        }
    }
})
</script>
