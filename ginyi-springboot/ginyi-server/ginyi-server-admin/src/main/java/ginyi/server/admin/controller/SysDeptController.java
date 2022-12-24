package ginyi.server.admin.controller;

import ginyi.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "部门模块")
@RestController
@RequestMapping("/api/dept")
public class SysDeptController {

    @ApiOperation("部门列表")
    @PostMapping("/list")
    public CommonResult list() {
        return CommonResult.success();
    }

}
