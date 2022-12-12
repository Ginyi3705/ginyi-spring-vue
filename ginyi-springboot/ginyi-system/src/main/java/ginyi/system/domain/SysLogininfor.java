package ginyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;
import java.util.Date;

/**
 * 系统访问记录表 sys_logininfor
 *
 * @author ruoyi
 */
@Data
@Document("sys_log_login")
public class SysLogininfor extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long infoId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 登录状态 0成功 1失败
     */
    private String status;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 提示消息
     */
    private String msg;

    /**
     * 访问时间
     */
    @JsonFormat
    private Date loginTime;

}
