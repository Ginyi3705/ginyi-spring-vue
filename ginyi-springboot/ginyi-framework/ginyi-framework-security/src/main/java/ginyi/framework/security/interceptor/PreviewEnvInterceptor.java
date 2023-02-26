package ginyi.framework.security.interceptor;

import ginyi.common.constant.CommonMessageConstants;
import ginyi.common.exception.CommonException;
import ginyi.common.result.StateCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class PreviewEnvInterceptor implements HandlerInterceptor {
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
        return true;
    }
}
