package com.revx.api.payload.request.order;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderRequestValidator {

    public static void validate(NewOrder request) {
        if (request == null) {
            throw new IllegalArgumentException("OrderRequest cannot be null");
        }

        // Validate side
        String side = request.side();
        if (!"buy".equals(side) && !"sell".equals(side)) {
            throw new IllegalArgumentException("side must be 'buy' or 'sell'");
        }

        // Validate order configuration
        OrderConfiguration config = request.orderConfiguration();
        boolean hasLimit = config.limit() != null;
        boolean hasMarket = config.market() != null;

        if (!hasLimit && !hasMarket) {
            throw new IllegalArgumentException("order_configuration must contain either 'limit' or 'market'");
        }
        if (hasLimit && hasMarket) {
            throw new IllegalArgumentException("order_configuration cannot contain both 'limit' and 'market'");
        }

        // Validate limit order
        if (hasLimit) {
            LimitOrderConfiguration limit = config.limit();
            boolean hasBaseSize = limit.baseSize() != null && !limit.baseSize().isBlank();
            boolean hasQuoteSize = limit.quoteSize() != null && !limit.quoteSize().isBlank();

            if (!hasBaseSize && !hasQuoteSize) {
                throw new IllegalArgumentException("limit configuration must contain either 'base_size' or 'quote_size'");
            }
            if (hasBaseSize && hasQuoteSize) {
                throw new IllegalArgumentException("limit configuration cannot contain both 'base_size' and 'quote_size'");
            }
            if (limit.price() == null || limit.price().isBlank()) {
                throw new IllegalArgumentException("price is required for limit orders");
            }
        }

        // Validate market order
        if (hasMarket) {
            MarketOrderConfiguration market = config.market();
            boolean hasBaseSize = market.baseSize() != null && !market.baseSize().isBlank();
            boolean hasQuoteSize = market.quoteSize() != null && !market.quoteSize().isBlank();

            if (!hasBaseSize && !hasQuoteSize) {
                throw new IllegalArgumentException("market configuration must contain either 'base_size' or 'quote_size'");
            }
            if (hasBaseSize && hasQuoteSize) {
                throw new IllegalArgumentException("market configuration cannot contain both 'base_size' and 'quote_size'");
            }
        }
    }
}
