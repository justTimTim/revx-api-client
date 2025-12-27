package com.revx.api.payload.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Retrieve specific order details by ID.
 *
 * @param data order info.
 */
public record OrderByIdResponse(@JsonProperty("data") OrderInfo data) {
}
