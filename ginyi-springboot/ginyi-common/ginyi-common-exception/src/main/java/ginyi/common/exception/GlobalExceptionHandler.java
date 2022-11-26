package ginyi.common.exception;

import cn.hutool.json.JSONUtil;
import ginyi.common.result.CommonResult;
import ginyi.common.result.StateCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局异常处理类
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 自定义业务 ===> 处理数据异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult BusinessEcxeption(BusinessException e) {
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
    public CommonResult MultipartException(MultipartException e) {
        if (e instanceof MaxUploadSizeExceededException) {
            return CommonResult.error(StateCode.ERROR_MULTIPART, "单文件大小不得大于5MB，总文件大小不得大于50MB");
        }
        log.info("文件上传业务异常", e);
        return CommonResult.error(StateCode.ERROR_MULTIPART, "文件上传业务异常");
    }


    /**
     * 自定义异常 ===> 方法参数错误异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<String> errorList = new ArrayList<>();
        // 从异常对象中获取 ObjectError 对象
        if (!e.getBindingResult().getAllErrors().isEmpty()) {
            for (ObjectError error : e.getBindingResult().getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
        }
        log.info(String.valueOf(JSONUtil.toJsonStr(CommonResult.error(StateCode.ERROR_PARAMS, errorList))));
        return CommonResult.error(StateCode.ERROR_PARAMS, errorList);
    }


    /**
     * 全局异常处理 ===> 处理其他所有未知异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult Exception(Exception e) {
        log.error("系统异常", e);
        return CommonResult.error(StateCode.ERROR_SYSTEM);
    }
}
