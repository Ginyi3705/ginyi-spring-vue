package ginyi.system.service;

import ginyi.system.domain.SysUser;
import ginyi.system.domain.model.dto.UserDto;

public interface ISysUserService {

    /**
     * 修改用户基本信息
     *
     * @param userDto 用户信息
     * @return 结果
     */
    public void updateUser(UserDto userDto);

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
     * @param user 用户信息
     * @return 结果
     */
    public String checkUserNameUnique(SysUser user);

    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public boolean registerUser(SysUser user);

    /**
     * 新增用户
     *
     * @param userDto
     */
    public void addUser(UserDto userDto);
}
