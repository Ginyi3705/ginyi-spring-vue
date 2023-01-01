package ginyi.framework.security.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import ginyi.common.constant.CacheConstants;
import ginyi.common.constant.CommonMessageConstants;
import ginyi.common.exception.CommonException;
import ginyi.common.mysql.MyPage;
import ginyi.common.redis.cache.RedisCache;
import ginyi.common.result.StateCode;
import ginyi.common.utils.StringUtils;
import ginyi.system.domain.SysRole;
import ginyi.system.domain.model.dto.RoleDto;
import ginyi.system.domain.model.vo.BaseVo;
import ginyi.system.domain.model.vo.RoleVo;
import ginyi.system.mapper.SysRoleMapper;
import ginyi.system.service.ISysRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysRoleServiceImpl implements ISysRoleService {

    @Resource
    private SysRoleMapper roleMapper;
    @Resource
    private RedisCache redisCache;

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<SysRole> perms = roleMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms) {
            if (StringUtils.isNotNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 角色列表
     *
     * @param roleDto
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public BaseVo<RoleVo> list(RoleDto roleDto, Long page, Long pageSize) {
        IPage<RoleVo> list = roleMapper.list(roleDto, new MyPage(page, pageSize).getPage());
        BaseVo<RoleVo> baseVo = new BaseVo<>();
        baseVo.setList(list.getRecords());
        baseVo.setCount((int) list.getTotal());
        return baseVo;
    }

    /**
     * 根据角色id获取角色
     *
     * @param roleId
     * @return
     */
    @Override
    public RoleVo getRoleByRoleId(Long roleId) {
        // 检查缓存中是否标记着空id
        if (redisCache.hasKey(CacheConstants.ROLE_NOT_EXIST_KEY + roleId)) {
            throw new CommonException(StateCode.ERROR_NOT_EXIST, roleId + CommonMessageConstants.ROLE_NOT_EXIST);
        }
        RoleVo roleVo = new RoleVo();
        // 检查缓存中是否存在
        SysRole role = redisCache.getCacheObject(CacheConstants.ROLE_DETAILS_BY_ROLEID_KEY + roleId, SysRole.class);
        if (StringUtils.isNotNull(role)) {
            BeanUtils.copyProperties(role, roleVo);
            return roleVo;
        }
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getRoleId, roleId);
        role = roleMapper.selectOne(queryWrapper);
        if (StringUtils.isNull(role)) {
            redisCache.setCacheObject(CacheConstants.ROLE_NOT_EXIST_KEY + roleId, null);
            throw new CommonException(StateCode.ERROR_NOT_EXIST, roleId + CommonMessageConstants.ROLE_NOT_EXIST);
        }
        redisCache.setCacheObject(CacheConstants.ROLE_DETAILS_BY_ROLEID_KEY + roleId, role);
        BeanUtils.copyProperties(role, roleVo);
        return roleVo;
    }

    /**
     * 新增角色
     *
     * @param roleDto
     */
    @Override
    @Transactional
    public void addRole(RoleDto roleDto) {
        // 角色名称是否被使用
        if (redisCache.hasKey(CacheConstants.ROLE_NAME_USED_KEY + roleDto.getRoleName())) {
            throw new CommonException(StateCode.ERROR_EXIST, CommonMessageConstants.ROLE_NAME_USED);
        }
        // 角色权限字符是否被使用
        if (redisCache.hasKey(CacheConstants.ROLE_CODE_USED_KEY + roleDto.getRoleName())) {
            throw new CommonException(StateCode.ERROR_EXIST, CommonMessageConstants.ROLE_PERMISSION_CODE_USED);
        }
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getRoleName, roleDto.getRoleName());
        SysRole role = roleMapper.selectOne(queryWrapper);
        if (StringUtils.isNotNull(role)) {
            redisCache.setCacheObject(CacheConstants.ROLE_NAME_USED_KEY + roleDto.getRoleName(), null);
            throw new CommonException(StateCode.ERROR_EXIST, CommonMessageConstants.ROLE_NAME_USED);
        }
        queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getRoleKey, roleDto.getRoleKey());
        role = roleMapper.selectOne(queryWrapper);
        if (StringUtils.isNotNull(role)) {
            redisCache.setCacheObject(CacheConstants.ROLE_NAME_USED_KEY + roleDto.getRoleKey(), null);
            throw new CommonException(StateCode.ERROR_EXIST, CommonMessageConstants.ROLE_PERMISSION_CODE_USED);
        }
        roleMapper.insertRole(roleDto);
        roleMapper.insertRoleMenu(roleDto);
        redisCache.removeCacheObject(CacheConstants.ROLE_KEY_PREFIX);
    }
}
