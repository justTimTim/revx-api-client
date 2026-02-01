package com.revx.api.payload.response.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Trade data.
 */
public record TradeData(
        @JsonProperty("tdt") Long tradeDateTime,
        @JsonProperty("aid") String assetId,
        @JsonProperty("anm") String assetName,
        @JsonProperty("p") String price,
        @JsonProperty("pc") String priceCurrency,
        @JsonProperty("pn") String priceNotation,
        @JsonProperty("q") String quantity,
        @JsonProperty("qc") String quantityCurrency,
        @JsonProperty("qn") String quantityNotation,
        @JsonProperty("ve") String venueExecution,
        @JsonProperty("pdt") Long publicationDateTime,
        @JsonProperty("vp") String venuePublication,
        @JsonProperty("tid") String transactionId
) {
}

