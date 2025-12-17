package com.revx.api.exception;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiError {

    private final String message;
    private final String errorId;
    private final long timestamp;

    @JsonCreator
    public ApiError(@JsonProperty("message") String message,
                    @JsonProperty("error_id") String errorId,
                    @JsonProperty("timestamp") long timestamp) {
        this.message = message;
        this.errorId = errorId;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorId() {
        return errorId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "message='" + message + '\'' +
                ", errorId='" + errorId + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
