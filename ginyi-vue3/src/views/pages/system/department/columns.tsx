import {DataTableColumns, NTag} from "naive-ui";
import {useCommonColumns} from "@/hooks/useCommonColums";

const {useRenderStateById} = useCommonColumns()

export const columns: DataTableColumns<any> = [
    {
        title: "部门名称",
        key: "deptName"
    },
    {
        title: "排序",
        key: "sort"
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