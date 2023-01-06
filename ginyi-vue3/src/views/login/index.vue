<template>
    <n-card class="login-box" :style="{height: clientHeight + 'px'}">
        <div style="display: flex; justify-content: flex-end" class="dark-switch">
            <n-switch v-model:value="darkTheme">
                <template #checked>
                    <n-icon size="14" color="#ffd93b" background="#212126">
                        <SunnySharp/>
                    </n-icon>
                </template>
                <template #unchecked>
                    <n-icon size="14" color="#ffd93b" background="#212126">
                        <Moon/>
                    </n-icon>
                </template>
            </n-switch>
        </div>
        <div
            style="width: 400px; position: absolute; top: 40%; left: 50%; transform: translate(-50%, -50%);
            border: 1px solid #ccc; border-radius: 8px; padding: 30px;">
            <n-tabs
                class="card-tabs"
                default-value="signin"
                size="large"
                animated
                style="margin: 0 -4px"
                pane-style="padding-left: 4px; padding-right: 4px; box-sizing: border-box;">
                <n-tab-pane name="signin" tab="登录">
                    <LoginForm @doLogin="doLogin"/>
                </n-tab-pane>
                <n-tab-pane name="signup" tab="注册">
                    <RegisterForm @doRegister="doRegister"/>
                </n-tab-pane>
            </n-tabs>
        </div>
    </n-card>
</template>

<script setup lang="ts">
import {Moon, SunnySharp} from '@vicons/ionicons5';
import {useSystemStore} from "@/store/modules/useSystemStore";
import {storeToRefs} from "pinia";
import {ILoginFormType, IRegisterFormType} from "@/interface/modules/system";
import LoginForm from "@/views/login/loginForm.vue";
import RegisterForm from "@/views/login/RegisterForm.vue";
import {useRouter} from "vue-router";

// 系统深色主题
const {darkTheme, clientHeight} = storeToRefs(useSystemStore())
const router = useRouter()
// 登录
const doLogin = (data: ILoginFormType) => {
    if (data.username === "ginyi" && data.password === "123456" && data.code === "xnxs") {
        console.log("登录成功")
    } else {
        console.error("登录失败")
    }
    router.push({name: 'home'})
}
// 注册
const doRegister = (data: IRegisterFormType) => {
    console.log('注册data', data)
}

</script>

<style scoped lang="less">
.login-box {
    border-radius: 0;
    background-image: url("../src/assets/bg-light.svg");
    background-repeat: no-repeat;
    background-size: 100% 100%;
}

.dark-switch .n-switch {
    ::v-deep(.n-switch__rail) {
        background-color: #000e1c;
    }
}
</style>