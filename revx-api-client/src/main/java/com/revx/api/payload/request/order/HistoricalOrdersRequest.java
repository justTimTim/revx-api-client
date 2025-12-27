package com.revx.api.payload.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Request parameters for querying historical orders.
 *
 * @param symbols     Filter historical orders by specific currency pairs (comma-separated).
 *                    Example: ["BTC-USD", "ETH-USD"]
 * @param orderStates Filter orders by one or more specific states.
 *                    Possible values: ["filled", "cancelled", "rejected", "replaced"]
 * @param orderTypes  Filter orders by specific types (comma-separated).
 *                    Possible values: ["market", "limit"]
 * @param startDate   Start timestamp for the query range in Unix epoch milliseconds.
 *                    If omitted, defaults to 1 week prior to end_date.
 *                    The difference between start_date and end_date must be ≤ 1 week.
 * @param endDate     End timestamp for the query range in Unix epoch milliseconds.
 *                    If omitted, defaults to start_date + 1 week (if start_date is provided)
 *                    or the current date (if start_date is missing).
 *                    The duration between start_date and end_date must not exceed 1 week.
 * @param cursor      Pagination cursor obtained from the metadata.next_cursor property of the previous response.
 *                    Example: "ZGF0ZT0xNzY0OTMxNTAyODU0O2lkPTM3YjExMWJlLTcwMzYtNGYzNC1hYWYyLTM4ZDVjYTEyN2M1Yw=="
 * @param limit       Maximum number of records to return.
 *                    Possible values: ≥ 1 and ≤ 100
 *                    Default value: 100
 *                    Example: 100
 */
public record HistoricalOrdersRequest(
        @JsonProperty("symbols") List<String> symbols,
        @JsonProperty("order_states") List<String> orderStates,
        @JsonProperty("order_types") List<String> orderTypes,
        @JsonProperty("start_date") Long startDate,
        @JsonProperty("end_date") Long endDate,
        @JsonProperty("cursor") String cursor,
        @JsonProperty("limit") Integer limit
) {
}
