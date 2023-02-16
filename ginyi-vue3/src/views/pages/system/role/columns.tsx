import {DataTableColumns, NTag} from "naive-ui";
import {useColumns} from "@/views/pages/system/role/useColumns";

const {useRenderStateById} = useColumns()

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
    }
]