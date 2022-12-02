package ginyi.system.mapper;

import java.util.List;

/**
 * 菜单表 数据层
 *
 * @author ruoyi
 */
public interface SysMenuMapper
{
    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public List<String> selectMenuPermsByUserId(Long userId);

    /**
     * 根据角色ID查询权限
     * 
     * @param roleId 角色ID
     * @return 权限列表
     */
    public List<String> selectMenuPermsByRoleId(Long roleId);

}
