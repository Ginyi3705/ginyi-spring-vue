<template>
    <n-modal
        v-model:show="showModal"
        :preset="preset"
        :title="title"
        positiveText="提交"
        negativeText="取消"
        @positive-click="onSubmit"
        @negative-click="onCancel">
        <div style="padding-top: 15px">
            <slot></slot>
        </div>
    </n-modal>
</template>

<script lang="ts">
import {defineComponent, ref, watch} from "vue";
import {definedProps} from "@/components/commonModel/props";

export default defineComponent({
    name: "CommonModel",
    props: {
        ...definedProps
    },
    emits: ["onSubmit", "onCancel"],
    setup(props, context) {
        const showModal = ref<boolean>(false)

        const onOpen = () => {
            showModal.value = true
        }
        const onSubmit = () => {
            window.$message.success('onSubmit')
        }
        const onCancel = () => {
            window.$message.success('onCancel')
        }

        return {
            onSubmit,
            onCancel,
            onOpen,
            showModal,
        }
    }
})
</script>

<style scoped>

</style>