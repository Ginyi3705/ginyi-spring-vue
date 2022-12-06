package ginyi.common.exception.handler;

import cn.hutool.json.JSONUtil;
import ginyi.common.constant.MessageConstants;
import ginyi.common.exception.BusinessException;
import ginyi.common.exception.UserPasswordNotMatchException;
import ginyi.common.exception.UserPasswordRetryLimitExceedException;
import ginyi.common.result.CommonResult;
import ginyi.common.result.StateCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 全局异常处理类
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 业务异常 ===> 处理数据异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult BusinessEcxeptionHandler(BusinessException e) {
        if (e.getState() == StateCode.ERROR_SYSTEM) {
            return CommonResult.error(e.getState(), MessageConstants.SYS_ERROR);
        }
        return CommonResult.error(e.getState(), e.getData());
    }

    /**
     * 参数校验 ===> 方法参数错误异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<String> errorList = new ArrayList<>();
        // 从异常对象中获取 ObjectError 对象
        if (!e.getBindingResult().getAllErrors().isEmpty()) {
            for (ObjectError error : e.getBindingResult().getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("errorMessageList", errorList);
        log.info(String.valueOf(JSONUtil.toJsonStr(CommonResult.error(StateCode.ERROR_PARAMS, errorList))));
        return CommonResult.error(StateCode.ERROR_PARAMS, map);
    }

    /**
     * 参数校验 ===>>> 请求参数不合法
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CommonResult HttpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        return CommonResult.error(StateCode.ERROR_REQUEST_PARAMS, MessageConstants.SYS_REQUEST_ILLEGAL);
    }

    /**
     * 登录认证异常 ===> 用户名密码不匹配
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(UserPasswordNotMatchException.class)
    public CommonResult UserPasswordNotMatchExceptionHandler(UserPasswordNotMatchException e) {
        return CommonResult.error(StateCode.ERROR_UNAUTHENTICATION, MessageConstants.USER_PASSWORD_NOT_MATCH);
    }

    /**
     * 登录认证异常 ===> 用户登录失败次数超最大限制异常
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(UserPasswordRetryLimitExceedException.class)
    public CommonResult UserPasswordRetryLimitExceedExceptionHandler(UserPasswordRetryLimitExceedException e) {
        return CommonResult.error(e.getState(), e.getData());
    }


    /**
     * 捕获上传文件异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult MultipartExceptionHandler(MultipartException e) {
        if (e instanceof MaxUploadSizeExceededException) {
            return CommonResult.error(StateCode.ERROR_MULTIPART, "单文件大小不得大于5MB，总文件大小不得大于50MB");
        }
        log.info("文件上传业务异常", e);
        return CommonResult.error(StateCode.ERROR_MULTIPART, "文件上传业务异常");
    }


    /**
     * 全局异常处理 ===> 处理其他所有未知异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult ExceptionHandler(Exception e) {
        log.error("系统异常", e);
        return CommonResult.error(StateCode.ERROR_SYSTEM, MessageConstants.SYS_ERROR);
    }
}
