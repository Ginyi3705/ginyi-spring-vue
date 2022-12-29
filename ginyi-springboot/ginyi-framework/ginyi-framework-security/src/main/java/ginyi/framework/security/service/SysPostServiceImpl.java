package ginyi.framework.security.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import ginyi.common.constant.CacheConstants;
import ginyi.common.constant.MessageConstants;
import ginyi.common.exception.CommonException;
import ginyi.common.mysql.MyPage;
import ginyi.common.redis.cache.RedisCache;
import ginyi.common.result.StateCode;
import ginyi.common.utils.StringUtils;
import ginyi.system.domain.SysPost;
import ginyi.system.domain.model.dto.PostDto;
import ginyi.system.domain.model.vo.BaseVo;
import ginyi.system.domain.model.vo.PostVo;
import ginyi.system.mapper.SysPostMapper;
import ginyi.system.service.ISysPostService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysPostServiceImpl implements ISysPostService {

    @Resource
    private SysPostMapper postMapper;
    @Resource
    private RedisCache redisCache;

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

    /**
     * 获取岗位详情
     *
     * @param postId
     * @return
     */
    @Override
    public PostVo getPostByPostId(Long postId) {
        // 检查缓存中是否存在空id
        if (redisCache.hasKey(CacheConstants.POST_NOT_EXIST_KEY + postId)) {
            throw new CommonException(StateCode.ERROR_NOT_EXIST, postId + MessageConstants.POST_NOT_EXIST);
        }
        PostVo postVo = new PostVo();
        // 检查缓存中是否存在
        SysPost post = redisCache.getCacheObject(CacheConstants.POST_DETAILS_BY_POSTID_KEY + postId, SysPost.class);
        if (StringUtils.isNotNull(post)) {
            BeanUtils.copyProperties(post, postVo);
            return postVo;
        }
        LambdaQueryWrapper<SysPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysPost::getPostId, postId);
        post = postMapper.selectOne(queryWrapper);
        if (StringUtils.isNull(post)) {
            redisCache.setCacheObject(CacheConstants.POST_NOT_EXIST_KEY + postId, null);
            throw new CommonException(StateCode.ERROR_NOT_EXIST, postId + MessageConstants.POST_NOT_EXIST);
        }
        redisCache.setCacheObject(CacheConstants.POST_DETAILS_BY_POSTID_KEY + postId, post);
        BeanUtils.copyProperties(post, postVo);
        return postVo;
    }
}
