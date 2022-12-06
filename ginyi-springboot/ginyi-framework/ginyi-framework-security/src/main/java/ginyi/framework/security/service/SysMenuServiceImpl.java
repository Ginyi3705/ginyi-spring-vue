package ginyi.framework.security.service;

import ginyi.common.constant.CacheConstants;
import ginyi.common.redis.cache.RedisCache;
import ginyi.common.utils.StringUtils;
import ginyi.system.domain.LoginUser;
import ginyi.system.domain.SysMenu;
import ginyi.system.domain.SysUser;
import ginyi.system.domain.model.dto.MenuDto;
import ginyi.system.mapper.SysMenuMapper;
import ginyi.system.service.ISysMenuService;
import ginyi.system.service.ITokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单 业务层处理
 *
 * @author ruoyi
 */
@Slf4j
@Service
public class SysMenuServiceImpl implements ISysMenuService {

    @Resource
    private SysMenuMapper menuMapper;
    @Resource
    private HttpServletRequest request;
    @Resource
    private ITokenService tokenService;
    @Resource
    private RedisCache redisCache;

    /**
     * 根据角色ID查询权限
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectMenuPermsByRoleId(Long roleId) {
        List<String> perms = menuMapper.selectMenuPermsByRoleId(roleId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }


    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectMenuPermsByUserId(Long userId) {
        List<String> perms = menuMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户查询系统菜单列表
     *
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuList(MenuDto menuDto) {
        List<SysMenu> menuList;
        LoginUser user = tokenService.getLoginUser(request);
        // 判断缓存是否有数据
        menuList = redisCache.getCacheList(CacheConstants.USER_MENU_KEY + user.getUsername(), SysMenu.class);
        if (menuList.size() > 0) {
            return menuList;
        }
        boolean isAdmin = SysUser.isAdmin(user.getUserId());
        // 管理员返回全部，普通用户则对应的菜单
        List<SysMenu> list = isAdmin ? menuMapper.selectList(null) : menuMapper.selectMenuListByUserId(menuDto, user.getUserId());
        // 转成树
        menuList = list.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> convertToMenuTree(menu, list)).collect(Collectors.toList());
        redisCache.setCacheList(CacheConstants.USER_MENU_KEY + user.getUsername(), menuList);
        return menuList;
    }

    /**
     * 菜单列表转成树状
     *
     * @param menu
     * @param list
     * @return
     */
    public SysMenu convertToMenuTree(SysMenu menu, List<SysMenu> list) {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(menu, sysMenu);
        List<SysMenu> children = list.stream()
                .filter(subMenu -> menu.getMenuId().equals(subMenu.getParentId()))
                .map(subMenu -> convertToMenuTree(subMenu, list)).collect(Collectors.toList());
        sysMenu.setChildren(children);
        return sysMenu;
    }
}
