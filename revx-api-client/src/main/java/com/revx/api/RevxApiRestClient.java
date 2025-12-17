package com.revx.api;

import com.revx.api.payload.request.order.ActiveOrderRequest;
import com.revx.api.payload.request.order.NewOrder;
import com.revx.api.payload.response.balance.Balance;
import com.revx.api.payload.response.configuration.Currency;
import com.revx.api.payload.response.configuration.Pair;
import com.revx.api.payload.response.market.OrderBook;
import com.revx.api.payload.response.order.ActiveOrders;
import com.revx.api.payload.response.order.NewOrderResponse;

import java.util.Collection;
import java.util.Map;

public interface RevxApiRestClient {

    /**
     * Get crypto exchange account balances for the requesting user.
     * Note! This endpoint does not return your balances in other services provided by Revolut.
     *
     * @return The list of available balances.
     */
    Collection<Balance> getBalances();

    /**
     * Get configuration for all currencies used on the exchange.
     * @return Supported currencies with their details.
     */
    Map<String, Currency> getCurrencies();

    /**
     * Get configuration for all traded currency pairs.
     * @return Supported currency pairs with their details.
     */
    Map<String, Pair> getPairs();

    /**
     * Retrieve the current order book snapshot (bids and asks) for a specific trading pair.
     * Depth of the order book to return (number of levels). Default value: 20
     *
     * @param symbol The trading pair symbol (e.g., BTC-USD).
     * @return The Order Book snapshot for the given trading pair.
     */
    OrderBook getOrderBook(String symbol);

    /**
     * Retrieve the current order book snapshot (bids and asks) for a specific trading pair.
     *
     * @param symbol The trading pair symbol (e.g., BTC-USD).
     * @param limit  Depth of the order book to return (number of levels). Default value: 20,
     *              Possible values: {@code >= 1} and {@code <= 20}.
     * @return The Order Book snapshot for the given trading pair.
     */
    OrderBook getOrderBook(String symbol, Integer limit);

    /**
     * Place a new order.
     * @param order Order placement details.
     * @return The successfully placed order details.
     */
    NewOrderResponse createOrder(NewOrder order);

    /**
     * Get active crypto exchange orders for the requesting user.
     * @param request request params.
     * @return The list of active orders.
     */
    ActiveOrders getActiveOrders(ActiveOrderRequest request);
}
