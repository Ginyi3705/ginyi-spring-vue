package ginyi.system.domain.model.dto;

import ginyi.common.swagger.AddGroup;
import ginyi.common.swagger.UpdateGroup;
import ginyi.system.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("系统角色请求参数")
public class RoleDto extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色ID")
    @NotNull(groups = UpdateGroup.class, message = "角色id不能为空")
    private Long roleId;

    @ApiModelProperty("角色名称")
    @NotBlank(groups = {AddGroup.class, UpdateGroup.class}, message = "角色名称不能为空")
    @Size(max = 20, message = "角色名称不能超过20个字符")
    private String roleName;

    @ApiModelProperty("角色权限字符串")
    @NotBlank(groups = {AddGroup.class, UpdateGroup.class}, message = "角色权限字符串不能为空")
    @Size(max = 20, message = "角色权限字符不能超过20个字符")
    private String roleKey;

    @ApiModelProperty("角色排序")
    @NotNull(groups = {AddGroup.class, UpdateGroup.class}, message = "角色排序不能为空")
    private Integer sort;

    @ApiModelProperty("角色状态（0正常 1停用）")
    @NotBlank(groups = {AddGroup.class, UpdateGroup.class}, message = "角色状态不能为空")
    @Size(max = 1, message = "状态不合法")
    private String status;

    @ApiModelProperty("角色菜单权限")
    private Set<String> permissions;

    @ApiModelProperty("创建时间，开始时间")
    private Date beginTime;

    @ApiModelProperty("创建时间，结束时间")
    private Date endTime;
}
