package ginyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ginyi.system.domain.SysUser;

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

}
