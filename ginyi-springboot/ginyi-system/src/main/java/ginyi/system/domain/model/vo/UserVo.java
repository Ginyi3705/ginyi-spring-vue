package ginyi.system.domain.model.vo;

import ginyi.system.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("系统用户返回数据")
public class UserVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ApiModelProperty("用户id")
    private Long userId;

    /**
     * 用户账号
     */
    @ApiModelProperty("用户账号")
    private String userName;

    /**
     * 部门ID
     */
    @ApiModelProperty("部门id")
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
    private String email;

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    private String phoneNumber;

    /**
     * 用户性别
     */
    @ApiModelProperty("性别（0男 1女 2未知）")
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
     * 岗位ID
     */
    @ApiModelProperty("岗位id")
    private List<Long> postIds;

    /**
     * 角色ID
     */
    @ApiModelProperty("角色id")
    private List<Long> roleIds;
}
