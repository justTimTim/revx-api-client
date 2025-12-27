package com.revx.api.payload.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Individual trade execution details.
 *
 * @param tdt Trade date and time, expressed in Unix epoch milliseconds
 * @param aid Crypto-asset ID code
 * @param anm Crypto-asset full name
 * @param p   Price in major currency units
 * @param pc  Price currency
 * @param pn  Price notation
 * @param q   Quantity
 * @param qc  Quantity currency
 * @param qn  Quantity notation
 * @param ve  Venue of execution (Always equals REVX)
 * @param pdt Publication date and time, expressed in Unix epoch milliseconds
 * @param vp  Venue of publication (Always equals REVX)
 * @param tid Transaction identification code
 */
public record FillData(
        @JsonProperty("tdt") Long tdt,
        @JsonProperty("aid") String aid,
        @JsonProperty("anm") String anm,
        @JsonProperty("p") String p,
        @JsonProperty("pc") String pc,
        @JsonProperty("pn") String pn,
        @JsonProperty("q") String q,
        @JsonProperty("qc") String qc,
        @JsonProperty("qn") String qn,
        @JsonProperty("ve") String ve,
        @JsonProperty("pdt") Long pdt,
        @JsonProperty("vp") String vp,
        @JsonProperty("tid") String tid
) {

}

