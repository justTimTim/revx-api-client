package com.revx.api.payload.enums;

public enum Type {
    MARKET("market"),
    LIMIT("limit");

    private final String value;

    Type(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Type fromValue(String value) {
        for (Type type : Type.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown type: " + value);
    }
}
