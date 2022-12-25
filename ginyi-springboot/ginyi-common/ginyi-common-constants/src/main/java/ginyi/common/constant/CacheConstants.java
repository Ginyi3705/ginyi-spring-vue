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

    /** 用户前缀，用于批量删除 */
    public static final String USER_KEY_PREFIX = "user_*";
    /** 用户不存在的key */
    public static final String USER_NOT_EXIST_KEY = "user_not_exist:";
    /** 用户详情 */
    public static final  String USER_DETAILS_BY_USERID_KEY = "user_details_by_userId:";

    /** 部门前缀，用于批量删除 */
    public static final String DEPT_KEY_PREFIX = "dept_*";
    /** 部门不存在的key */
    public static final String DEPT_NOT_EXIST_KEY = "dept_not_exist:";
    /** 部门详情 */
    public static final String DEPT_DETAILS_BY_DEPTID_KEY = "dept_details_by_deptId:";
}
