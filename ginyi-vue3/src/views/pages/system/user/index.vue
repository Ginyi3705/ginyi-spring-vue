<template>
    <div>
        <CommonTable :columns="userListColumns"
                     :dataList="dataList"
                     :labelField="'title'"
                     :rowKey="(row) => row.userId"
                     :actionColData="actionCol"
                     :actionWidth="300"
                     :pagination="pagination"
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
import {defineComponent, onMounted, ref, watch} from "vue";
import CommonTable from "@/components/table/index.vue"
import {userListColumns} from "@/views/pages/system/user/userListColumns";
import {userController} from "@/api";
import {usePagination} from "@/hooks/usePagination";

export default defineComponent({
    components: {
        CommonTable
    },
    setup() {
        // 表格数据
        const dataList = ref<Array<any>>([])
        // 总条数
        const total = ref<number>(0)
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
        // 分页对象
        const {pagination} = usePagination()

        watch(() => pagination, () => getUserList(), {deep: true})

        const onBatchDeleteEvent = (value: Array<any>) => {
            window.$message.warning("你点击了批量删除，请到控制台查看选中的数据！")
            console.log('批量删除选中的数据', value)
        }

        const onPageChange = (page: number) => {
            pagination.page = page
        }
        const onPageSizeChange = (pageSize: number) => {
            pagination.pageSize = pageSize
        }
        const onEvent = (value: any) => {
            window.$message.warning(`你点击了 ${value.type === 0 ? '【新增数据】'
                : value.type === 1 ? '【编辑】'
                    : value.type === 2 ? '【删除】' : '【自定义的】'}`)
            console.log('表格事件的回调信息：', value)
        }

        const getUserList = () => {
            userController.getUserList({}, {page: pagination.page, pageSize: pagination.pageSize}).then((res) => {
                dataList.value = res.data.list
                pagination.itemCount = res.data.count
            })
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
            onBatchDeleteEvent,
            onPageChange,
            onPageSizeChange,
            onEvent
        }
    }
})
</script>
