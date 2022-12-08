package ginyi.system.domain.model.dto;

import ginyi.system.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@ApiModel("系统菜单请求参数")
public class MenuDto extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单名称
     */
    @Size(max = 50, message = "菜单名称长度不能超过50个字符")
    @ApiModelProperty("菜单名称")
    private String menuName;

    /**
     * 排序
     */
    @ApiModelProperty("排序（1升序，0降序）")
    private Integer sort = 1;

    /**
     * 路由地址
     */
    @ApiModelProperty("路由地址")
    @Size(max = 200, message = "路由地址长度不得超过200个字符")
    private String path;

    /**
     * 组件路径
     */
    @ApiModelProperty("组件路径")
    @Size(max = 200, message = "组件路径长度不得超过200个字符")
    private String component;

    /**
     * 路由参数
     */
    @ApiModelProperty("路由参数")
    private String query;

    /**
     * 是否为外链（0是 1否）
     */
    @ApiModelProperty("是否为外链（0是 1否）")
    private String isFrame;


    /**
     * 类型（M目录 C菜单 F按钮）
     */
    @ApiModelProperty("类型（M目录 C菜单 F按钮）")
    private String menuType;

    /**
     * 显示状态（0显示 1隐藏）
     */
    @ApiModelProperty("显示状态（0显示 1隐藏）")
    private String visible;

    /**
     * 菜单状态（0正常 1停用）
     */
    @ApiModelProperty("菜单状态（0正常 1停用）")
    private String status;

    /**
     * 权限字符串
     */
    @ApiModelProperty("权限字符串")
    private String perms;

    /**
     * 菜单图标
     */
    @ApiModelProperty("菜单图标")
    private String icon;

}
