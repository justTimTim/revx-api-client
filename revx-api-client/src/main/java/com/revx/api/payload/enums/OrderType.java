package com.revx.api.payload.enums;

import lombok.Getter;

@Getter
public enum OrderType {
    MARKET("market"),
    LIMIT("limit");

    private final String value;

    OrderType(String value) {
        this.value = value;
    }

    public static OrderType fromValue(String value) {
        for (OrderType orderType : OrderType.values()) {
            if (orderType.value.equals(value)) {
                return orderType;
            }
        }
        throw new IllegalArgumentException("Unknown type: " + value);
    }
}
