<template>
    <n-form
        ref="formRef"
        :inline="inline"
        :rules="rules"
        :model="model"
        :label-width="labelWidth"
        :size="size">
        <slot></slot>
        <n-form-item>
            <n-space>
                <n-button type="primary" attr-type="button" @click="handleValidate">{{ submitButtonText }}</n-button>
                <n-button attr-type="button" @click="handleReset">{{ cancelButtonText }}</n-button>
            </n-space>
        </n-form-item>
    </n-form>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from "vue";
import {definedProps} from "@/components/commonForm/props";
import {FormInst} from "naive-ui";
import {useDeepClone} from "@/hooks/useObject";


export default defineComponent({
    name: "CommonForm",
    emits: ["onReset", "onSubmit"],
    props: {
        ...definedProps
    },
    setup(props, context) {
        const formRef = ref<FormInst | null>(null)
        const defaultValue = ref<object | undefined>(undefined)

        const handleValidate = (e: MouseEvent) => {
            e.preventDefault()
            formRef.value?.validate((errors: any) => {
                if (errors) {
                    window.$message.error('校验失败')
                }
                context.emit("onSubmit", !errors)
            })
        }
        const handleReset = () => {
            formRef.value?.restoreValidation()
            context.emit("onReset", defaultValue.value)
        }

        onMounted(() => {
            if (defaultValue.value === undefined) {
                defaultValue.value = useDeepClone(props.model)
            }
        })
        return {
            handleValidate,
            formRef,
            handleReset
        }
    }
})
</script>

<style scoped>

</style>