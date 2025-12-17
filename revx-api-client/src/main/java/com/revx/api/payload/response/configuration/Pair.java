package com.revx.api.payload.response.configuration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Get configuration for all traded currency pairs. Represents a trading currency pair available on the exchange.
 * <p>Example: For BTC-USD pair:
 * <ul>
 *   <li>Base: BTC</li>
 *   <li>Quote: USD</li>
 * </ul>
 * </p>
 *
 * @param base              The base currency code.
 * @param quote             The quote currency code.
 * @param baseStep          The minimal step for changing the quantity in the base currency.
 * @param quoteStep         The minimal step for changing the amount in the quote currency.
 * @param minOrderSize      The minimal order quantity (in the base currency) accepted by the exchange.
 *                          This is the smallest amount of base currency that can be traded in a single order.
 * @param maxOrderSize      The maximal order quantity (in the base currency) accepted by the exchange.
 *                          This is the largest amount of base currency that can be traded in a single order.
 * @param minOrderSizeQuote The minimal order quantity (in the quote currency) accepted by the exchange.
 *                          This is the smallest amount of quote currency that can be traded in a single order.
 * @param status            The status of the currency pair. Indicates whether it is available for use.
 *                          Possible values: [active, inactive]
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Pair(
        @JsonProperty("base") String base,
        @JsonProperty("quote") String quote,
        @JsonProperty("base_step") String baseStep,
        @JsonProperty("quote_step") String quoteStep,
        @JsonProperty("min_order_size") String minOrderSize,
        @JsonProperty("max_order_size") String maxOrderSize,
        @JsonProperty("min_order_size_quote") String minOrderSizeQuote,
        @JsonProperty("status") String status
) {

    public String getSymbol() {
        return base + "-" + quote;
    }
}
