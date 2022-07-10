package com.example.wisdombooks.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R<Object> handleException(HttpMessageNotReadableException e) {
        log.error("", e);
        R<Object> r = new R<>();
        r.setCode(-1);
        r.setMsg("请求体不能为空");
        return r;
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Object> handleException(MethodArgumentNotValidException e) {
        String err = e.getBindingResult().getFieldError() != null ? e.getBindingResult().getFieldError().getDefaultMessage() : e.getMessage();
        log.error("", e);
        R<Object> r = new R<>();
        r.setCode(-1);
        r.setMsg(err);
        return r;
    }
}
