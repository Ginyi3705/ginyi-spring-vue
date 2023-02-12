import {DataTableColumns, NTag} from "naive-ui";

export const userListColumns: DataTableColumns<any> = [
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
        key: "deptId"
    },
    {
        title: "岗位",
        key: "postIds"
    },
    {
        title: "角色",
        key: "roleIds"
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
                {row.status === "0" ? "正常" : "禁用"}
            </NTag>
        )
    },
    {
        title: "性别",
        key: "sex",
        render: (row) => (
            <span>{row.sex === "0" ? "男" : row.sex === "1" ? "女" : "未知"}</span>
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