package com.revx.api.payload.response.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Represents a supported currency on the exchange.
 * <p>
 * A currency defines the monetary unit that can be traded, deposited, or withdrawn,
 * along with its properties and constraints.
 * </p>
 *
 * @param symbol    The symbol of the currency (e.g., "BTC", "USD", "ETH").
 * @param name      The full name of the currency (e.g., "Bitcoin", "US Dollar", "Ethereum").
 * @param scale     The scale of the currency - the number of decimal places used to express
 *                  the currency's smallest unit. For example, a scale of 8 means amounts
 *                  can have precision up to eight decimal places (e.g., BTC: 0.00000001).
 * @param assetType The type of the currency - either "fiat" (traditional government-issued)
 *                  or "crypto" (cryptocurrency).
 * @param status    The status of the currency - indicates whether it is available for use
 *                  ("active" or "inactive").
 */
public record Currency(@JsonProperty("symbol") String symbol,
                       @JsonProperty("name") String name,
                       @JsonProperty("scale") Integer scale,
                       @JsonProperty("asset_type") String assetType,
                       @JsonProperty("status") String status) {


    /**
     * Checks if the currency is a cryptocurrency.
     *
     * @return true if assetType is "crypto"
     */
    public boolean isCrypto() {
        return "crypto".equals(assetType);
    }

    /**
     * Checks if the currency is a fiat currency.
     *
     * @return true if assetType is "fiat"
     */
    public boolean isFiat() {
        return "fiat".equals(assetType);
    }

    /**
     * Checks if the currency is active.
     *
     * @return true if status is "active"
     */
    public boolean isActive() {
        return "active".equals(status);
    }

    /**
     * Gets the smallest unit of this currency.
     * For scale 8, this would be 0.00000001.
     *
     * @return the smallest representable unit as a BigDecimal
     */
    public BigDecimal getSmallestUnit() {
        return BigDecimal.ONE.movePointLeft(scale);
    }
}
