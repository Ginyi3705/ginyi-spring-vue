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
                     type="password" @keyup.enter.native="handleLogin"
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
                <img :src="`data:image/png;base64,${captchaCode ?? null}`"
                     @click="() => getCaptcha()"
                     style="width: 120px; height: 34px; cursor: pointer"
                     alt="重新获取">
            </n-input-group>
        </n-form-item-row>
    </n-form>
    <n-button type="primary" block @click="handleLogin">登录</n-button>
</template>

<script lang="ts">
import {defineComponent, onMounted, reactive, ref, watchEffect} from "vue";
import {BagOutline, Moon, Person, QrCode, SunnySharp} from '@vicons/ionicons5';
import type {ILoginFormType} from "@/interface/modules/system";
import {userController} from "@/api";

export default defineComponent({
    name: "LoginForm",
    emits: ["doLogin"],
    props: {
        isSuccess: {
            type: [Boolean, String],
            default: false
        }
    },
    setup(props, {emit}) {
        // 验证码
        const captchaCode = ref<string | undefined>(undefined)
        const loginForm = reactive<ILoginFormType>({
            username: undefined,
            password: undefined,
            code: undefined
        })
        // 登录
        const handleLogin = () => {
            emit("doLogin", loginForm)
        }
        // 获取验证码
        const getCaptcha = () => {
            userController.captcha().then(res => {
                captchaCode.value = res.data.img
            })
        }
        // 监听登录状态，失败则更新验证码
        watchEffect(() => {
            if (props.isSuccess !== true) {
                getCaptcha()
            }
        })

        // 加载验证码
        onMounted(() => {
            getCaptcha()
        })

        return {
            loginForm,
            captchaCode,
            handleLogin,
            getCaptcha,
            BagOutline, Moon, Person, QrCode, SunnySharp
        }
    }
})
</script>

<style scoped>

</style>