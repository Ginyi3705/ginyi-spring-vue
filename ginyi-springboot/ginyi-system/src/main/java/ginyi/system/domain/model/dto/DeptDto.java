package ginyi.system.domain.model.dto;

import ginyi.common.swagger.AddGroup;
import ginyi.common.swagger.UpdateGroup;
import ginyi.system.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class DeptDto extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    @ApiModelProperty("部门id")
    @NotNull(groups = UpdateGroup.class, message = "部门id不能为空")
    private Long deptId;

    /**
     * 父部门ID
     */
    @ApiModelProperty("父级id")
    private Long parentId;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    @NotBlank(groups = {AddGroup.class, UpdateGroup.class}, message = "部门名称不能为空")
    @Size(min = 0, max = 30, message = "部门名称长度不能超过30个字符")
    private String deptName;

    /**
     * 显示顺序
     */
    @ApiModelProperty("显示顺序")
    @NotNull(groups = {AddGroup.class, UpdateGroup.class}, message = "显示顺序不能为空")
    private Integer sort;

    /**
     * 负责人
     */
    @ApiModelProperty("部门负责人")
    private String leader;

    /**
     * 联系电话
     */
    @ApiModelProperty("部门负责人电话")
    @Pattern(regexp = "(?:0|86|\\+86)?1[3-9]\\d{9}", message = "手机号码格式不正确")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty("部门负责人邮箱")
    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    /**
     * 状态:0正常,1停用
     */
    @ApiModelProperty("状态，0正常，1停用")
    @Size(min = 0, max = 1, message = "状态不合法，0正常，1停用")
    private String status;

    /**
     * 创建时间，开始时间
     */
    @ApiModelProperty("创建时间，开始时间")
    private Date beginTime;

    /**
     * 创建时间，结束时间
     */
    @ApiModelProperty("创建时间，结束时间")
    private Date endTime;


    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}
