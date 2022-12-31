package ginyi.system.service;

import ginyi.system.domain.model.dto.RoleDto;
import ginyi.system.domain.model.vo.BaseVo;
import ginyi.system.domain.model.vo.RoleVo;

import java.util.Set;

/**
 * 角色业务层
 *
 * @author ruoyi
 */
public interface ISysRoleService {
    /**
     * 根据用户ID查询角色权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public Set<String> selectRolePermissionByUserId(Long userId);

    /**
     * 角色列表
     * @param roleDto
     * @param page
     * @param pageSize
     * @return
     */
    BaseVo<RoleVo> list(RoleDto roleDto, Long page, Long pageSize);

    /**
     * 根据角色id获取角色
     * @param roleId
     * @return
     */
    public RoleVo getRoleByRoleId(Long roleId);
}
