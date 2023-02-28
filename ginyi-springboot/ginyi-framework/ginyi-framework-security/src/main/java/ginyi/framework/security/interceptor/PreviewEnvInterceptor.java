package ginyi.framework.security.interceptor;

import ginyi.common.constant.CacheConstants;
import ginyi.common.constant.CommonMessageConstants;
import ginyi.common.exception.CommonException;
import ginyi.common.redis.cache.RedisCache;
import ginyi.common.result.StateCode;
import ginyi.common.utils.ServletUtils;
import ginyi.common.utils.StringUtils;
import ginyi.common.utils.ip.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class PreviewEnvInterceptor implements HandlerInterceptor {

    @Resource
    private RedisCache redisCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        boolean flag;
//        List<String> list = Arrays.asList("add", "update", "edit", "delete", "remove", "register");
//        for (String url : list) {
//            flag = request.getRequestURI().contains(url);
//            if (flag) {
//                throw new CommonException(StateCode.ERROR_LIMITED, CommonMessageConstants.SYS_PREVIEW_ENV);
//            }
//        }
        String key = CacheConstants.API_REQUEST + IpUtils.getIpAddr(ServletUtils.getRequest()) + request.getRequestURI();
        Integer count = redisCache.getCacheObject(key, Integer.class);
        long expire = redisCache.getExpire(key);
        if (StringUtils.isNull(count)) {
            redisCache.setCacheObject(key, 0, 300, TimeUnit.SECONDS);
        } else {
            // 请求大于100次
            if (count > 100) {
                redisCache.setCacheObject(key, count + 1, 300, TimeUnit.SECONDS);
                throw new CommonException(StateCode.ERROR_LIMITED, CommonMessageConstants.SYS_BED_REQUEST);
            } else {
                redisCache.setCacheObject(key, count + 1, (int) expire, TimeUnit.SECONDS);
            }
        }
        return true;
    }
}
