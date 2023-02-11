<template>
    <n-form
        :model="userInfoForm"
        :inline="false"
        :rules="rules">
        <n-grid :cols="24" :x-gap="24">
            <n-form-item-gi :span="12" label="用户账号" path="userName">
                <n-input v-model:value="userInfoForm.userName" clearable placeholder="请输入用户账号"/>
            </n-form-item-gi>
            <n-form-item-gi :span="12" label="用户昵称" path="nickName">
                <n-input v-model:value="userInfoForm.nickName" clearable placeholder="请输入用户昵称"/>
            </n-form-item-gi>
            <n-form-item-gi :span="12" label="手机号码" path="phoneNumber">
                <n-input v-model:value="userInfoForm.phoneNumber" clearable placeholder="请输入手机号码"/>
            </n-form-item-gi>
            <n-form-item-gi :span="12" label="头像" path="avatar">
                <n-input v-model:value="userInfoForm.avatar" clearable placeholder="请上传头像"/>
            </n-form-item-gi>
            <n-form-item-gi :span="12" label="邮箱" path="email">
                <n-input v-model:value="userInfoForm.email" clearable placeholder="请输入邮箱"/>
            </n-form-item-gi>
            <n-form-item-gi :span="12" label="岗位" path="postIds">
                <n-select v-model:value="userInfoForm.postIds"
                          label-field="postName"
                          value-field="postId"
                          clearable
                          filterable
                          :multiple="true"
                          :options="postSelectDropDict"/>
            </n-form-item-gi>
            <n-form-item-gi :span="12" label="归属部门" path="deptId">
                <n-tree-select label-field="deptName"
                               key-field="deptId"
                               v-model:value="userInfoForm.deptId"
                               :default-expand-all="true"
                               :options="deptSelectDropDict"/>
            </n-form-item-gi>
            <n-form-item-gi :span="12" label="性别" path="sex">
                <n-radio-group v-model:value="userInfoForm.sex" size="small" name="radioGroup1">
                    <n-radio-button v-for="(item, index) in sexSelectDropDict" :key="index" :value="item.value">
                        {{ item.label }}
                    </n-radio-button>
                </n-radio-group>
            </n-form-item-gi>
            <n-form-item-gi :span="12" label="用户角色" path="roleIds">
                <n-select v-model:value="userInfoForm.roleIds"
                          label-field="roleName"
                          value-field="roleId"
                          clearable
                          filterable
                          :multiple="true"
                          :options="roleSelectDropDict"/>
            </n-form-item-gi>
            <n-form-item-gi :span="12" label="状态" path="status">
                <n-radio-group v-model:value="userInfoForm.status" name="radioGroup2" size="small">
                    <n-radio-button v-for="(item, index) in statusSelectDropDict" :key="index" :value="item.value">
                        {{ item.label }}
                    </n-radio-button>
                </n-radio-group>
            </n-form-item-gi>
            <n-form-item-gi :span="24" label="备注" path="remark">
                <n-input v-model:value="userInfoForm.remark" clearable type="textarea" placeholder="请输入备注"/>
            </n-form-item-gi>
        </n-grid>
    </n-form>
</template>

<script lang="ts">
import {defineComponent, onMounted, onUnmounted, ref, toRaw} from "vue";
import {useSexDict, useStatusDict} from "@/dictionary/useSystemDict";
import {SelectOption, TreeSelectOption} from "naive-ui";
import {deptController, postController, roleController, userController} from "@/api";
import {useRemoveEmptyChildrenField} from "@/hooks/useTree";

export default defineComponent({
    name: "userEditForm",
    props: {
        userId: {
            type: Number,
            require: true
        },
        isEdit: {
            type: Boolean,
            default: false
        }
    },
    setup(props) {
        const userInfoForm = ref({
            avatar: undefined,
            deptId: undefined,
            email: undefined,
            nickName: undefined,
            phoneNumber: undefined,
            postIds: [],
            remark: undefined,
            roleIds: [],
            sex: 0,
            status: 0,
            userName: undefined
        })
        /**
         * 表单默认值，用于重置表单数据
         */
        const defaultValue = toRaw(userInfoForm.value)

        const rules = {
            deptId: {type: "number", required: true, message: "请选择归属部门", trigger: ['blur', 'change']},
            status: {type: "string", required: true, message: "请选择状态", trigger: ["blur", "change"]},
            postIds: {type: "array", required: true, message: "请选择岗位", trigger: ["blur", "change"]},
            roleIds: {type: "array", required: true, message: "请选择角色", trigger: ["blur", "change"]},
            userName: {type: "string", required: true, message: "请输入用户名", trigger: ["input", "blur"]},
            nickName: {type: "string", required: true, message: "请输入用户昵称", trigger: ["input", "blur"]},
        }

        const sexSelectDropDict = ref<Array<any>>(useSexDict)
        const statusSelectDropDict = ref<Array<any>>(useStatusDict)
        const deptSelectDropDict = ref<TreeSelectOption | null | Array<TreeSelectOption | null>>([])
        const postSelectDropDict = ref<Array<SelectOption>>([])
        const roleSelectDropDict = ref<Array<SelectOption>>([])

        const getDeptList = () => {
            deptController.list({}).then(res => {
                deptSelectDropDict.value = useRemoveEmptyChildrenField(res.data.list)
            })
        }
        const getPostList = () => {
            postController.list({}).then(res => {
                postSelectDropDict.value = res.data.list
            })
        }
        const getRoleList = () => {
            roleController.list({}).then(res => {
                roleSelectDropDict.value = res.data.list
            })
        }
        const getUserById = (userId: number) => {
            if (props.isEdit) {
                userController.getUserById(userId).then(res => {
                    userInfoForm.value = res.data
                })
            }
        }

        const onSubmit = () => {
            console.log('===提交===')
        }

        onMounted(() => {
            getDeptList()
            getPostList()
            getRoleList()
            getUserById(props.userId as number)
        })

        onUnmounted(() => {
            userInfoForm.value = defaultValue
        })

        return {
            userInfoForm,
            rules,
            sexSelectDropDict,
            statusSelectDropDict,
            deptSelectDropDict,
            postSelectDropDict,
            roleSelectDropDict,
        }
    }

})
</script>

<style scoped>

</style>