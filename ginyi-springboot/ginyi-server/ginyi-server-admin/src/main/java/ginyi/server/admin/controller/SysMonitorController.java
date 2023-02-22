package ginyi.server.admin.controller;

import ginyi.common.result.CommonResult;
import ginyi.system.domain.model.vo.BaseVo;
import ginyi.system.domain.model.vo.SessionUserVo;
import ginyi.system.service.ISysMonitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "在线用户")
@RestController
@RequestMapping("/api/online")
public class SysMonitorController {

    @Resource
    private ISysMonitorService monitorService;

    @ApiOperation("在线用户")
    @GetMapping("/getOnlineUserList")
    public CommonResult<BaseVo<SessionUserVo>> getOnlineUserList(@RequestParam(value = "page", required = false) Long page,
                                                                 @RequestParam(value = "pageSize", required = false) Long pageSize) {
        BaseVo<SessionUserVo> list = monitorService.getOnlineUserList(page, pageSize);
        return CommonResult.success(list);
    }

}
