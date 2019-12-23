package com.yewei.sample.common.handlers;

import com.yewei.sample.common.error.BusinessException;
import com.yewei.sample.common.error.GeneralCode;
import com.yewei.sample.common.models.APIResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * 异常统一处理程序
 * 
 * @author wang
 *
 */
@Log4j2
@RestControllerAdvice
public class HandlerAdvice {
    @Resource
    private MessageHelper messageHelper;

    private APIResponse<?> getValid(BindingResult bindingResult) {
        Map<String, Object> data = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            data.put(error.getField(), error.getDefaultMessage());
        }
        return APIResponse.getErrorJsonResult(APIResponse.Type.VALID, GeneralCode.SYS_VALID.getCode(), data);
    }

    @ExceptionHandler(value = BindException.class)
    public APIResponse<?> exception(HttpServletResponse response, BindException exception, HandlerMethod handler)
            throws IOException {
        log.warn("system warn:", exception);
        return this.getValid(exception);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public APIResponse<?> exception(HttpServletResponse response, MethodArgumentNotValidException exception,
            HandlerMethod handler) throws IOException {
        log.warn("system warn:", exception);
        return this.getValid(exception.getBindingResult());
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public APIResponse<?> exception(IllegalArgumentException exception, HandlerMethod handler) throws IOException {
        log.warn("system warn:", exception);
        return APIResponse.getErrorJsonResult(APIResponse.Type.GENERAL, GeneralCode.SYS_ERROR.getCode(),
                exception.getMessage());
    }

    @ExceptionHandler(value = BusinessException.class)
    public APIResponse<?> exception(BusinessException exception, HandlerMethod handler, HttpServletRequest request)
            throws IOException {
        log.warn("system warn, BusinessException: {}", this.parseExceptionAsString(exception));
        String code =  exception.getBizCode();
        String message = exception.getBizMessage();
        if(exception.getErrorCode() != null){
            code = exception.getErrorCode().getCode();
            message = messageHelper.getMessage(exception.getErrorCode(), exception.getParams());
        }
        return APIResponse.getErrorJsonResult(APIResponse.Type.GENERAL,code ,message, exception.getParams(),
                exception.getSubData());
    }

    @ExceptionHandler({Error.class, Exception.class, Throwable.class, RuntimeException.class})
    public APIResponse<?> exception(HttpServletResponse response, Throwable exception, HttpServletRequest request)
            throws IOException {
        log.error("system error:", exception);
        return APIResponse.getErrorJsonResult(APIResponse.Type.SYS, GeneralCode.SYS_ERROR.getCode(),
                messageHelper.getMessage(GeneralCode.SYS_ERROR));
    }

    private String parseExceptionAsString(BusinessException e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.getClass().getName());
        sb.append(":").append(e.getMessage());
        sb.append(":");
        sb.append(e.toString());
        sb.append("\n");
        StackTraceElement[] stes = e.getStackTrace();
        if (stes != null) {
            for (StackTraceElement ste : stes) {
                sb.append("\tat ");
                sb.append(ste.toString());
                sb.append("\n");
            }
        }
        return sb.toString();
    }

}
