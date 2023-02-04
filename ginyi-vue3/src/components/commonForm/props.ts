export const definedProps = {
    /**
     * 标签宽度
     */
    labelWidth: {
        type: Number,
        default: 80
    },
    /**
     * 表单尺寸大小
     */
    size: {
        type: String,
        default: "small"
    },
    /**
     * 表单数据模型
     */
    model: {
        type: Object,
        default: () => {}
    },
    /**
     * 表单规则对象
     */
    rules: {
        type: Object,
        default: () => {}
    },
    /**
     * 是否展示为行内表单
     */
    inline: {
        type: Boolean,
        default: false
    },
    labelPlacement: {
        type: String,
        default: "top"
    },
    /**
     * 确认按钮的文本内容
     */
    submitButtonText: {
        type: String,
        default: "提交"
    },
    /**
     * 取消按钮的文本内容
     */
    cancelButtonText: {
        type: String,
        default: "重置"
    }
}