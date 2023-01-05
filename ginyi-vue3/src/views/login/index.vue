<template>
    <n-card class="login-box">
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
            style="width: 400px; position: absolute; top: 40%; left: 50%; transform: translate(-50%, -50%); border: 1px solid #ccc; border-radius: 8px; padding: 30px;">
            <n-tabs
                class="card-tabs"
                default-value="signin"
                size="large"
                animated
                style="margin: 0 -4px"
                pane-style="padding-left: 4px; padding-right: 4px; box-sizing: border-box;">
                <n-tab-pane name="signin" tab="登录">
                    <n-form label-placement="left" label-width="auto">
                        <n-form-item-row label="用户名">
                            <n-input placeholder="用户名" clearable>
                                <template #prefix>
                                    <n-icon :component="Person"/>
                                </template>
                            </n-input>
                        </n-form-item-row>
                        <n-form-item-row label="密码">
                            <n-input type="password" show-password-on="mousedown" placeholder="密码" clearable>
                                <template #prefix>
                                    <n-icon :component="BagOutline"/>
                                </template>
                            </n-input>
                        </n-form-item-row>
                        <n-form-item-row label="验证码">
                            <n-input-group>
                                <n-input placeholder="验证码" clearable>
                                    <template #prefix>
                                        <n-icon :component="QrCode"/>
                                    </template>
                                </n-input>
                                <img src="src/assets/base64.png" width="120" height="35">
                            </n-input-group>
                        </n-form-item-row>
                    </n-form>
                    <n-button type="primary" block>登录</n-button>
                </n-tab-pane>
                <n-tab-pane name="signup" tab="注册">
                    <n-form label-placement="left" label-width="auto">
                        <n-form-item-row label="用户名">
                            <n-input placeholder="用户名" clearable>
                                <template #prefix>
                                    <n-icon :component="Person"/>
                                </template>
                            </n-input>
                        </n-form-item-row>
                        <n-form-item-row label="密码">
                            <n-input type="password" show-password-on="mousedown" placeholder="密码" clearable>
                                <template #prefix>
                                    <n-icon :component="BagOutline"/>
                                </template>
                            </n-input>
                        </n-form-item-row>
                        <n-form-item-row label="重复密码">
                            <n-input type="password" show-password-on="mousedown" placeholder="重复密码" clearable>
                                <template #prefix>
                                    <n-icon :component="BagOutline"/>
                                </template>
                            </n-input>
                        </n-form-item-row>
                        <n-form-item-row label="验证码">
                            <n-input-group>
                                <n-input placeholder="验证码" clearable>
                                    <template #prefix>
                                        <n-icon :component="QrCode"/>
                                    </template>
                                </n-input>
                                <img src="src/assets/base64.png" width="120" height="35">
                            </n-input-group>
                        </n-form-item-row>
                    </n-form>
                    <n-button type="primary" block>注册</n-button>
                </n-tab-pane>
            </n-tabs>
        </div>
    </n-card>
</template>

<script setup lang="ts">
import {BagOutline, Moon, Person, QrCode, SunnySharp} from '@vicons/ionicons5';
import {useSystemStore} from "@/store/modules/useSystemStore";
import {storeToRefs} from "pinia";

const {darkTheme} = storeToRefs(useSystemStore())

</script>

<style scoped lang="less">
.login-box {
    border-radius: 0;
    height: 100vh;
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