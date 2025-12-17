package com.revx.api.payload.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Configuration for a limit order.
 * Must contain exactly one of 'base_size' or 'quote_size'.
 */

/**
 *
 * @param baseSize Amount of base currency.
 * @param quoteSize Amount of quote currency.
 * @param price The limit price.
 * @param executionInstructions List of execution instructions for the order. Default value: allow_taker
 *                              Possible values: [allow_taker, post_only]
 */
public record LimitOrderConfiguration(@JsonProperty("base_size") String baseSize,
                                      @JsonProperty("quote_size") String quoteSize,
                                      @JsonProperty("price") String price,
                                      @JsonProperty("execution_instructions") List<String> executionInstructions) {
}
