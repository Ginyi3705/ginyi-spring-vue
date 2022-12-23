package ginyi.framework.security.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import ginyi.common.constant.MessageConstants;
import ginyi.common.constant.UserConstants;
import ginyi.common.exception.CommonException;
import ginyi.common.result.StateCode;
import ginyi.common.utils.StringUtils;
import ginyi.framework.security.utils.SecurityUtils;
import ginyi.system.domain.SysDept;
import ginyi.system.domain.SysPost;
import ginyi.system.domain.SysRole;
import ginyi.system.domain.SysUser;
import ginyi.system.domain.model.dto.UserDto;
import ginyi.system.mapper.SysDeptMapper;
import ginyi.system.mapper.SysPostMapper;
import ginyi.system.mapper.SysRoleMapper;
import ginyi.system.mapper.SysUserMapper;
import ginyi.system.service.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class SysUserServiceImpl implements ISysUserService {

    @Resource
    private SysUserMapper userMapper;
    @Resource
    private SysPostMapper postMapper;
    @Resource
    private SysRoleMapper roleMapper;
    @Resource
    private SysDeptMapper deptMapper;

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserProfile(SysUser user) {
        return userMapper.updateUser(user);
    }

    @Override
    public SysUser selectUserByUserName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public String checkUserNameUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkUserNameUnique(user.getUserName());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean registerUser(SysUser user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userMapper.insertUser(userDto) > 0;
    }

    /**
     * 新增用户
     *
     * @param userDto
     */
    @Override
    @Transactional
    public void addUser(UserDto userDto) {
        // 判断部门是否存在
        SysDept sysDept;
        LambdaQueryWrapper<SysDept> deptQueryWrapper = new LambdaQueryWrapper<>();
        deptQueryWrapper.eq(SysDept::getDeptId, userDto.getDeptId());
        sysDept = deptMapper.selectOne(deptQueryWrapper);
        if (sysDept == null) {
            throw new CommonException(StateCode.ERROR_NOT_EXIST, MessageConstants.DEPT_NOT_EXIST);
        }

        LambdaQueryWrapper<SysUser> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(SysUser::getUserName, userDto.getUserName());
        SysUser user = userMapper.selectOne(userQueryWrapper);
        if (user != null) {
            throw new CommonException(StateCode.ERROR_EXIST, MessageConstants.USER_NAME_USED);
        }
        // todo 为空时，设置为 sys_config 表的默认密码
        // 密码加密
        if (StringUtils.isNotBlank(userDto.getPassword())) {
            userDto.setPassword(SecurityUtils.encryptPassword(userDto.getPassword()));
        }
        userMapper.insertUser(userDto);

        // 判断插入的岗位id是否存在
        if (userDto.getPostIds().length > 0) {
            SysPost sysPost;
            for (Long postId : userDto.getPostIds()) {
                LambdaQueryWrapper<SysPost> postQueryWrapper = new LambdaQueryWrapper<>();
                postQueryWrapper.eq(SysPost::getPostId, postId);
                sysPost = postMapper.selectOne(postQueryWrapper);
                if (sysPost == null) {
                    throw new CommonException(StateCode.ERROR_NOT_EXIST, MessageConstants.POST_NOT_EXIST);
                }
            }
            userMapper.insertUserPostIds(userDto);
        }

        // 判断插入的角色id是否存在
        if (userDto.getRoleIds().length > 0) {
            SysRole sysRole;
            for (Long roleId : userDto.getRoleIds()) {
                LambdaQueryWrapper<SysRole> roleQueryWrapper = new LambdaQueryWrapper<>();
                roleQueryWrapper.eq(SysRole::getRoleId, roleId);
                sysRole = roleMapper.selectOne(roleQueryWrapper);
                if (sysRole == null) {
                    throw new CommonException(StateCode.ERROR_NOT_EXIST, MessageConstants.ROLE_NOT_EXIST);
                }
            }
            userMapper.insertUserRoleIds(userDto);
        }

    }
}
