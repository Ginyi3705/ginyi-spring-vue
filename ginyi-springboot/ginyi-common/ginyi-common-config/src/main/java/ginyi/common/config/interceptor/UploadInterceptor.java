package ginyi.common.config.interceptor;

import com.alibaba.fastjson.JSON;
import ginyi.common.config.request.RequestWrapper;
import ginyi.common.exception.BusinessException;
import ginyi.common.result.StateCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class UploadInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${spring.redis.token_expire_time}")
    private Integer tokenExpireTime;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 权限校验
        String token = new RequestWrapper(request).getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            throw new BusinessException(StateCode.ERROR_UNAUTHENTICATION, "token令牌不存在");
        }

        // todo 这个 object 记得修改为 user
        Object user = JSON.parseObject(String.valueOf(redisTemplate.opsForValue().get("token:" + token)), Object.class);
        if (user == null) {
            throw new BusinessException(StateCode.ERROR_TIMEOUT_TOKEN, "token已过期");
        }

        // 请求成功，更新token
        redisTemplate.opsForValue().set("token:" + token, user, tokenExpireTime, TimeUnit.SECONDS);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
