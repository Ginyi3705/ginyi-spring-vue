<template>
    <n-form label-placement="left" label-width="auto">
        <n-form-item-row label="用户名">
            <n-input v-model:value="loginForm.username" @keyup.enter.native="handleLogin" placeholder="用户名" clearable>
                <template #prefix>
                    <n-icon :component="Person"/>
                </template>
            </n-input>
        </n-form-item-row>
        <n-form-item-row label="密码">
            <n-input v-model:value="loginForm.password"
                     type="password"  @keyup.enter.native="handleLogin"
                     show-password-on="mousedown" placeholder="密码"
                     clearable>
                <template #prefix>
                    <n-icon :component="BagOutline"/>
                </template>
            </n-input>
        </n-form-item-row>
        <n-form-item-row label="验证码">
            <n-input-group>
                <n-input v-model:value="loginForm.code" @keyup.enter.native="handleLogin" placeholder="验证码" clearable>
                    <template #prefix>
                        <n-icon :component="QrCode"/>
                    </template>
                </n-input>
                <img src="src/assets/base64.png" width="120" height="35">
            </n-input-group>
        </n-form-item-row>
    </n-form>
    <n-button type="primary" block @click="handleLogin">登录</n-button>
</template>

<script lang="ts">
import {defineComponent, reactive} from "vue";
import {BagOutline, Moon, Person, QrCode, SunnySharp} from '@vicons/ionicons5';
import type {ILoginFormType} from "@/interface/modules/system";

export default defineComponent({
    name: "LoginForm",
    emits: ["doLogin"],
    setup(props, {emit}) {
        const loginForm = reactive<ILoginFormType>({
            username: undefined,
            password: undefined,
            code: undefined
        })
        // 登录
        const handleLogin = () => {
            emit("doLogin", loginForm)
        }
        return {
            loginForm,
            handleLogin,
            BagOutline, Moon, Person, QrCode, SunnySharp
        }
    }
})
</script>

<style scoped>

</style>