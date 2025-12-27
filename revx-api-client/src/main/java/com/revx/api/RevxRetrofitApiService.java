package com.revx.api;


import com.revx.api.payload.request.order.NewOrder;
import com.revx.api.payload.response.balance.Balance;
import com.revx.api.payload.response.configuration.Currency;
import com.revx.api.payload.response.configuration.Pair;
import com.revx.api.payload.response.market.OrderBook;
import com.revx.api.payload.response.order.FillsResponse;
import com.revx.api.payload.response.order.NewOrderResponse;
import com.revx.api.payload.response.order.OrderByIdResponse;
import com.revx.api.payload.response.order.Orders;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

public interface RevxRetrofitApiService {

    @GET("/api/1.0/balances")
    Call<List<Balance>> getBalances();

    @GET("/api/1.0/configuration/currencies")
    Call<Map<String, Currency>> getCurrencies();

    @GET("/api/1.0/configuration/pairs")
    Call<Map<String, Pair>> getPairs();

    @GET("/api/1.0/order-book/{symbol}")
    Call<OrderBook> getOrderBook(@Path("symbol") String symbol);

    @GET("/api/1.0/order-book/{symbol}")
    Call<OrderBook> getOrderBook(@Path("symbol") String symbol, @Query("limit") Integer limit);

    @POST("/api/1.0/orders")
    Call<NewOrderResponse> createOrder(@Body NewOrder order);

    @GET("/api/1.0/orders")
    Call<Orders> getActiveOrders(
            @Query("symbols") List<String> symbols,
            @Query("order_states") List<String> orderStates,
            @Query("side") String side,
            @Query("cursor") String cursor,
            @Query("limit") Integer limit
    );

    @GET("/api/1.0/orders/historical")
    Call<Orders> getHistoricalOrders(
            @Query("symbols") List<String> symbols,
            @Query("order_states") List<String> orderStates,
            @Query("order_types") List<String> orderTypes,
            @Query("start_date") Long startDate,
            @Query("end_date") Long endDate,
            @Query("cursor") String cursor,
            @Query("limit") Integer limit
    );

    @GET("/api/1.0/orders/{venue_order_id}")
    Call<OrderByIdResponse> getOrderById(@Path("venue_order_id") String venueOrderId);

    @HTTP(method = "DELETE", path = "/api/1.0/orders/{venue_order_id}")
    Call<Void> cancelOrder(@Path("venue_order_id") String venueOrderId);

    @GET("/api/1.0/orders/fills/{venue_order_id}")
    Call<FillsResponse> getOrderFills(@Path("venue_order_id") String orderId);

}
