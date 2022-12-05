package ginyi.system.service;

import ginyi.system.domain.model.dto.LoginDto;
import ginyi.system.domain.model.dto.RegisterDto;
import ginyi.system.domain.model.vo.LoginVo;

public interface ISysLoginService {

    /**
     * 登录验证
     * @param loginDto
     * @return
     */
    public LoginVo login(LoginDto loginDto);

    public void register(RegisterDto registerDto);
}
