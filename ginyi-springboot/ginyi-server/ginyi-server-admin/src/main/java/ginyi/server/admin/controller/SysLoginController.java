package ginyi.server.admin.controller;

import ginyi.common.result.CommonResult;
import ginyi.common.utils.constants.Constants;
import ginyi.system.domain.model.LoginBody;
import ginyi.system.service.ISysLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/user")
@Api(tags = "登录模块")
public class SysLoginController {

    @Autowired
    private ISysLoginService loginService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @ApiOperation("登录")
    @PostMapping("/login")
    public CommonResult login(@RequestBody LoginBody loginBody) {
        // 生成令牌
        String token = loginService.login(
                loginBody.getUsername(),
                loginBody.getPassword(),
                loginBody.getCode());
        HashMap<String, String> map = new HashMap<>();
        map.put(Constants.TOKEN, token);
        return CommonResult.success(map);
    }
}
