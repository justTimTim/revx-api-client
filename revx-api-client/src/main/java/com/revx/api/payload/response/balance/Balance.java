package com.revx.api.payload.response.balance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Get crypto exchange account balances for the requesting user.
 * "currency": "BTC",
 * "available": "1000.00000000",
 * "reserved": "234.50000000",
 * "total": "1234.50000000"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Balance(String currency, String available, String reserved, String total) {
}
