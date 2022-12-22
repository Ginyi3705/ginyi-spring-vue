package ginyi.common.constant;

/**
 * 缓存的key 常量
 *
 * @author ruoyi
 */
public class CacheConstants {
    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 登录账户密码错误次数 redis key
     */
    public static final String PWD_ERR_CNT_KEY = "pwd_err_cnt:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /** 菜单前缀，用于批量删除 */
    public static final String MENU_KEY_PREFIX = "menu_*";
    /** 用户菜单的菜单列表 */
    public static final String MENU_USER_LIST_KEY = "menu_user_list:";
    /** 菜单不存在的key */
    public static final String MENU_NOT_EXIST_KEY = "menu_not_exist:";
    /** 菜单详情 */
    public static final String MENU_DETAILS_BY_ID_KEY = "menu_details_by_id:";
}
