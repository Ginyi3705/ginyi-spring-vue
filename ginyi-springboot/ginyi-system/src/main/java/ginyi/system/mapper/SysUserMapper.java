package ginyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ginyi.system.entity.SysUser;
import ginyi.system.entity.dto.params.SysUserParams;
import ginyi.system.entity.dto.vo.SysUserVo;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUserVo> selectUserList(SysUserParams user);

}
