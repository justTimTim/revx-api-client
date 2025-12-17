package com.revx.api.payload.enums;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

/**
 * Execution instructions for the order.
 */
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public enum ExecutionInstruction {
  ALLOW_TAKER("allow_taker"),
  POST_ONLY("post_only");

  private final String value;

  ExecutionInstruction(String value) {
    this.value = value;
  }

    public static ExecutionInstruction fromValue(String value) {
    for (ExecutionInstruction side : ExecutionInstruction.values()) {
      if (side.value.equals(value)) {
        return side;
      }
    }
    throw new IllegalArgumentException("Unknown side value: " + value);
  }
}