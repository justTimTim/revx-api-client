package com.revx.api;


import com.revx.api.payload.request.order.NewOrder;
import com.revx.api.payload.response.balance.Balance;
import com.revx.api.payload.response.configuration.Currency;
import com.revx.api.payload.response.configuration.Pair;
import com.revx.api.payload.response.market.OrderBook;
import com.revx.api.payload.response.order.ActiveOrders;
import com.revx.api.payload.response.order.NewOrderResponse;
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
    Call<ActiveOrders> getActiveOrders(
            @Query("symbols") List<String> symbols,
            @Query("order_states") List<String> orderStates,
            @Query("side") String side,
            @Query("cursor") String cursor,
            @Query("limit") Integer limit
    );

}
