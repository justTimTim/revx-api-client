package com.revx.api.impl;


import com.revx.api.RevxApiRestClient;
import com.revx.api.RevxRetrofitApiService;
import com.revx.api.payload.request.order.ActiveOrderRequest;
import com.revx.api.payload.request.order.HistoricalOrdersRequest;
import com.revx.api.payload.request.order.NewOrder;
import com.revx.api.payload.response.balance.Balance;
import com.revx.api.payload.response.configuration.Currency;
import com.revx.api.payload.response.configuration.Pair;
import com.revx.api.payload.response.market.OrderBook;
import com.revx.api.payload.response.order.NewOrderResponse;
import com.revx.api.payload.response.order.OrderInfo;
import com.revx.api.payload.response.order.Orders;

import java.util.Collection;
import java.util.Map;

import static com.revx.api.ApiCore.create;
import static com.revx.api.ApiCore.executeSync;

public class RevxApiRestClientImpl implements RevxApiRestClient {

    private final RevxRetrofitApiService apiService;

    public RevxApiRestClientImpl(String url, String apiKey, String secret) {
        apiService = create(RevxRetrofitApiService.class, url, apiKey, secret);
    }

    @Override
    public Collection<Balance> getBalances() {
        return executeSync(apiService.getBalances());
    }

    @Override
    public Map<String, Currency> getCurrencies() {
        return executeSync(apiService.getCurrencies());
    }

    @Override
    public Map<String, Pair> getPairs() {
        return executeSync(apiService.getPairs());
    }

    @Override
    public OrderBook getOrderBook(String symbol) {
        return executeSync(apiService.getOrderBook(symbol));
    }

    @Override
    public OrderBook getOrderBook(String symbol, Integer limit) {
        return executeSync(apiService.getOrderBook(symbol, limit));
    }

    @Override
    public NewOrderResponse createOrder(NewOrder order) {
        return executeSync(apiService.createOrder(order));
    }

    @Override
    public Orders getActiveOrders(ActiveOrderRequest request) {
        return executeSync(apiService.getActiveOrders(request.symbols(),
                request.orderStates(),
                request.side(),
                request.cursor(),
                request.limit()));
    }

    @Override
    public Orders getHistoricalOrders(HistoricalOrdersRequest request) {
        return executeSync(apiService.getHistoricalOrders(request.symbols(),
                request.orderStates(),
                request.orderTypes(),
                request.startDate(),
                request.endDate(),
                request.cursor(),
                request.limit()));
    }

    @Override
    public OrderInfo getOrderById(String orderId) {
        return executeSync(apiService.getOrderById(orderId)).data();
    }

    @Override
    public void cancelOrder(String venueOrderId) {
        executeSync(apiService.cancelOrder(venueOrderId));
    }
}
