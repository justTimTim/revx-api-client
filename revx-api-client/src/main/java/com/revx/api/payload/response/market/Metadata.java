package com.revx.api.payload.response.market;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @param timestamp Timestamp when the order book snapshot was generated, returned in Unix epoch milliseconds.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Metadata(@JsonProperty("timestamp") long timestamp) {

}
