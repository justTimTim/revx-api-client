package com.revx.api.exception;


public class ApiInitializationException extends RuntimeException{

    public ApiInitializationException(String msg) {
        super(msg);
    }

    public ApiInitializationException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    @Override
    public String toString() {
        return "ApiInitializationException{" +
                "message=" + getMessage() +
    "}";
    }
}
