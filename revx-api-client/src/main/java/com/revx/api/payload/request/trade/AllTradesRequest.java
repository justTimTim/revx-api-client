package com.revx.api.payload.request.trade;

public record AllTradesRequest(String symbol, Long startDate, Long endDate, String cursor, Integer limit) {
    public AllTradesRequest {
        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("symbol cannot be null or blank");
        }
        if (limit != null && (limit < 1 || limit > 100)) {
            throw new IllegalArgumentException("limit must be between 1 and 100");
        }
    }
}
