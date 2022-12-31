package ginyi.framework.security.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import ginyi.common.mysql.MyPage;
import ginyi.common.utils.StringUtils;
import ginyi.system.domain.SysRole;
import ginyi.system.domain.model.dto.RoleDto;
import ginyi.system.domain.model.vo.BaseVo;
import ginyi.system.domain.model.vo.RoleVo;
import ginyi.system.mapper.SysRoleMapper;
import ginyi.system.service.ISysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysRoleServiceImpl implements ISysRoleService {

    @Resource
    private SysRoleMapper roleMapper;

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
}
