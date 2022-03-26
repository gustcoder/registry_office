package br.com.docket.registryoffice.exceptions;

public class ApiCustomError {
    private final String message;
    private final Integer code;

    ApiCustomError(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
