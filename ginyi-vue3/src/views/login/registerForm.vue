<template>
    <n-form label-placement="left" label-width="auto">
        <n-form-item-row label="用户名">
            <n-input placeholder="用户名" v-model:value="registerForm.username"
                     @keyup.enter.native="handleRegister" clearable>
                <template #prefix>
                    <n-icon :component="Person"/>
                </template>
            </n-input>
        </n-form-item-row>
        <n-form-item-row label="密码">
            <n-input v-model:value="registerForm.password"
                     type="password" @keyup.enter.native="handleRegister"
                     show-password-on="mousedown"
                     placeholder="密码" clearable>
                <template #prefix>
                    <n-icon :component="BagOutline"/>
                </template>
            </n-input>
        </n-form-item-row>
        <n-form-item-row label="重复密码">
            <n-input v-model:value="registerForm.password2"
                     type="password" @keyup.enter.native="handleRegister"
                     show-password-on="mousedown"
                     placeholder="重复密码" clearable>
                <template #prefix>
                    <n-icon :component="BagOutline"/>
                </template>
            </n-input>
        </n-form-item-row>
        <n-form-item-row label="验证码">
            <n-input-group>
                <n-input v-model:value="registerForm.code"
                         placeholder="验证码" @keyup.enter.native="handleRegister"
                         clearable>
                    <template #prefix>
                        <n-icon :component="QrCode"/>
                    </template>
                </n-input>
                <img src="src/assets/base64.png" width="120" height="35">
            </n-input-group>
        </n-form-item-row>
    </n-form>
    <n-button type="primary" block @click="handleRegister">注册</n-button>
</template>

<script lang="ts">
import {defineComponent, reactive} from "vue";
import {IRegisterFormType} from "@/interface/modules/system";
import {BagOutline, Moon, Person, QrCode, SunnySharp} from '@vicons/ionicons5';

export default defineComponent({
    name: "RegisterForm",
    emits: ["doRegister"],
    setup(props, {emit}) {
        const registerForm = reactive<IRegisterFormType>({
            username: undefined,
            password: undefined,
            password2: undefined,
            code: undefined
        })
        // 注册
        const handleRegister = () => {
            emit("doRegister", registerForm)
        }
        return {
            registerForm,
            handleRegister,
            BagOutline, Moon, Person, QrCode, SunnySharp
        }
    }
})
</script>

<style scoped>

</style>