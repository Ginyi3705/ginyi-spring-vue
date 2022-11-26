package ginyi.common.config.aop;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import ginyi.common.exception.BusinessException;
import ginyi.common.result.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.fusesource.jansi.Ansi;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * aop
 * 对进入 controller 的所有方法进行增强，获取请求 and 响应信息
 */
@Aspect
@Component
@Slf4j
public class WebLogAspectConfig {

    /**
     * 以 controller 包下定义的所有请求为切入点
     */
    @Pointcut("execution(public * com.example.reggie.controller..*.*(..))")
    public void pointCut() {
    }

    /**
     * 在切点之前织入
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String ip = ServletUtil.getClientIP(request);
        Map<String, String> getParams = ServletUtil.getParamMap(request);
        Object[] postParams = joinPoint.getArgs();

        log.info("<<< request and responese info >>>");
        log.info("URL            : {}", url);
        log.info("HTTP Method    : {}", Ansi.ansi().eraseScreen().fg(method.equals("POST") ? Ansi.Color.RED : Ansi.Color.GREEN).a(method).reset().toString());
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.info("IP             : {}", ip);
        log.info("Request Args   : {}", JSONUtil.toJsonStr(method.equals("POST") ? postParams : getParams));

    }

    /**
     * 在切点之后织入
     */
    @AfterReturning(pointcut = "pointCut()", returning = "CommonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object CommonResult) throws Throwable {
        log.info("Response Args  : {}", JSONUtil.toJsonStr(CommonResult));
        log.info("End...\n");
    }

    /**
     * 在上面定义的切面方法执行抛异常时，执行该方法
     *
     * @param joinPoint
     * @param exception
     */
    @AfterThrowing(pointcut = "pointCut()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Exception exception) {
        if (exception instanceof BusinessException) {
            BusinessException e = (BusinessException) exception;
            log.info("Response Args  : {}", JSONUtil.toJsonStr(CommonResult.error(e.getState(), e.getData())));
            log.info("End...\n");
        } else {
            log.info("aop后置增强未知异常=====>", exception);
        }
    }
}