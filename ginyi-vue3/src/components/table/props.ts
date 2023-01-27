export const definedProps = {
    /**
     * 表格的列
     */
    columns: {
        require: true,
        type: Array<any>
    },
    /**
     * 表格的数据
     */
    dataList: {
        type: Array<any>
    },
    /**
     * 整个组件的尺寸
     */
    size: {
        type: String,
        default: "small"
    },
    /**
     * 表格的列显示中文的字段，如 {label: 年龄, value: age}，则传 label
     */
    labelField: {
        type: String
    },
    /**
     * 每页条数数组
     */
    pageSizes: {
        default: () => [5, 10, 20, 50, 100],
        type: Array<any>
    },
    /**
     * 每一行数据都要有唯一的 key
     */
    rowKey: {
        require: true,
        type: Function
    },
    /**
     * 分页总条数
     */
    total: {
        type: Number,
    },
    /**
     * 操作列宽度
     */
    actionWidth: {
        type: Number,
        default: 250
    },
    /**
     * 操作列的数据
     */
    actionColData: {
        default: () => [],
        type: Array<any>
    }
}