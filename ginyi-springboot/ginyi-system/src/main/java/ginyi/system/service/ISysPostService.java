package ginyi.system.service;

import ginyi.system.domain.SysPost;
import ginyi.system.domain.model.dto.PostDto;
import ginyi.system.domain.model.vo.BaseVo;
import ginyi.system.domain.model.vo.PostVo;

public interface ISysPostService {

    /**
     * 查询岗位列表
     * @param postDto
     * @param page
     * @param pageSize
     * @return
     */
    public BaseVo<PostVo> list(PostDto postDto, Long page, Long pageSize);
}
