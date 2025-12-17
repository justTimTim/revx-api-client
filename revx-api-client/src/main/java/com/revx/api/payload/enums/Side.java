package com.revx.api.payload.enums;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

/**
 * The direction of the order.
 */
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public enum Side {
  BUY("buy"),
  SELL("sell");

  private final String value;

  Side(String value) {
    this.value = value;
  }

    public static Side fromValue(String value) {
    for (Side side : Side.values()) {
      if (side.value.equals(value)) {
        return side;
      }
    }
    throw new IllegalArgumentException("Unknown side value: " + value);
  }
}