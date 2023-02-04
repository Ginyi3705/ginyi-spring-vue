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
                <CommonForm
                    :model="formValue"
                    :rules="rules"
                    :inline="true"
                    :submitButtonText="'查询'"
                    :cancelButtonText="'重置'"
                    @onSubmit="onSubmit"
                    @onReset="onReset">
                    <n-form-item label="姓名" path="user.name">
                        <n-input v-model:value="formValue.user.name" placeholder="输入姓名"/>
                    </n-form-item>
                    <n-form-item label="年龄" path="user.age">
                        <n-input v-model:value="formValue.user.age" placeholder="输入年龄"/>
                    </n-form-item>
                    <n-form-item label="电话号码" path="phone">
                        <n-input v-model:value="formValue.phone" placeholder="电话号码"/>
                    </n-form-item>
                </CommonForm>
            </template>
        </CommonTable>
    </div>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref, watch} from "vue";
import CommonTable from "@/components/commonTable/index.vue"
import {userListColumns} from "@/views/pages/system/user/userListColumns";
import {userController} from "@/api";
import {usePagination} from "@/hooks/usePagination";
import CommonForm from "@/components/commonForm/index.vue";
import {useDeepClone} from "@/hooks/useObject";

export default defineComponent({
    components: {
        CommonTable, CommonForm
    },
    setup() {

        const formValue = ref({
            user: {
                name: '',
                age: '18'
            },
            phone: ''
        })
        const rules = ref({
            user: {
                name: {
                    required: true,
                    message: '请输入姓名',
                    trigger: 'blur'
                },
                age: {
                    required: true,
                    message: '请输入年龄',
                    trigger: ['input', 'blur']
                }
            },
            phone: {
                required: true,
                message: '请输入电话号码',
                trigger: ['input']
            }
        })

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

        const onReset = (value: any) => {
            formValue.value = useDeepClone(value)
        }
        const onSubmit = (result: boolean) => {
            window.$message.warning(`校验结果-->>${result}`)
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
            formValue,
            rules,
            onReset,
            onSubmit
        }
    }
})
</script>
