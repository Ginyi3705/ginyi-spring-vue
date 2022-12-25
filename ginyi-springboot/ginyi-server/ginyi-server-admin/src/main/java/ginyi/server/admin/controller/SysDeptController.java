package ginyi.server.admin.controller;

import ginyi.common.result.CommonResult;
import ginyi.system.domain.SysDept;
import ginyi.system.domain.model.dto.DeptDto;
import ginyi.system.domain.model.vo.BaseVo;
import ginyi.system.domain.model.vo.DeptVo;
import ginyi.system.service.ISysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "部门模块")
@RestController
@RequestMapping("/api/dept")
public class SysDeptController {

    @Resource
    private ISysDeptService deptService;

    @ApiOperation("部门列表")
    @PostMapping("/list")
    @PreAuthorize("@ss.hasPermission('system:dept:list')")
    public CommonResult<BaseVo<SysDept>> list(@RequestBody DeptDto deptDto,
                             @RequestParam(value = "page", required = false) Long page,
                             @RequestParam(value = "pageSize", required = false) Long pageSize) {
        BaseVo<SysDept> list = deptService.list(deptDto, page, pageSize);
        return CommonResult.success(list);
    }

    @ApiOperation("部门详情")
    @GetMapping("/getDeptByDeptId/{deptId}")
    @PreAuthorize("@ss.hasPermission('system:dept:edit')")
    public CommonResult<DeptVo> getDeptByDeptId(@PathVariable("deptId") Long deptId){
        DeptVo deptVo = deptService.getDeptByDeptId(deptId);
        return CommonResult.success(deptVo);
    }

}
