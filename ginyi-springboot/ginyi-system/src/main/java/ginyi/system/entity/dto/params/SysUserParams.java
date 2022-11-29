package ginyi.system.entity.dto.params;

import com.fasterxml.jackson.annotation.JsonFormat;
import ginyi.system.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("查询用户列表参数")
public class SysUserParams extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门id")
    private Long deptId;

    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    /**
     * 用户邮箱
     */
    @ApiModelProperty(value = "用户邮箱")
    private String email;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String phoneNumber;

    /**
     * 用户性别
     */
    @ApiModelProperty(value = "性别")
    private String sex;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;


    /**
     * 帐号状态（0正常 1停用）
     */
    @ApiModelProperty(value = "账号状态（0正常 1停用）")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @ApiModelProperty(value = "删除标志（0未删除 2已删除）")
    private String delFlag;

    /**
     * 最后登录IP
     */
    @ApiModelProperty(value = "最后登录ip")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后登录时间")
    private Date loginDate;

    /**
     * 起始索引
     */
    @ApiModelProperty(value = "当前页数")
    @Min(1)
    private Integer pageNum;

    /**
     * 每页的条数
     */
    @ApiModelProperty(value = "每页条数")
    @Min(1)
    private Integer pageSize;

}
