package com.revx.api.payload.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Request parameters for querying orders.
 *
 * @param symbols Filter active orders by specific currency pairs.
 *                Example: ["BTC-USD", "ETH-USD"]
 * @param orderStates Filter orders by one or more specific states.
 *                    Possible values: ["pending_new", "new", "partially_filled"]
 * @param side Filter by order direction (Buy or Sell).
 *             Possible values: ["buy", "sell"]
 * @param cursor Pagination cursor obtained from the metadata.next_cursor of the previous response.
 * @param limit Maximum number of records to return.
 *              Possible values: {@code >= 1} and {@code <= 100}
 *              Default value: 100
 */
public record ActiveOrderRequest(
        @JsonProperty("symbols") List<String> symbols,
        @JsonProperty("order_states") List<String> orderStates,
        @JsonProperty("side") String side,
        @JsonProperty("cursor") String cursor,
        @JsonProperty("limit") Integer limit
) {

    public static ActiveOrderRequest forSymbol(String symbol) {
        return new ActiveOrderRequest(
                List.of(symbol),
                null, null, null, null
        );
    }

    public static ActiveOrderRequest forSymbols(List<String> symbols) {
        return new ActiveOrderRequest(
                symbols,
                null, null, null, null
        );
    }

    public static ActiveOrderRequest activeBuyOrders(List<String> symbols) {
        return new ActiveOrderRequest(
                symbols,
                List.of("pending_new", "new", "partially_filled"),
                "buy",
                null,
                null
        );
    }
}
