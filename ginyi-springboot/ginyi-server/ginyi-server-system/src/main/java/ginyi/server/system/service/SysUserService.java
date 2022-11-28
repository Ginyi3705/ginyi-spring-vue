package ginyi.server.system.service;

import ginyi.server.system.dto.params.SysUserParams;
import ginyi.server.system.dto.vo.SysUserVo;

import java.util.List;

public interface SysUserService {

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUserVo> selectUserList(SysUserParams user);
}
