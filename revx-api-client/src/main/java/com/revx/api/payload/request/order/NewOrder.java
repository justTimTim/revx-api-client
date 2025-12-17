package com.revx.api.payload.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.revx.api.payload.enums.Side;


/**
 * Represents an order creation request.
 * <p>
 * This record contains all the necessary information to create a new order
 * on the exchange, including the order type (limit or market), size, and
 * execution instructions.
 * </p>
 *
 * @param clientOrderId      Unique identifier for idempotency (UUID format).
 *                           This ensures that duplicate orders are not created
 *                           if the same request is sent multiple times.
 * @param symbol             The trading pair symbol (e.g., "BTC-USD").
 * @param side               The direction of the order - either "buy" or "sell".
 * @param orderConfiguration Configuration for the order. Must contain exactly
 *                           one of 'limit' or 'market' sub-configurations.
 */
public record NewOrder(@JsonProperty("client_order_id") String clientOrderId,
                       @JsonProperty("symbol") String symbol,
                       @JsonProperty("side") String side,
                       @JsonProperty("order_configuration") OrderConfiguration orderConfiguration) {

    public static NewOrder limitOrder(String clientOrderId,
                                      String symbol,
                                      Side side,
                                      LimitOrderConfiguration limitConfig) {
        return new NewOrder(clientOrderId, symbol, side.getValue(),
                new OrderConfiguration(limitConfig, null)
        );
    }

    public static NewOrder marketOrder(String clientOrderId,
                                      String symbol,
                                      Side side,
                                      MarketOrderConfiguration marketConfig) {
        return new NewOrder(clientOrderId, symbol, side.getValue(),
                new OrderConfiguration(null, marketConfig)
        );
    }
}
