package ginyi.server.admin.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import ginyi.common.result.CommonResult;
import ginyi.system.domain.model.dto.RoleDto;
import ginyi.system.domain.model.vo.BaseVo;
import ginyi.system.domain.model.vo.RoleVo;
import ginyi.system.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "角色模块")
@RestController
@RequestMapping("/api/role")
public class SysRoleController {

    @Resource
    private ISysRoleService roleService;

    @ApiOperation("角色列表")
    @PostMapping("/list")
    @PreAuthorize("@ss.hasPermission('system:role:query')")
    @ApiOperationSupport(ignoreParameters = {
            "roleDto.updateBy",
            "roleDto.updateTime",
            "roleDto.createTime",
            "roleDto.params",
            "roleDto.sort",
            "roleDto.permissions",
            "roleDto.roleId",
    })
    public CommonResult<BaseVo<RoleVo>> list(@RequestBody @Validated RoleDto roleDto,
                                             @RequestParam(value = "page", required = false) Long page,
                                             @RequestParam(value = "pageSize", required = false) Long pageSize) {
        BaseVo<RoleVo> list = roleService.list(roleDto, page, pageSize);
        return CommonResult.success(list);
    }

    @ApiOperation("角色详情")
    @GetMapping("/getRoleByRoleId/{roleId}")
    @PreAuthorize("@ss.hasPermission('system:role:edit')")
    public CommonResult getRoleByRoleId(@PathVariable("roleId") Long roleId){
        RoleVo role = roleService.getRoleByRoleId(roleId);
        return CommonResult.success(role);
    }
}
