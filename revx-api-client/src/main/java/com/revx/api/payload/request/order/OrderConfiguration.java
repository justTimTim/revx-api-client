package com.revx.api.payload.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Configuration for the order. Must contain exactly one of 'limit' or 'market'.
 */
public record OrderConfiguration(@JsonProperty("limit") LimitOrderConfiguration limit,
                                 @JsonProperty("market") MarketOrderConfiguration market) {
}
