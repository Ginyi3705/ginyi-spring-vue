package ginyi.server.admin.controller;

import ginyi.common.result.CommonResult;
import ginyi.framework.core.annotation.Log;
import ginyi.framework.core.enums.BusinessType;
import ginyi.system.domain.model.dto.MenuDto;
import ginyi.system.domain.model.vo.MenuVo;
import ginyi.system.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "菜单模块")
@RestController
@RequestMapping("/api/menu")
public class SysMenuController {

    @Resource
    private ISysMenuService sysMenuService;

    @ApiOperation("菜单列表")
    @PostMapping("/getMenuList")
    public CommonResult<MenuVo> list() {
        MenuVo menuVo = sysMenuService.selectMenuList();
        return CommonResult.success(menuVo);
    }

    @ApiOperation("菜单列表 - admin")
    @PreAuthorize("@ss.hasPermission('system:dept:list:admin')")
    @Log(title = "菜单模块", businessType = BusinessType.OTHER)
    @PostMapping("/list")
    public CommonResult<MenuVo> list(@RequestBody @Validated MenuDto menuDto) {
        MenuVo menuVo = sysMenuService.selectMenuListByAdmin(menuDto);
        return CommonResult.success(menuVo);
    }
}
