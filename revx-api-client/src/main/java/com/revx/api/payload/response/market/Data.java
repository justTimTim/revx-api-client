package com.revx.api.payload.response.market;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @param asks The list of asks (sell orders), sorted by price in descending order.
 * @param bids The list of bids (buy orders), sorted by price in descending order.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Data(@JsonProperty("asks") List<OrderBoorRecord> asks,
                   @JsonProperty("bids")List<OrderBoorRecord> bids) {
}
