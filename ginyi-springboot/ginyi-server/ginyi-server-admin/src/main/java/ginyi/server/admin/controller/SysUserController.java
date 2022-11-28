package ginyi.server.admin.controller;

import ginyi.common.result.CommonResult;
import ginyi.server.system.dto.params.SysUserParams;
import ginyi.server.system.dto.vo.SysUserVo;
import ginyi.server.system.mapper.SysUserMapper;
import ginyi.server.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Api(tags = "用户模块")
@RestController
@RequestMapping("/api/user")
@Slf4j
public class SysUserController {


    @Autowired
    private SysUserService userService;

    @ApiOperation("获取用户列表")
    @PostMapping("/getUserList")
    public CommonResult<List<SysUserVo>> getUserList(@RequestBody @Validated SysUserParams userParams){
        List<SysUserVo> userList = userService.selectUserList(userParams);
        return CommonResult.success(userList);
    }

}
