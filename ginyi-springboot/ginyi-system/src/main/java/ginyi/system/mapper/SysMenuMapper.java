package ginyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ginyi.system.domain.SysMenu;
import ginyi.system.domain.model.dto.MenuDto;

import java.util.List;

/**
 * 菜单表 数据层
 *
 * @author ruoyi
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
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

    /**
     * 根据用户查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuListByUserId(MenuDto menu, Long userId);

}
