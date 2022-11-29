package ginyi.system.service.impl;

import ginyi.system.entity.dto.params.SysUserParams;
import ginyi.system.entity.dto.vo.SysUserVo;
import ginyi.system.mapper.SysUserMapper;
import ginyi.system.service.SysUserService;
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
