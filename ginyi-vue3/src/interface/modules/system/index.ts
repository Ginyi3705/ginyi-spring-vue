/**
 * pinia - 系统状态
 */
export interface ISystemState {
    // 系统深色主题
    darkTheme: boolean | undefined;
}

/**
 * 登录表单
 */
export interface ILoginFormType {
    // 用户名
    username: string | undefined;
    // 密码
    password: string | number | undefined;
    // 验证码
    code: string | undefined;
}

/**
 * 注册表单
 */
export interface IRegisterFormType {
    // 用户名
    username: string | undefined;
    // 密码
    password: string | number | undefined;
    // 重复密码
    password2: string | number | undefined;
    // 验证码
    code: string | undefined;
}