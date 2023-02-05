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
                     @onBatchDeleteEvent="onBatchDeleteEvent"
                     @onEvent="onEvent">
            <template #query>
                <CommonForm
                    :model="queryForm"
                    :inline="true"
                    :labelPlacement="'top'"
                    :submitButtonText="'查询'"
                    :cancelButtonText="'重置'"
                    @onSubmit="onSubmit"
                    @onReset="onReset">
                    <n-form-item label="用户名" path="userName">
                        <n-input v-model:value="queryForm.userName" placeholder="请输入用户名"/>
                    </n-form-item>
                    <n-form-item label="昵称" path="user.nickName">
                        <n-input v-model:value="queryForm.nickName" placeholder="请输入昵称"/>
                    </n-form-item>
                    <n-form-item label="状态" path="status">
                        <n-select v-model:value="queryForm.status" :options="options" style="width: 100px"/>
                    </n-form-item>
                    <n-form-item label="手机号码" path="phoneNumber">
                        <n-input v-model:value="queryForm.phoneNumber" placeholder="请输入手机号码"/>
                    </n-form-item>
                    <n-form-item label="起止时间" path="time">
                        <n-date-picker v-model:formatted-value="queryForm.time"
                                       clearable
                                       value-format="yyyy-MM-dd HH:mm:ss"
                                       update-value-on-close type="datetimerange" :actions="['confirm', 'clear']"/>
                    </n-form-item>
                </CommonForm>
            </template>
        </CommonTable>
    </div>
</template>

<script lang="ts">
import {defineComponent, onMounted, reactive, ref, watch} from "vue";
import CommonTable from "@/components/commonTable/index.vue"
import {userListColumns} from "@/views/pages/system/user/userListColumns";
import {userController} from "@/api";
import {usePagination} from "@/hooks/usePagination";
import CommonForm from "@/components/commonForm/index.vue";
import {useDeepClone} from "@/hooks/useObject";
import {SelectGroupOption, SelectOption} from "naive-ui";
import {useStatusDict} from "@/dictionary/useSystemDict";

export default defineComponent({
    components: {
        CommonTable, CommonForm
    },
    setup() {

        const queryForm = ref<any>({
            userName: null,
            nickName: null,
            email: null,
            time: null,
            phoneNumber: null,
            status: null,
        })

        const options = ref<Array<SelectOption | SelectGroupOption>>(useStatusDict)

        // 表格数据
        const dataList = ref<Array<any>>([])
        // 总条数
        const total = ref<number>(0)
        // 操作列
        const actionCol = ref<Array<any>>([
            {
                title: "分配角色",
                colorType: "info",
                actionType: 3,
            },
            {
                title: "重置密码",
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
            if(queryForm.value?.time && queryForm.value?.time?.length !== 0){
                queryForm.value.beginTime = queryForm.value.time[0]
                queryForm.value.endTime = queryForm.value.time[1]
            }
            userController.getUserList(queryForm.value, {page: pagination.page, pageSize: pagination.pageSize}).then((res) => {
                dataList.value = res.data.list
                pagination.itemCount = res.data.count
            })
        }

        const onReset = (value: any) => {
            queryForm.value = useDeepClone(value)
            getUserList()
        }
        const onSubmit = (result: boolean) => {
            getUserList()
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
            onEvent,
            queryForm,
            onReset,
            onSubmit,
            options
        }
    }
})
</script>
