/**
 * pinia - 系统状态
 */
export interface ISystemState {
    // 系统深色主题
    darkTheme: boolean | undefined;
    // 项目logo
    logo: string | undefined;
    // 项目名称 = title + name
    title: string | undefined;
    // 项目名称 = title + name
    name: string | undefined;
    // 作者
    author: string | undefined;
    // 客户端宽度
    clientWidth: number | undefined;
    // 客户端高度
    clientHeight: number | undefined;
    // Layout - header 的高度
    layoutHeaderHeight: number | undefined;
    // Layout - footer 的高度
    layoutFooterHeight: number | undefined;
    // 侧边栏折叠
    collapsed: boolean | undefined
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