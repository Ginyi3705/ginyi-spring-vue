package ginyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ginyi.system.domain.SysPost;
import ginyi.system.domain.model.dto.PostDto;
import ginyi.system.domain.model.vo.PostVo;
import org.apache.ibatis.annotations.Param;

/**
 * 岗位
 */
public interface SysPostMapper extends BaseMapper<SysPost> {

    /**
     * 查询岗位列表
     * @param postDto
     * @param page
     * @return
     */
    public IPage<PostVo> list(@Param("postDto") PostDto postDto, Page page);
}
