package com.revx.api.payload.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Configuration for a market order.
 * Must contain exactly one of 'base_size' or 'quote_size'.
 */
public record MarketOrderConfiguration(@JsonProperty("base_size") String baseSize,
                                       @JsonProperty("quote_size") String quoteSize) {
}
