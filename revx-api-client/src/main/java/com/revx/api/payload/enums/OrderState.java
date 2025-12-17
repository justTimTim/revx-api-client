package com.revx.api.payload.enums;

import lombok.Getter;

/**
 * Enum for order states.
 */
@Getter
public enum OrderState {
    PENDING_NEW("pending_new"),
    NEW("new"),
    PARTIALLY_FILLED("partially_filled"),
    FILLED("filled"),
    CANCELLED("cancelled"),
    REJECTED("rejected"),
    REPLACED("replaced");

    private final String value;

    OrderState(String value) {
        this.value = value;
    }

    public static OrderState fromValue(String value) {
        for (OrderState state : OrderState.values()) {
            if (state.value.equals(value)) {
                return state;
            }
        }
        throw new IllegalArgumentException("Unknown order state: " + value);
    }
}