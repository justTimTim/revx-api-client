package com.revx.api.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ApiRevxException extends RuntimeException {

    private final String errorId;
    private final long timestamp;
    @Setter
    private int httpStatus;


    public ApiRevxException(String message, String errorId, long timestamp) {
        super(message);
        this.errorId = errorId;
        this.timestamp = timestamp;
    }


    public ApiRevxException(String message, String errorId, long timestamp, int httpStatus) {
        super(message);
        this.errorId = errorId;
        this.timestamp = timestamp;
        this.httpStatus = httpStatus;
    }

    public ApiRevxException(String message, String errorId, long timestamp, Throwable throwable) {
        super(message, throwable);
        this.errorId = errorId;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ApiRevxException{" +
                "message=" + getMessage() +
                ", errorId='" + errorId + '\'' +
                ", timestamp=" + timestamp +
                ", httpStatus=" + httpStatus +
                '}';
    }
}
