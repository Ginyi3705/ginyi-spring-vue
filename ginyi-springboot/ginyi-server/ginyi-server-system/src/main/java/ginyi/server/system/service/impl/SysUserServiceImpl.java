package ginyi.server.system.service.impl;

import ginyi.server.system.dto.params.SysUserParams;
import ginyi.server.system.dto.vo.SysUserVo;
import ginyi.server.system.mapper.SysUserMapper;
import ginyi.server.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {


    @Autowired
    private SysUserMapper userMapper;

    @Override
    public List<SysUserVo> selectUserList(SysUserParams user) {
        return userMapper.selectUserList(user);
    }
}
