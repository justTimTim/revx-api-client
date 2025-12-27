package com.revx.api.payload.response.order;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Response containing active orders with pagination metadata.
 *
 * @param data     Array of active order details
 * @param metadata Pagination metadata for navigating through results
 */
public record Orders(@JsonProperty("data") List<OrderInfo> data,
                     @JsonProperty("metadata") OrderMetadata metadata) {
}
