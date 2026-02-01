package com.revx.api.payload.response.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Response containing trades for a symbol.
 */
public record TradesResponse(
        @JsonProperty("data") List<TradeData> data,
        @JsonProperty("metadata") Metadata metadata
) {

}