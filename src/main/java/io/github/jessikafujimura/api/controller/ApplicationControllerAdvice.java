package io.github.jessikafujimura.api.controller;

import io.github.jessikafujimura.api.ApiErrors;
import io.github.jessikafujimura.exception.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException e){
        String messageError = e.getMessage();
        return new ApiErrors(messageError);
    }
}
