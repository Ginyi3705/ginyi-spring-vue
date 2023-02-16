import {DataTableColumns, NSpace, NTag} from "naive-ui";
import {useColumns} from "@/views/pages/system/user/useColumns";

const {
    useRenderSexById,
    useRenderStateById,
    useRenderDeptNameById,
    useRenderRoleNameByIds,
    useRenderPostNameByIds
} = useColumns()

export const columns: DataTableColumns<any> = [
    {
        title: "用户昵称",
        key: "nickName"
    },
    {
        title: "用户账号",
        key: "userName"
    },
    {
        title: "部门",
        key: "deptId",
        width: 300,
        ellipsis: {
            tooltip: true
        },
        render: (row) => (
            <span>{ useRenderDeptNameById(row.deptId)}</span>
        )
    },
    {
        title: "岗位",
        key: "postIds",
        width: 200,
        render: (row) => (
            <NSpace>
                {useRenderPostNameByIds(row.postIds)?.map(post => <NTag key={post}>{post}</NTag>)}
            </NSpace>
        )
    },
    {
        title: "角色",
        key: "roleIds",
        width: 200,
        ellipsis: {
            tooltip: true
        },
        render: (row) => (
            <NSpace>
                {useRenderRoleNameByIds(row.roleIds)?.map(role => <NTag key={role}>{role}</NTag>)}
            </NSpace>
        )
    },
    {
        title: "手机号码",
        key: "phoneNumber"
    },
    {
        title: "状态",
        key: "status",
        render: (row) => (
            <NTag type={row.status === "0" ? "success" : "error"}>
                {useRenderStateById(row.status)}
            </NTag>
        )
    },
    {
        title: "性别",
        key: "sex",
        render: (row) => (
            <span>{useRenderSexById(row.sex)}</span>
        )
    },
    {
        title: "创建时间",
        key: "createTime",
        width: 200
    },
    {
        title: "更新时间",
        key: "updateTime",
        width: 200
    }
]