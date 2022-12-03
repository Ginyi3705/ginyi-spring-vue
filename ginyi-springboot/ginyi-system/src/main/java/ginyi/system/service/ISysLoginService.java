package ginyi.system.service;

import ginyi.system.domain.model.dto.LoginDto;
import ginyi.system.domain.model.dto.RegisterDto;

public interface ISysLoginService {

    /**
     * 登录验证
     * @param loginDto
     * @return
     */
    public String login(LoginDto loginDto);

    public void register(RegisterDto registerDto);
}
