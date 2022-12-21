package ginyi.server.admin.controller;

import ginyi.common.result.CommonResult;
import ginyi.common.swagger.AddGroup;
import ginyi.framework.core.annotation.Log;
import ginyi.framework.core.enums.BusinessType;
import ginyi.system.domain.model.dto.MenuDto;
import ginyi.system.domain.model.vo.MenuVo;
import ginyi.system.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "菜单模块")
@RestController
@RequestMapping("/api/menu")
public class SysMenuController {

    @Resource
    private ISysMenuService sysMenuService;

    @ApiOperation("路由菜单列表")
    @GetMapping("/getMenuList")
    public CommonResult<MenuVo> list() {
        MenuVo menuVo = sysMenuService.selectMenuList();
        return CommonResult.success(menuVo);
    }

    @ApiOperation("菜单列表")
    @PreAuthorize("@ss.hasPermission('system:menu:list')")
    @Log(title = "菜单模块", businessType = BusinessType.OTHER)
    @PostMapping("/list")
    public CommonResult<MenuVo> list(@RequestBody @Validated MenuDto menuDto) {
        MenuVo menuVo = sysMenuService.selectMenuListByAdmin(menuDto);
        return CommonResult.success(menuVo);
    }

    @ApiOperation("添加菜单")
    @PostMapping("/add")
    public CommonResult add(@RequestBody @Validated({AddGroup.class}) MenuDto menuDto){
        sysMenuService.addMenu(menuDto);
        return CommonResult.success();
    }
}
