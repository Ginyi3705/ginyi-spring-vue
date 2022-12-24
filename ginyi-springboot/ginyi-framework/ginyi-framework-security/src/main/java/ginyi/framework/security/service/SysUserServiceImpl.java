package ginyi.framework.security.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import ginyi.common.constant.CacheConstants;
import ginyi.common.constant.MessageConstants;
import ginyi.common.constant.UserConstants;
import ginyi.common.exception.CommonException;
import ginyi.common.mysql.MyPage;
import ginyi.common.redis.cache.RedisCache;
import ginyi.common.result.StateCode;
import ginyi.common.utils.StringUtils;
import ginyi.system.domain.SysDept;
import ginyi.system.domain.SysPost;
import ginyi.system.domain.SysRole;
import ginyi.system.domain.SysUser;
import ginyi.system.domain.model.dto.UserDto;
import ginyi.system.domain.model.vo.BaseVo;
import ginyi.system.domain.model.vo.UserVo;
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
    @Resource
    private RedisCache redisCache;

    /**
     * 修改用户基本信息
     *
     * @param userDto 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public void updateUser(UserDto userDto) {
        if (checkLogic(userDto)) {
            userMapper.updateUser(userDto);
            if (StringUtils.isNotNull(userDto.getPostIds()) && userDto.getPostIds().size() > 0) {
                userMapper.updateUserPostIds(userDto);
            }
            if (StringUtils.isNotNull(userDto.getRoleIds()) && userDto.getRoleIds().size() > 0) {
                userMapper.updateUserRoleIds(userDto);
            }
            // 清除缓存
            redisCache.removeCacheObject(CacheConstants.USER_KEY_PREFIX);
        }
    }


    /**
     * 新增用户
     *
     * @param userDto
     */
    @Override
    @Transactional
    public void addUser(UserDto userDto) {
        if (checkLogic(userDto)) {
            userMapper.insertUser(userDto);
            if (StringUtils.isNotNull(userDto.getPostIds()) && userDto.getPostIds().size() > 0) {
                userMapper.insertUserPostIds(userDto);
            }
            if (StringUtils.isNotNull(userDto.getRoleIds()) && userDto.getRoleIds().size() > 0) {
                userMapper.insertUserRoleIds(userDto);
            }
            // 清除缓存
            redisCache.removeCacheObject(CacheConstants.USER_KEY_PREFIX);
        }
    }

    /**
     * 根据用户id查询用户
     *
     * @param userId
     */
    @Override
    public UserVo getUserByUserId(String userId) {
        // 检查缓存中是否记录着空的userId
        if (redisCache.hasKey(CacheConstants.USER_NOT_EXIST_KEY + userId)) {
            throw new CommonException(StateCode.ERROR_NOT_EXIST, MessageConstants.USER_NOT_EXIST);
        }
        UserVo user;
        // 检查缓存中是否有该user
        user = redisCache.getCacheObject(CacheConstants.USER_DETAILS_BY_USERID_KEY + userId, UserVo.class);
        if (StringUtils.isNotNull(user)) {
            return user;
        }
        user = userMapper.selectUserByUserId(userId);
        if (StringUtils.isNull(user)) {
            redisCache.setCacheObject(CacheConstants.USER_NOT_EXIST_KEY + userId, null);
            throw new CommonException(StateCode.ERROR_NOT_EXIST, MessageConstants.USER_NOT_EXIST);
        }
        // 存入缓存
        redisCache.setCacheObject(CacheConstants.USER_DETAILS_BY_USERID_KEY + userId, user);
        return user;
    }


    /**
     * 获取用户列表(不含admin)
     *
     * @param userDto
     * @param page
     * @param pageSize
     */
    @Override
    public BaseVo<UserVo> list(UserDto userDto, Long page, Long pageSize) {
        IPage<UserVo> list = userMapper.list(userDto, new MyPage(page, pageSize).getPage());
        BaseVo<UserVo> baseVo = new BaseVo<>();
        baseVo.setList(list.getRecords());
        baseVo.setCount((int) list.getTotal());
        return baseVo;
    }


    /**
     * 根据用户id删除用户
     *
     * @param userId
     */
    @Override
    public void removeById(Long userId) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserId, userId);
        SysUser user = userMapper.selectOne(queryWrapper);
        if (StringUtils.isNull(user)) {
            throw new CommonException(StateCode.ERROR_NOT_EXIST, MessageConstants.USER_NOT_EXIST);
        }
        userMapper.deleteById(userId);
    }

    /**
     * 校验逻辑
     *
     * @param userDto
     */
    public boolean checkLogic(UserDto userDto) {
        // 判断部门是否存在
        if (StringUtils.isNotNull(userDto.getDeptId())) {
            SysDept sysDept;
            LambdaQueryWrapper<SysDept> deptQueryWrapper = new LambdaQueryWrapper<>();
            deptQueryWrapper.eq(SysDept::getDeptId, userDto.getDeptId());
            sysDept = deptMapper.selectOne(deptQueryWrapper);
            if (sysDept == null) {
                throw new CommonException(StateCode.ERROR_NOT_EXIST, MessageConstants.DEPT_NOT_EXIST);
            }
        }

        // 用户名是否被占用（插入时）
        if (StringUtils.isNotBlank(userDto.getUserName()) && StringUtils.isNull(userDto.getUserId())) {
            LambdaQueryWrapper<SysUser> userQueryWrapper = new LambdaQueryWrapper<>();
            userQueryWrapper.eq(SysUser::getUserName, userDto.getUserName());
            SysUser user = userMapper.selectOne(userQueryWrapper);
            if (user != null) {
                throw new CommonException(StateCode.ERROR_EXIST, MessageConstants.USER_NAME_USED);
            }
        }

        // 判断插入的岗位id是否存在
        if (StringUtils.isNotNull(userDto.getPostIds()) && userDto.getPostIds().size() > 0) {
            SysPost sysPost;
            for (Long postId : userDto.getPostIds()) {
                LambdaQueryWrapper<SysPost> postQueryWrapper = new LambdaQueryWrapper<>();
                postQueryWrapper.eq(SysPost::getPostId, postId);
                sysPost = postMapper.selectOne(postQueryWrapper);
                if (sysPost == null) {
                    throw new CommonException(StateCode.ERROR_NOT_EXIST, MessageConstants.POST_NOT_EXIST);
                }
            }
        }

        // 判断插入的角色id是否存在
        if (StringUtils.isNotNull(userDto.getRoleIds()) && userDto.getRoleIds().size() > 0) {
            SysRole sysRole;
            for (Long roleId : userDto.getRoleIds()) {
                LambdaQueryWrapper<SysRole> roleQueryWrapper = new LambdaQueryWrapper<>();
                roleQueryWrapper.eq(SysRole::getRoleId, roleId);
                sysRole = roleMapper.selectOne(roleQueryWrapper);
                if (sysRole == null) {
                    throw new CommonException(StateCode.ERROR_NOT_EXIST, MessageConstants.ROLE_NOT_EXIST);
                }
            }
        }
        return true;
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
}
