package ginyi.server.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import ginyi.server.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户模块", description = "用户模块")
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @ApiModelProperty("用户id")
    private Long userId;

    /** 部门ID */
    @ApiModelProperty("部门id")
    private Long deptId;

    /** 用户账号 */
    @ApiModelProperty("用户名")
    private String userName;

    /** 用户昵称 */
    @ApiModelProperty("用户昵称")
    private String nickName;

    /** 用户邮箱 */
    @ApiModelProperty("用户邮箱")
    private String email;

    /** 手机号码 */
    @ApiModelProperty("手机号码")
    private String phonenumber;

    /** 用户性别 */
    @ApiModelProperty("性别")
    private String sex;

    /** 用户头像 */
    @ApiModelProperty("头像")
    private String avatar;

    /** 密码 */
    @ApiModelProperty("密码")
    private String password;

    /** 帐号状态（0正常 1停用） */
    @ApiModelProperty("账号状态（0正常 1停用）")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    @ApiModelProperty("删除标志（0未删除 2已删除）")
    private String delFlag;

    /** 最后登录IP */
    @ApiModelProperty("最后登录ip")
    private String loginIp;

    /** 最后登录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("最后登录时间")
    private Date loginDate;
}
