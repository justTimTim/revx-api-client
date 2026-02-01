package com.revx.api.payload.response.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Pagination metadata.
 */
public record Metadata(
        @JsonProperty("timestamp") Long timestamp,
        @JsonProperty("next_cursor") String nextCursor
) {
}
