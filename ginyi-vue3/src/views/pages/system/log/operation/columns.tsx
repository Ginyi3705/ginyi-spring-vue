import {DataTableColumns, NTag} from "naive-ui";
import {useCommonColumns} from "@/hooks/useCommonColums";

const {useRenderSuccessById} = useCommonColumns()


export const columns: DataTableColumns<any> = [
    {
        title: "操作人员",
        key: "operationName",
    },
    {
        title: "操作地址",
        key: "operationIp"
    },
    {
        title: "操作地点",
        key: "operationLocation",
    },
    {
        title: "系统模块",
        key: "title",
    },
    {
        title: "操作类型",
        key: "businessType",
    },
    {
        title: "请求方式",
        key: "requestMethod",
    },
    {
        title: "操作状态",
        key: "status",
        render: (row) => (
            <NTag type={row.status.toString() === "0" ? "success" : "error"}>
                {useRenderSuccessById(row.status)}
            </NTag>
        )
    },
    {
        title: "操作时间",
        key: "createTime",
        width: 200
    }
]