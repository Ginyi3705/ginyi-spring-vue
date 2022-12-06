package ginyi.server.admin.controller;

import ginyi.common.result.CommonResult;
import ginyi.system.domain.SysMenu;
import ginyi.system.domain.model.dto.MenuDto;
import ginyi.system.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "菜单模块")
@RestController
@RequestMapping("/api/menu")
public class SysMenuController {

    @Resource
    private ISysMenuService sysMenuService;

    @ApiOperation("菜单列表")
    @PostMapping("/list")
    public CommonResult<List<SysMenu>> list(@RequestBody @Validated MenuDto menuDto){
        List<SysMenu> menuList = sysMenuService.selectMenuList(menuDto);
        return CommonResult.success(menuList);
    }
}
