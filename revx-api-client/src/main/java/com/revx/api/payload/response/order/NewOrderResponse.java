package com.revx.api.payload.response.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Wrapper object containing the order details.
 * <p>
 * Contains the order details including system-generated ID and current state.
 * The API returns this response when an order is successfully submitted.
 * </p>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record NewOrderResponse(@JsonProperty("data") List<NewOrderData> data) {
}
