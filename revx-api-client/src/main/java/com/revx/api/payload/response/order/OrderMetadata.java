package com.revx.api.payload.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Pagination metadata for navigating through results.
 *
 * @param timestamp Current timestamp in Unix epoch milliseconds
 * @param nextCursor Cursor used to retrieve the next page of results.
 *                   To continue paginating, pass this value in the cursor query parameter
 */
public record OrderMetadata(@JsonProperty("timestamp") Long timestamp,
                            @JsonProperty("next_cursor") String nextCursor) {
}
