<template>
    <n-card title="查询条件" :size="size" v-if="hasQuerySlot('query')" style="margin-bottom: 10px">
        <slot name="query"></slot>
    </n-card>
    <n-card>
        <div style="display: flex; justify-content: space-between; margin-bottom: 5px">
            <n-space>
                <n-button type="primary" :size="size">
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
                                                <div class="item">
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
        <n-data-table ref="tableRef"
                      :remote="true"
                      :columns="tableColumns"
                      :data="dataList"
                      :size="tableSize"
                      :max-height="clientHeight - (layoutHeaderHeight + layoutFooterHeight) - 200"
                      :pagination="pagination"
                      @update:page="handlePageChange"
                      @update:page-size="handlePageSizeChange"
                      @update:checked-row-keys="handleCheckRows"/>
    </n-card>
</template>

<script lang="ts">
import {defineComponent, onMounted, reactive, ref, toRefs, watchEffect} from "vue";
import {storeToRefs} from "pinia";
import {useSystemStore} from "@/store/modules/useSystemStore";
import {AddCircleOutline, Move, SettingsOutline, TrashBinOutline} from "@vicons/ionicons5";
import {DataTableRowKey} from "naive-ui";
import Draggable from 'vuedraggable'
import {useDeepClone} from "@/hooks/useObject";

export default defineComponent({
    name: "CommonTable",
    emits: ["onBatchDeleteEvent", "onPageChange", "onPageSizeChange"],
    props: {
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
        // /**
        //  * 每一行数据都要有唯一的 key
        //  */
        // rowKey: {
        //     require: true,
        //     type: String
        // },
    },
    components: {
        SettingsOutline, Draggable, Move
    },
    setup(props, context) {
        // 可选列
        const selectCol = ref<Array<any>>([{
            type: 'selection'
        }])
        // draggable 配置项
        const dragData = reactive({
            drag: false,
        })
        // 分页对象
        const paginationReactive = reactive({
            page: 1,
            pageSize: props.pageSizes[0],
            pageSizes: props.pageSizes,
            pageCount: props.dataList?.length,
            itemCount: props.dataList?.length,
            size: "medium",
            showQuickJumper: true,
            showSizePicker: true
        })
        const {layoutHeaderHeight, clientHeight, layoutFooterHeight} = storeToRefs((useSystemStore()));
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

        watchEffect(() => {
            tableColumns.value = hasSelect.value ? selectCol.value.concat(
                tableConfigColumns.value.filter(column => {
                    return checkedList.value.includes(column[props.labelField as string])
                })
            ) : checkedList.value.length < tableConfigColumns.value.length ? tableConfigColumns.value.filter(column => {
                return checkedList.value.includes(column[props.labelField as string])
            }) : props.columns as Array<any>
        })

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
            if (hasSelect.value) {
                tableColumns.value.unshift(...selectCol.value)
            }
        }

        const onDragEnd = () => {
            dragData.drag = false
            // 将排序后的 columns 重新赋值给 tableColumns (回显)
            tableColumns.value = useDeepClone(tableConfigColumns.value)
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

        onMounted(() => {
            checkedList.value = tableColumns.value.map(item => item[props.labelField as string])
            tableConfigColumns.value = useDeepClone(tableColumns.value)
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
            pagination: paginationReactive,
            onDragEnd
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
    border: 1px solid antiquewhite;
    display: flex;
    align-items: center;
}

.item:hover {
    cursor: move;
}
</style>