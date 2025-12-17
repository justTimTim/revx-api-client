package com.revx.api.payload.response.market;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderBook {

    private final Data data;
    private final Metadata metadata;

    @JsonCreator
    public OrderBook(@JsonProperty("data") Data data, @JsonProperty("metadata") Metadata metadata) {
        this.data = data;
        this.metadata = metadata;
    }
}
