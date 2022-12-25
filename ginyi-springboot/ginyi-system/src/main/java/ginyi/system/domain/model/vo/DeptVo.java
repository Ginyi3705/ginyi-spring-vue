package ginyi.system.domain.model.vo;

import ginyi.system.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("系统部门返回数据")
public class DeptVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    @ApiModelProperty("部门id")
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
    private String deptName;

    /**
     * 显示顺序
     */
    @ApiModelProperty("显示顺序")
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
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty("部门负责人邮箱")
    private String email;

    /**
     * 状态:0正常,1停用
     */
    @ApiModelProperty("状态，0正常，1停用")
    private String status;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}
