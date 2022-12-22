package ginyi.framework.security.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import ginyi.common.constant.CacheConstants;
import ginyi.common.constant.MessageConstants;
import ginyi.common.exception.CommonException;
import ginyi.common.redis.cache.RedisCache;
import ginyi.common.result.StateCode;
import ginyi.common.utils.StringUtils;
import ginyi.framework.security.utils.SecurityUtils;
import ginyi.system.domain.LoginUser;
import ginyi.system.domain.SysMenu;
import ginyi.system.domain.SysUser;
import ginyi.system.domain.model.dto.MenuDto;
import ginyi.system.domain.model.vo.MenuVo;
import ginyi.system.mapper.SysMenuMapper;
import ginyi.system.service.ISysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    public MenuVo selectMenuList() {
        List<SysMenu> menuList;
        MenuVo menuVo = new MenuVo();
        LoginUser user = SecurityUtils.getLoginUser();
        // 判断缓存是否有数据
        menuList = redisCache.getCacheList(CacheConstants.MENU_USER_LIST_KEY + user.getUsername(), SysMenu.class);
        if (menuList.size() > 0) {
            menuVo.setList(menuList);
            menuVo.setCount(menuList.size());
            return menuVo;
        }
        boolean isAdmin = SysUser.isAdmin(user.getUserId());
        // 管理员返回全部，普通用户则对应的菜单
        List<SysMenu> list = isAdmin ? menuMapper.selectList(null) : menuMapper.selectMenuListByUserId(user.getUserId());
        // 转成树
        menuList = list.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> convertToMenuTree(menu, list)).collect(Collectors.toList());
        redisCache.setCacheList(CacheConstants.MENU_USER_LIST_KEY + user.getUsername(), menuList);

        menuVo.setList(menuList);
        menuVo.setCount(menuList.size());
        return menuVo;
    }

    /**
     * 管理员查询（管理）菜单列表
     *
     * @param menuDto
     * @return
     */
    @Override
    public MenuVo selectMenuListByAdmin(MenuDto menuDto) {
        List<SysMenu> list = menuMapper.selectMenuListByAdmin();
        List<SysMenu> menuList = list.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> convertToMenuTree(menu, list)).collect(Collectors.toList());
        MenuVo menuVo = new MenuVo();
        menuVo.setList(menuList);
        menuVo.setCount(menuList.size());
        return menuVo;
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

    /**
     * 添加菜单
     *
     * @param menuDto
     */
    @Override
    public void addMenu(MenuDto menuDto) {
        // c 是菜单，其余的是目录或者按钮
        if ("C".equalsIgnoreCase(menuDto.getMenuType())) {
            if (menuDto.getComponent().isEmpty()) {
                throw new CommonException(StateCode.ERROR_PARAMS, MessageConstants.MENU_COMPONENT_NOT_EXIST);
            }
            if (menuDto.getPath().isEmpty()) {
                throw new CommonException(StateCode.ERROR_PARAMS, MessageConstants.MENU_PATH_NOT_EXIST);
            }
        }

        // 判断是否已存在
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getMenuName, menuDto.getMenuName());
        SysMenu result = menuMapper.selectOne(queryWrapper);
        if (result != null) {
            throw new CommonException(StateCode.ERROR_EXIST, MessageConstants.MENU_NAME_USED);
        }

        menuMapper.insertMenu(menuDto);
        // 清除menu的相关缓存
        redisCache.removeCacheObject(CacheConstants.MENU_KEY_PREFIX);
    }

    /**
     * 根据id获取菜单详情
     *
     * @param menuId
     * @return
     */
    @Override
    public SysMenu getMenuById(Long menuId) {
        SysMenu menu;

        // 判断是否是无效id
        if (redisCache.hasKey(CacheConstants.MENU_NOT_EXIST_KEY + menuId)) {
            throw new CommonException(StateCode.ERROR_NOT_EXIST, MessageConstants.MENU_NOT_EXIST);
        }
        // 查看缓存中是否有
        menu = redisCache.getCacheObject(CacheConstants.MENU_DETAILS_BY_ID_KEY + menuId, SysMenu.class);
        if (menu != null) {
            return menu;
        }

        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getMenuId, menuId);
        menu = menuMapper.selectOne(queryWrapper);

        if (menu == null) {
            // 存储不存在的key
            redisCache.setCacheObject(CacheConstants.MENU_NOT_EXIST_KEY + menuId, null);
            throw new CommonException(StateCode.ERROR_NOT_EXIST, MessageConstants.MENU_NOT_EXIST);
        }
        // 存入缓存
        redisCache.setCacheObject(CacheConstants.MENU_DETAILS_BY_ID_KEY + menuId, menu);
        return menu;
    }

    /**
     * 更新菜单
     *
     * @param menuDto
     */
    @Override
    public void updateMenu(MenuDto menuDto) {
        // c 是菜单，其余的是目录或者按钮
        if ("C".equalsIgnoreCase(menuDto.getMenuType())) {
            if (menuDto.getComponent().isEmpty()) {
                throw new CommonException(StateCode.ERROR_PARAMS, MessageConstants.MENU_COMPONENT_NOT_EXIST);
            }
            if (menuDto.getPath().isEmpty()) {
                throw new CommonException(StateCode.ERROR_PARAMS, MessageConstants.MENU_PATH_NOT_EXIST);
            }
        }

        // 判断menuId是否存在
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getMenuId, menuDto.getMenuId());
        SysMenu result = menuMapper.selectOne(queryWrapper);
        if (result == null) {
            throw new CommonException(StateCode.ERROR_NOT_EXIST, MessageConstants.MENU_NOT_EXIST);
        }

        menuMapper.updateMenu(menuDto);
        // 清除menu的相关缓存
        redisCache.removeCacheObject(CacheConstants.MENU_KEY_PREFIX);
    }

    /**
     * 根据菜单id删除
     *
     * @param menuId
     */
    @Override
    public void removeMenuById(Long menuId) {
        // 缓存中是否标记空id
        if (redisCache.hasKey(CacheConstants.MENU_NOT_EXIST_KEY + menuId)) {
            throw new CommonException(StateCode.ERROR_NOT_EXIST, MessageConstants.MENU_NOT_EXIST);
        }

        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getMenuId, menuId);
        SysMenu menu = menuMapper.selectOne(queryWrapper);

        // 数据不存在
        if (menu == null) {
            redisCache.setCacheObject(CacheConstants.MENU_NOT_EXIST_KEY + menuId, null);
            throw new CommonException(StateCode.ERROR_NOT_EXIST, MessageConstants.MENU_NOT_EXIST);
        }

        menuMapper.deleteById(menuId);
        redisCache.removeCacheObject(CacheConstants.MENU_KEY_PREFIX);
    }

    /**
     * 根据ids批量删除菜单
     * @param ids
     */
    @Override
    @Transactional
    public void delete(Set<Long> ids) {
        for (Long menuId : ids) {
            // 缓存中是否标记空id
            if (redisCache.hasKey(CacheConstants.MENU_NOT_EXIST_KEY + menuId)) {
                throw new CommonException(StateCode.ERROR_NOT_EXIST, MessageConstants.MENU_IDS_NOT_EXIST);
            }
        }
        menuMapper.deleteBatchIds(ids);
        redisCache.removeCacheObject(CacheConstants.MENU_KEY_PREFIX);

    }
}
