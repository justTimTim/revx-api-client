package com.revx.api.payload.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record FillsResponse(@JsonProperty("data") List<FillData> data) {
}
