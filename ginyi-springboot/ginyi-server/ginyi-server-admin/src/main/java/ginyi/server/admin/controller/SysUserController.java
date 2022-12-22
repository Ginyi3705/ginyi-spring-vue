package ginyi.server.admin.controller;

import cn.hutool.core.lang.Validator;
import ginyi.common.annotation.Log;
import ginyi.common.enums.BusinessType;
import ginyi.common.result.CommonResult;
import ginyi.common.swagger.AddGroup;
import ginyi.framework.security.service.SysUserServiceImpl;
import ginyi.system.domain.model.dto.UserDto;
import ginyi.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "用户模块")
@RestController
@Slf4j
@RequestMapping("/api/user")
public class SysUserController {

    @Resource
    private ISysUserService userService;

    @ApiOperation("添加用户")
    @PostMapping("/add")
    @PreAuthorize("@ss.hasPermission('system:user:add')")
    @Log(title = "用户模块", businessType = BusinessType.INSERT)
    public CommonResult add(@RequestBody @Validated(AddGroup.class) UserDto userDto){
        userService.addUser(userDto);
        return CommonResult.success();
    }


}
