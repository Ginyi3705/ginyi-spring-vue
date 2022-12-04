package ginyi.server.admin.controller;

import ginyi.common.result.CommonResult;
import ginyi.common.utils.Constants;
import ginyi.system.domain.model.dto.LoginDto;
import ginyi.system.domain.model.dto.RegisterDto;
import ginyi.system.service.ISysLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Api(tags = "登录模块")
@Slf4j
public class SysLoginController {

    @Autowired
    private ISysLoginService loginService;

    /**
     * 用户登录
     *
     * @param loginDto 登录信息
     * @return 结果
     */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public CommonResult login(@RequestBody @Validated LoginDto loginDto) {
        String token = loginService.login(loginDto);
        Map<String, String> map = new HashMap<>();
        map.put(Constants.TOKEN, token);
        return CommonResult.success(map);
    }

    /**
     * 用户注册
     *
     * @param registerDto
     * @return 结果
     */
    @ApiOperation("用户注册")
    @PostMapping("/register")
    public CommonResult register(@RequestBody @Validated RegisterDto registerDto) {
        loginService.register(registerDto);
        return CommonResult.success();
    }
}
