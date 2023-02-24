package com.projec.protest1.exception;

public class ApiErrorCodeException extends Exception {

    public ApiErrorCodeException() {
        super();
    }

    public ApiErrorCodeException(String s) {
        super(s);
    }

    public ApiErrorCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiErrorCodeException(Throwable cause) {
        super(cause);
    }

}
