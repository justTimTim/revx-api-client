package com.revx.api.payload.enums;

public enum Status {
    PENDING_NEW("pending_new"),
    NEW("new"),
    PARTIALLY_FILLED("partially_filled"),
    FILLED("filled"),
    CANCELLED("cancelled"),
    REJECTED("rejected"),
    REPLACED("replaced");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Status fromValue(String value) {
        for (Status status : Status.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + value);
    }
}
