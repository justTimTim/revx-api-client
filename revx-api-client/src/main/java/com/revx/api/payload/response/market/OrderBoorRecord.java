package com.revx.api.payload.response.market;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @param assetId             Crypto-asset ID code.
 * @param assetName           Crypto-asset full name.
 * @param side                The side of the price level.
 *                            Possible values: [SELL, BUYI]
 * @param price               Price in major currency units.
 *                            For example, for USD, 4600 represents 4600 dollars.
 * @param priceCurrency       Price currency.
 * @param priceNotation       Price notation.
 * @param quantity            Aggregated quantity at this price level.
 * @param quantityCurrency    Quantity currency.
 * @param quantityNotation    Quantity notation.
 * @param venue               Venue of execution.
 *                            Always equals REVX.
 * @param numberOfOrders      Number of orders at the price level.
 * @param tradingSystem       Trading system.
 *                            Always equals CLOB (Central Limit Order Book).
 * @param publicationDateTime Publication date and time, returned in Unix epoch milliseconds.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OrderBoorRecord(@JsonProperty("aid") String assetId,
                              @JsonProperty("anm") String assetName,
                              @JsonProperty("s") String side,
                              @JsonProperty("p") String price,
                              @JsonProperty("pc") String priceCurrency,
                              @JsonProperty("pn") String priceNotation,
                              @JsonProperty("q") String quantity,
                              @JsonProperty("qc") String quantityCurrency,
                              @JsonProperty("qn") String quantityNotation,
                              @JsonProperty("ve") String venue,
                              @JsonProperty("no") String numberOfOrders,
                              @JsonProperty("ts") String tradingSystem,
                              @JsonProperty("pdt") Long publicationDateTime) {

}
