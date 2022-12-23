package ginyi.server.admin.controller;

import cn.hutool.core.lang.Validator;
import ginyi.common.annotation.Log;
import ginyi.common.enums.BusinessType;
import ginyi.common.result.CommonResult;
import ginyi.common.swagger.AddGroup;
import ginyi.common.swagger.UpdateGroup;
import ginyi.framework.security.service.SysUserServiceImpl;
import ginyi.system.domain.model.dto.UserDto;
import ginyi.system.domain.model.vo.UserVo;
import ginyi.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("更新用户")
    @PostMapping("/update")
    @PreAuthorize("@ss.hasPermission('system:user:edit')")
    @Log(title = "用户模块", businessType = BusinessType.UPDATE)
    public CommonResult update(@RequestBody @Validated(UpdateGroup.class) UserDto userDto){
        userService.updateUser(userDto);
        return CommonResult.success();
    }

    @ApiOperation("用户详情")
    @GetMapping("/getUserByUserId/{userId}")
    @PreAuthorize("@ss.hasPermission('system:user:edit')")
    public CommonResult<UserVo> getUserByUserId(@PathVariable("userId") String userId){
        UserVo user = userService.getUserByUserId(userId);
        return CommonResult.success(user);
    }


}
