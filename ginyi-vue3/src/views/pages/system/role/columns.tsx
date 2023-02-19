import {DataTableColumns, NTag} from "naive-ui";
import {useCommonColumns} from "@/hooks/useCommonColums";

const {useRenderStateById} = useCommonColumns()

export const columns: DataTableColumns<any> = [
    {
        title: "角色名称",
        key: "roleName"
    },
    {
        title: "角色权限字符串",
        key: "roleKey"
    },
    {
        title: "排序",
        key: "sort",
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
        title: "创建时间",
        key: "createTime",
        width: 200
    }
]