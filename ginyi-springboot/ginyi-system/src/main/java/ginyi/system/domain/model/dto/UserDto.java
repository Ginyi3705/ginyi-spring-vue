package ginyi.system.domain.model.dto;

import ginyi.common.swagger.AddGroup;
import ginyi.common.swagger.UpdateGroup;
import ginyi.system.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("系统用户请求参数")
public class UserDto extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ApiModelProperty("用户id")
    @NotNull(groups = UpdateGroup.class, message = "用户id不能为空")
    private Long userId;

    /**
     * 用户账号
     */
    @ApiModelProperty("用户账号")
    @NotBlank(groups = {UpdateGroup.class, AddGroup.class}, message = "用户账号不能为空")
    private String userName;

    /**
     * 部门ID
     */
    @ApiModelProperty("部门id")
    @NotNull(groups = {UpdateGroup.class, AddGroup.class}, message = "部门id不能为空")
    private Long deptId;

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String nickName;

    /**
     * 用户邮箱
     */
    @ApiModelProperty("邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    @Pattern(regexp = "(?:0|86|\\+86)?1[3-9]\\d{9}", message = "手机号码格式不正确")
    private String phoneNumber;

    /**
     * 用户性别
     */
    @ApiModelProperty("性别（0男 1女 2未知）")
    @Size(min = 0, max = 2, message = "性别不合法，0男 1女 2未知")
    private String sex;

    /**
     * 用户头像
     */
    @ApiModelProperty("头像")
    private String avatar;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    @ApiModelProperty("状态（0正常 1停用）")
    private String status;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;


    /**
     * 岗位ID
     */
    @ApiModelProperty("岗位id")
    private Long[] postIds;

    /**
     * 角色ID
     */
    @ApiModelProperty("角色id")
    private Long[] roleIds;

}
