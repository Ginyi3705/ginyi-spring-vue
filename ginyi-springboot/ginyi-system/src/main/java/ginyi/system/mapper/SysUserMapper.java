package ginyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ginyi.system.domain.SysUser;
import ginyi.system.domain.model.dto.UserDto;
import org.apache.ibatis.annotations.Param;

/**
 * 用户表
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(SysUser user);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByUserName(String userName);

    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    public SysUser checkUserNameUnique(String userName);

    /**
     * 新增用户信息
     *
     * @param userDto 用户信息
     * @return 结果
     */
    public int insertUser(@Param("userDto") UserDto userDto);

    /**
     * 插入用户岗位中间表, 插入用户对应的岗位信息
     * @param userDto
     * @return
     */
    public int insertUserPostIds(@Param("userDto") UserDto userDto);

    /**
     * 插入用户角色中间表, 插入用户对应的角色信息
     * @param userDto
     * @return
     */
    public int insertUserRoleIds(@Param("userDto") UserDto userDto);

}
