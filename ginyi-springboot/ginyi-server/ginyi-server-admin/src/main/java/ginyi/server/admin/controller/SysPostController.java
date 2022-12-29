package ginyi.server.admin.controller;

import ginyi.common.result.CommonResult;
import ginyi.system.domain.SysPost;
import ginyi.system.domain.model.dto.PostDto;
import ginyi.system.domain.model.vo.BaseVo;
import ginyi.system.domain.model.vo.DeptVo;
import ginyi.system.domain.model.vo.PostVo;
import ginyi.system.service.ISysPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Api(tags = "岗位模块")
@RestController
@RequestMapping("/api/post")
public class SysPostController {

    @Resource
    private ISysPostService postService;

    @ApiOperation("岗位列表")
    @PostMapping("/list")
    @PreAuthorize("@ss.hasPermission('system:post:list')")
    public CommonResult<BaseVo<PostVo>> list(@RequestBody PostDto postDto,
                             @RequestParam(value = "page", required = false) Long page,
                             @RequestParam(value = "pageSize", required = false) Long pageSize){
        BaseVo<PostVo> list = postService.list(postDto, page, pageSize);
        return CommonResult.success(list);
    }

    @ApiOperation("岗位详情")
    @GetMapping("/getPostById/{postId}")
    @PreAuthorize("@ss.hasPermission('system:post:edit')")
    public CommonResult<PostVo> getPostByDeptId(@PathVariable("postId") Long postId) {
        PostVo postVo = postService.getPostByPostId(postId);
        return CommonResult.success(postVo);
    }
}
