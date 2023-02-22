package ginyi.framework.security.service;

import cn.hutool.json.JSONUtil;
import ginyi.common.constant.CacheConstants;
import ginyi.common.mysql.MyPage;
import ginyi.common.redis.cache.RedisCache;
import ginyi.system.domain.LoginUser;
import ginyi.system.domain.model.vo.BaseVo;
import ginyi.system.domain.model.vo.SessionUserVo;
import ginyi.system.service.ISysMonitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class MonitorServiceImpl implements ISysMonitorService {

    @Resource
    private RedisCache redisCache;

    @Override
    public BaseVo<SessionUserVo> getOnlineUserList(Long page, Long pageSize) {
        MyPage IPage = new MyPage(page, pageSize);
        long skipCount = IPage.getPageSize() * (IPage.getPageNum() - 1);

        BaseVo<SessionUserVo> baseVo = new BaseVo<>();
        List<SessionUserVo> list = new LinkedList<>();
        Set<String> keys = redisCache.getKeys(CacheConstants.LOGIN_TOKEN_KEY + "*");
        LoginUser loginUser;
        for (String key : keys) {
            SessionUserVo sessionUserVo = new SessionUserVo();
            loginUser = redisCache.getCacheObject(key, LoginUser.class);
            BeanUtils.copyProperties(loginUser, sessionUserVo);
            list.add(sessionUserVo);
        }

        long toIndex = Math.min(skipCount + IPage.getPageSize(), list.size());

        if(skipCount > list.size()){
            list = new LinkedList<>();
        }else {
            list = list.subList((int) skipCount, (int) toIndex);
        }

        baseVo.setList(list);
        baseVo.setCount(list.size());
        return baseVo;
    }
}
