package ginyi.framework.security.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import ginyi.common.mysql.MyPage;
import ginyi.system.domain.SysPost;
import ginyi.system.domain.model.dto.PostDto;
import ginyi.system.domain.model.vo.BaseVo;
import ginyi.system.domain.model.vo.PostVo;
import ginyi.system.mapper.SysPostMapper;
import ginyi.system.service.ISysPostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysPostServiceImpl implements ISysPostService {

    @Resource
    private SysPostMapper postMapper;

    /**
     * 查询岗位列表
     *
     * @param postDto
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public BaseVo<PostVo> list(PostDto postDto, Long page, Long pageSize) {
        IPage<PostVo> list = postMapper.list(postDto, new MyPage(page, pageSize).getPage());
        BaseVo<PostVo> baseVo = new BaseVo<>();
        baseVo.setList(list.getRecords());
        baseVo.setCount((int) list.getTotal());
        return baseVo;
    }
}
