package br.com.docket.registryoffice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ApiCustomException extends Exception{
    public ApiCustomException(String message) {
        super(message);
    }

    @ExceptionHandler(ApiCustomException.class)
    public ApiCustomError handleMyCustomException(Exception ex) {
        return new ApiCustomError("Oops! " + ex.getMessage(), HttpStatus.BAD_REQUEST.value());
    }
}
