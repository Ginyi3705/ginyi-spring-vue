package ginyi.system.service;

public interface ISysLoginService {

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @return 结果
     */
    public String login(String username, String password, String code);
}
