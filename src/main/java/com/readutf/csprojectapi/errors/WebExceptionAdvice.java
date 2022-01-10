package com.readutf.csprojectapi.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class WebExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(WebException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String webException(WebException exception) {
        return exception.getMessage();
    }

}
