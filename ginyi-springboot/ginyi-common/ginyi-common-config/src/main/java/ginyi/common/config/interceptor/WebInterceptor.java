package ginyi.common.config.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import ginyi.common.config.request.RequestWrapper;
import ginyi.common.exception.BusinessException;
import ginyi.common.result.StateCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@Slf4j
@Component
public class WebInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${spring.redis.token_expire_time}")
    private Integer tokenExpireTime;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 权限校验
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            throw new BusinessException(StateCode.ERROR_UNAUTHENTICATION, "token令牌不存在");
        }

        // 获取key值
        // todo 这个 object 记得修改为 user 类
        Object user = JSON.parseObject(String.valueOf(redisTemplate.opsForValue().get("token:" + token)), Object.class);
        if (user == null) {
            throw new BusinessException(StateCode.ERROR_TIMEOUT_TOKEN, "token已过期");
        }

        if ("POST".equalsIgnoreCase(request.getMethod()) && isJson(request)) {

            if (!isJson(request)) {
                throw new BusinessException(StateCode.ERROR_PARAMS_SERVICE, "请将参数数据放至请求体当中");
            }

            // 获取json字符串
            String jsonParam = new RequestWrapper(request).getBodyString();
            JSONObject jsonObject = null;
            try {
                jsonObject = JSONObject.parseObject(jsonParam);
            }catch (JSONException e){
                throw new BusinessException(StateCode.ERROR_SYSTEM, "请求体Json格式有误");
            }

            String time = jsonObject.getString("time");
            // 时间校验
            if (StringUtils.isEmpty(time)) {
                throw new BusinessException(StateCode.ERROR_PARAMS_SERVICE, "time不存在");
            }

//            // 请求超时(大于5秒)校验
//            if (time.length() != 13) {
//                throw new BusinessException(StateCode.ERROR_PARAMS_SERVICE, "请发送13位数的时间戳");
//            }
//            long newTime = System.currentTimeMillis() - Long.parseLong(time);
//            if (newTime > 50000) {
//                throw new BusinessException(StateCode.ERROR_TIMEOUT_REQUEST, "请求超时");
//            }

        }

        // 请求成功，更新token
        redisTemplate.opsForValue().set("token:" + token, user, tokenExpireTime, TimeUnit.SECONDS);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    /**
     * 判断本次请求的数据类型是否为json
     *
     * @param request request
     * @return boolean
     */
    private boolean isJson(HttpServletRequest request) {
        if (request.getContentType() != null) {
            return request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE) ||
                    request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_UTF8_VALUE);
        }
        return false;
    }

}

