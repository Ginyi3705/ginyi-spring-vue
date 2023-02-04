import {IColumnType} from "@/interface/modules/system";

export const userListColumns: Array<IColumnType> = [
    {
        title: "用户昵称",
        key: "nickName"
    },
    {
        title: "用户账号",
        key: "userName"
    },
    {
        title: "邮箱",
        key: "email",
    },
    {
        title: "部门ID",
        key: "deptId"
    },
    {
        title: "头像",
        key: "avatar"
    },
    {
        title: "手机号码",
        key: "phoneNumber"
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
        title: "性别",
        key: "sex"
    },
    {
        title: "状态",
        key: "status"
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