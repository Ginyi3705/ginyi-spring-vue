<template>
    <n-card title="查询条件" :size="size" v-if="hasQuerySlot('query')" style="margin-bottom: 15px">
        <slot name="query"></slot>
    </n-card>
    <n-card>
        <div style="display: flex; justify-content: space-between; margin-bottom: 5px">
            <n-space>
                <n-button type="primary" :size="size" @click="onEvent(0)">
                    <template #icon>
                        <n-icon :component="AddCircleOutline"/>
                    </template>
                    新增数据
                </n-button>
                <n-button type="error" :size="size"
                          @click="onBatchDeleteEvent"
                          :disabled="!hasSelect || checkedRowList.length === 0">
                    <template #icon>
                        <n-icon :component="TrashBinOutline"/>
                    </template>
                    批量删除
                </n-button>
            </n-space>
            <div style="display: flex; align-items: center">
                <n-radio-group :size="size" v-model:value="tableSize">
                    <n-radio-button label="紧凑" value="small"/>
                    <n-radio-button label="默认" value="medium"/>
                    <n-radio-button label="宽松" value="large"/>
                </n-radio-group>
                <n-space style="margin-left: 10px; cursor: pointer">
                    <n-popover :show-arrow="false" placement="bottom-end" trigger="click">
                        <template #trigger>
                            <n-icon style=" display: flex; align-items: center" :size="20">
                                <SettingsOutline/>
                            </n-icon>
                        </template>
                        <div style="width: 350px; height: 400px;">
                            <n-divider title-placement="center">其他配置</n-divider>
                            <div style="display: flex; justify-content: space-between">
                                <div>
                                    <span style="margin-right: 10px">序号</span>
                                    <n-radio-group :size="size" v-model:value="hasIndex">
                                        <n-radio-button label="开启" :value="true"/>
                                        <n-radio-button label="隐藏" :value="false"/>
                                    </n-radio-group>
                                </div>
                                <div>
                                    <span style="margin-right: 10px">可选列</span>
                                    <n-radio-group :size="size" v-model:value="hasSelect">
                                        <n-radio-button label="开启" :value="true"/>
                                        <n-radio-button label="隐藏" :value="false"/>
                                    </n-radio-group>
                                </div>
                            </div>
                            <n-divider title-placement="center">列的排序</n-divider>
                            <n-scrollbar style="max-height: 240px">
                                <div>
                                    <n-checkbox-group v-model:value="checkedList" @update:value="handleChecked">
                                        <Draggable v-model="tableConfigColumns" group="people"
                                                   @start="drag = true" @end="onDragEnd"
                                                   item-key="id"
                                                   animation="300"
                                                   chosen-class="chosenClass">
                                            <template #item="{element}">
                                                <div class="item" :style="{borderColor: themeColor}">
                                                    <n-space item-style="display: flex;" align="center">
                                                        <n-icon :component="Move"></n-icon>
                                                        <n-checkbox :value="element[labelField]"
                                                                    :label="[undefined, null, ''].includes(element[labelField])
                                                                ? '可选列' : element[labelField]"/>
                                                    </n-space>
                                                </div>
                                            </template>
                                        </Draggable>
                                    </n-checkbox-group>
                                </div>
                            </n-scrollbar>
                        </div>
                    </n-popover>
                </n-space>
            </div>
        </div>
        <n-data-table :remote="true"
                      :columns="tableColumns"
                      :data="dataList"
                      :size="tableSize"
                      :max-height="(clientHeight - (layoutHeaderHeight + layoutFooterHeight)) / 2"
                      :pagination="pagination"
                      :row-key="rowKey"
                      :scroll-x="2000"
                      :loading="dataList.length === 0"
                      @update:page="handlePageChange"
                      @update:page-size="handlePageSizeChange"
                      @update:checked-row-keys="handleCheckRows"/>
    </n-card>
</template>

<script lang="ts">
import {defineComponent, h, onMounted, reactive, ref, toRefs, watch} from "vue";
import {storeToRefs} from "pinia";
import {useSystemStore} from "@/store/modules/useSystemStore";
import {AddCircleOutline, Move, SettingsOutline, TrashBinOutline} from "@vicons/ionicons5";
import {DataTableRowKey, NButton, NSpace} from "naive-ui";
import Draggable from 'vuedraggable'
import {useDeepClone} from "@/hooks/useObject";
import {definedProps} from "@/components/table/props";
import {Size} from "naive-ui/es/button/src/interface";

export default defineComponent({
    name: "CommonTable",
    emits: ["onBatchDeleteEvent", "onPageChange", "onPageSizeChange", "onEvent"],
    props: {
        ...definedProps
    },
    components: {
        SettingsOutline, Draggable, Move
    },
    setup(props, context) {
        // 可选列
        const selectCol = ref<Array<any>>([{
            type: 'selection'
        }])
        // 操作列
        const actionCol = ref<Array<any>>([
            {
                title: "编辑",
                colorType: "primary",
                actionType: 1,
            },
            {
                title: "删除",
                colorType: "error",
                actionType: 2,
            }
        ])
        // 操作列添加完毕标志
        const actionColFlag = ref<boolean>(false)
        // draggable 配置项
        const dragData = reactive({
            drag: false,
        })
        const {layoutHeaderHeight, clientHeight, layoutFooterHeight, themeColor} = storeToRefs((useSystemStore()));
        // 表格的尺寸
        const tableSize = ref<string>("medium")
        // 是否开启序号列
        const hasIndex = ref<boolean>(false)
        // 是否开启可选列
        const hasSelect = ref<boolean>(false)
        // 表格的列
        const tableColumns = ref<Array<any>>([])
        // 更多设置中的表格的列
        const tableConfigColumns = ref<Array<any>>([])
        // 更多设置中的单选框选中的
        const checkedList = ref<Array<string>>([])
        // 表格中启用可选列后选中的
        const checkedRowList = ref<Array<any>>([])

        watch(() => hasSelect.value, () => {
            concatActionCol()
        })

        // 拼接操作列
        const concatActionCol = () => {
            if (hasSelect.value) {
                tableColumns.value = selectCol.value.concat(
                    tableConfigColumns.value.filter(column => {
                        return checkedList.value.includes(column[props.labelField as string])
                    })
                )
            } else if (checkedList.value.length < tableConfigColumns.value.length) {
                tableColumns.value = tableConfigColumns.value.filter(column => {
                    return checkedList.value.includes(column[props.labelField as string])
                })
            } else {
                tableColumns.value = props.columns as Array<any>
            }
            tableColumns.value = [...tableColumns.value, ...renderActionCol()]
        }

        // 渲染操作列
        const renderActionCol = () => {
            return [{
                title: "操作",
                key: "action",
                fixed: "right",
                width: props.actionWidth,
                render: (row: any) => {
                    return h(NSpace, null, () => [
                            [...actionCol.value, ...props.actionColData].map((action) => {
                                return h(NButton, {
                                        size: props.size as Size,
                                        type: action.colorType,
                                        onClick: () => onEvent(action.actionType, row)
                                    },
                                    {default: () => action.title}
                                )
                            })
                        ]
                    )
                }
            }]
        }

        // 检查插槽是否有内容
        const hasQuerySlot = (name: string) => !!context.slots[name];
        // 勾选的行
        const handleCheckRows = (rowKeys: DataTableRowKey[]) => {
            checkedRowList.value = rowKeys
        }
        // 更多设置里边的复选框选中事件
        const handleChecked = (values: Array<any>) => {
            // 返回勾选的列，重新赋值给 tableColumns (回显)
            tableColumns.value = tableConfigColumns.value.filter(column => {
                return values.includes(column[props.labelField as string])
            })
            tableColumns.value = [...tableColumns.value, ...renderActionCol()]
            // 添加操作列
            if (hasSelect.value) {
                tableColumns.value.unshift(...selectCol.value)
            }
        }

        const onDragEnd = () => {
            dragData.drag = false
            // 将排序后的 columns 重新赋值给 tableColumns (回显)
            tableColumns.value = [...useDeepClone(
                tableConfigColumns.value.filter(column => {
                    return checkedList.value.includes(column[props.labelField as string])
                })
            ), ...renderActionCol()]
            if (hasSelect.value) {
                tableColumns.value.unshift(...selectCol.value)
            }
        }

        // 批量删除
        const onBatchDeleteEvent = () => {
            context.emit("onBatchDeleteEvent", checkedRowList.value)
        }

        // 页码改变事件
        const handlePageChange = (page: number) => {
            context.emit("onPageChange", page)
        }
        // 每页数据大小改变事件
        const handlePageSizeChange = (pageSize: number) => {
            context.emit("onPageSizeChange", pageSize)
        }

        /**
         * @param type 0新增、1编辑、2删除、其他为自定义传入的值
         * @param row 操作的行
         */
        const onEvent = (type: number, row?: any) => {
            context.emit("onEvent", {
                type: type,
                row: row
            })
        }

        onMounted(() => {
            concatActionCol()
            checkedList.value = tableColumns.value.map(item => item[props.labelField as string])
            tableConfigColumns.value = useDeepClone(tableColumns.value.filter(column => {
                return column.key !== "action"
            }))
        })

        return {
            tableColumns,
            SettingsOutline,
            Move, AddCircleOutline, TrashBinOutline,
            hasQuerySlot,
            tableSize,
            hasIndex,
            hasSelect,
            handleCheckRows,
            ...toRefs(dragData),
            handleChecked,
            checkedList,
            tableConfigColumns,
            checkedRowList,
            onBatchDeleteEvent,
            handlePageChange,
            handlePageSizeChange,
            layoutHeaderHeight, clientHeight, layoutFooterHeight,
            onDragEnd,
            themeColor,
            onEvent
        }
    }
})
</script>

<style scoped lang="less">
.chosenClass {
    opacity: 1;
}

.item {
    height: 40px;
    line-height: 40px;
    border-radius: 4px;
    margin: 5px 0;
    padding: 0 5px;
    border: 1px solid;
    display: flex;
    align-items: center;
}

.item:hover {
    cursor: move;
}
</style>