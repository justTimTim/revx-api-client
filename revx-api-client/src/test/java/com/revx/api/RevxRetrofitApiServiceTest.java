package com.revx.api;

import com.revx.api.payload.enums.*;
import com.revx.api.payload.request.order.ActiveOrderRequest;
import com.revx.api.payload.request.order.LimitOrderConfiguration;
import com.revx.api.payload.request.order.NewOrder;
import com.revx.api.payload.request.order.OrderConfiguration;
import com.revx.api.payload.response.balance.Balance;
import com.revx.api.payload.response.configuration.Currency;
import com.revx.api.payload.response.market.Data;
import com.revx.api.payload.response.market.Metadata;
import com.revx.api.payload.response.market.OrderBook;
import com.revx.api.payload.response.market.OrderBoorRecord;
import com.revx.api.payload.response.order.ActiveOrders;
import com.revx.api.payload.response.order.NewOrderData;
import com.revx.api.payload.response.order.NewOrderResponse;
import com.revx.api.payload.response.order.OrderInfo;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RevxRetrofitApiServiceTest {

    private MockWebServer mockWebServer;
    private RevxRetrofitApiService apiService;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        String baseUrl = mockWebServer.url("/").toString();
        apiService = createTestService(baseUrl);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void getBalances_ShouldReturnListOfBalances() throws IOException, InterruptedException {
        String jsonResponse = """
                [
                  {
                    "currency": "BTC",
                    "available": "1000.00000000",
                    "reserved": "234.50000000",
                    "total": "1234.50000000"
                  },
                  {
                    "currency": "ETH",
                    "available": "1000.0000",
                    "reserved": "34.5000",
                    "total": "234.5000"
                  }
                ]
                """;

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(jsonResponse)
                .addHeader("Content-Type", "application/json"));

        // When
        Call<List<Balance>> call = apiService.getBalances();
        List<Balance> balances = call.execute().body();

        // Then
        assertNotNull(balances);
        assertEquals(2, balances.size());

        Balance btcBalance = balances.get(0);
        assertEquals("BTC", btcBalance.currency());
        assertEquals("1000.00000000", btcBalance.available());
        assertEquals("234.50000000", btcBalance.reserved());
        assertEquals("1234.50000000", btcBalance.total());


        Balance ethBalance = balances.get(1);
        assertEquals("ETH", ethBalance.currency());
        assertEquals("1000.0000", ethBalance.available());
        assertEquals("34.5000", ethBalance.reserved());
        assertEquals("234.5000", ethBalance.total());

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("GET", request.getMethod());
        assertEquals("/api/1.0/balances", request.getPath());
    }

    @Test
    void getBalances_ServerError_ShouldReturnErrorResponse() throws IOException {
        String errorJson = """
                {
                    "message": "Internal server error",
                    "errorId": "ERR_500",
                    "timestamp": 1678886400000
                }
                """;

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody(errorJson)
                .addHeader("Content-Type", "application/json"));

        Call<List<Balance>> call = apiService.getBalances();
        retrofit2.Response<List<Balance>> response = call.execute();

        assertFalse(response.isSuccessful());
        assertEquals(500, response.code());
        assertNull(response.body());
        assertNotNull(response.errorBody());
    }



    @Test
    void getBalances_WithExtraFields_ShouldIgnoreThem() throws IOException {
        String jsonResponse = """
                [
                    {
                        "currency": "BTC",
                        "available": "1000.00000000",
                        "reserved": "234.50000000",
                        "total": "1234.50000000",
                        "extra_field": "should be ignored",
                        "another_field": 123
                    }
                ]
                """;

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(jsonResponse)
                .addHeader("Content-Type", "application/json"));

        Call<List<Balance>> call = apiService.getBalances();
        List<Balance> balances = call.execute().body();

        assertNotNull(balances);
        assertEquals(1, balances.size());

        Balance balance = balances.get(0);
        assertEquals("BTC", balance.currency());
        assertEquals("1000.00000000", balance.available());
        assertEquals("234.50000000", balance.reserved());
        assertEquals("1234.50000000", balance.total());
    }

    @Test
    void getCurrencies_ShouldReturnMapOfCurrencies() throws IOException, InterruptedException {
        String jsonResponse = """
        {
            "BTC": {
                "symbol": "BTC",
                "name": "Bitcoin",
                "scale": 8,
                "asset_type": "crypto",
                "status": "active"
            },
            "ETH": {
                "symbol": "ETH",
                "name": "Ethereum",
                "scale": 8,
                "asset_type": "crypto",
                "status": "active"
            },
            "USD": {
                "symbol": "USD",
                "name": "US Dollar",
                "scale": 2,
                "asset_type": "fiat",
                "status": "active"
            }
        }
        """;

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(jsonResponse)
                .addHeader("Content-Type", "application/json"));

        Call<Map<String, Currency>> call = apiService.getCurrencies();
        Map<String, Currency> currencies = call.execute().body();

        assertNotNull(currencies);
        assertEquals(3, currencies.size());

        Currency btc = currencies.get("BTC");
        assertNotNull(btc);
        assertEquals("BTC", btc.symbol());
        assertEquals("Bitcoin", btc.name());
        assertEquals(8, btc.scale());
        assertEquals("crypto", btc.assetType());
        assertEquals("active", btc.status());

        Currency eth = currencies.get("ETH");
        assertNotNull(eth);
        assertEquals("ETH", eth.symbol());
        assertEquals("Ethereum", eth.name());
        assertEquals(8, eth.scale());
        assertEquals("crypto", eth.assetType());
        assertEquals("active", eth.status());

        Currency usd = currencies.get("USD");
        assertNotNull(usd);
        assertEquals("USD", usd.symbol());
        assertEquals("US Dollar", usd.name());
        assertEquals(2, usd.scale());
        assertEquals("fiat", usd.assetType());
        assertEquals("active", usd.status());

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("GET", request.getMethod());
        assertEquals("/api/1.0/configuration/currencies", request.getPath());
    }

    @Test
    void getOrderBook_ShouldReturnOrderBook() throws IOException, InterruptedException {
        String jsonResponse = """
        {
          "data": {
            "asks": [
              {
                "aid": "ETH",
                "anm": "Ethereum",
                "s": "SELL",
                "p": "4600",
                "pc": "USD",
                "pn": "MONE",
                "q": "17",
                "qc": "ETH",
                "qn": "UNIT",
                "ve": "REVX",
                "no": "3",
                "ts": "CLOB",
                "pdt": 3318215482991
              },
              {
                "aid": "ETH",
                "anm": "Ethereum",
                "s": "SELL",
                "p": "4555",
                "pc": "USD",
                "pn": "MONE",
                "q": "2.1234",
                "qc": "ETH",
                "qn": "UNIT",
                "ve": "REVX",
                "no": "2",
                "ts": "CLOB",
                "pdt": 3318215482991
              }
            ],
            "bids": [
              {
                "aid": "ETH",
                "anm": "Ethereum",
                "s": "BUYI",
                "p": "4550",
                "pc": "USD",
                "pn": "MONE",
                "q": "0.25",
                "qc": "ETH",
                "qn": "UNIT",
                "ve": "REVX",
                "no": "1",
                "ts": "CLOB",
                "pdt": 3318215482991
              },
              {
                "aid": "ETH",
                "anm": "Ethereum",
                "s": "BUYI",
                "p": "4500",
                "pc": "USD",
                "pn": "MONE",
                "q": "24.42",
                "qc": "ETH",
                "qn": "UNIT",
                "ve": "REVX",
                "no": "5",
                "ts": "CLOB",
                "pdt": 3318215482991
              }
            ]
          },
          "metadata": {
            "timestamp": 3318215482991
          }
        }
        """;

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(jsonResponse)
                .addHeader("Content-Type", "application/json"));

        String symbol = "ETH-USD";

        Call<OrderBook> call = apiService.getOrderBook(symbol);
        OrderBook orderBook = call.execute().body();

        assertNotNull(orderBook);


        Metadata metadata = orderBook.getMetadata();
        assertNotNull(metadata);
        assertEquals(3318215482991L, metadata.timestamp());


        Data data = orderBook.getData();
        assertNotNull(data);

        List<OrderBoorRecord> asks = data.asks();
        assertNotNull(asks);
        assertEquals(2, asks.size());

        OrderBoorRecord ask1 = asks.get(0);
        assertEquals("ETH", ask1.assetId());
        assertEquals("Ethereum", ask1.assetName());
        assertEquals("SELL", ask1.side());
        assertEquals("4600", ask1.price());
        assertEquals("USD", ask1.priceCurrency());
        assertEquals("MONE", ask1.priceNotation());
        assertEquals("17", ask1.quantity());
        assertEquals("ETH", ask1.quantityCurrency());
        assertEquals("UNIT", ask1.quantityNotation());
        assertEquals("REVX", ask1.venue());
        assertEquals("3", ask1.numberOfOrders());
        assertEquals("CLOB", ask1.tradingSystem());
        assertEquals(3318215482991L, ask1.publicationDateTime());

        OrderBoorRecord ask2 = asks.get(1);
        assertEquals("ETH", ask2.assetId());
        assertEquals("Ethereum", ask2.assetName());
        assertEquals("SELL", ask2.side());
        assertEquals("4555", ask2.price());
        assertEquals("2.1234", ask2.quantity());
        assertEquals("2", ask2.numberOfOrders());

        List<OrderBoorRecord> bids = data.bids();
        assertNotNull(bids);
        assertEquals(2, bids.size());

        OrderBoorRecord bid1 = bids.get(0);
        assertEquals("ETH", bid1.assetId());
        assertEquals("Ethereum", bid1.assetName());
        assertEquals("BUYI", bid1.side());
        assertEquals("4550", bid1.price());
        assertEquals("0.25", bid1.quantity());
        assertEquals("1", bid1.numberOfOrders());

        OrderBoorRecord bid2 = bids.get(1);
        assertEquals("ETH", bid2.assetId());
        assertEquals("Ethereum", bid2.assetName());
        assertEquals("BUYI", bid2.side());
        assertEquals("4500", bid2.price());
        assertEquals("24.42", bid2.quantity());
        assertEquals("5", bid2.numberOfOrders());

        for (OrderBoorRecord bid : bids) {
            assertEquals("USD", bid.priceCurrency());
            assertEquals("MONE", bid.priceNotation());
            assertEquals("ETH", bid.quantityCurrency());
            assertEquals("UNIT", bid.quantityNotation());
            assertEquals("REVX", bid.venue());
            assertEquals("CLOB", bid.tradingSystem());
            assertEquals(3318215482991L, bid.publicationDateTime());
        }

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("GET", request.getMethod());
        assertEquals("/api/1.0/order-book/ETH-USD", request.getPath());
    }

    @Test
    void createOrder_ShouldReturnOrderResponse() throws IOException, InterruptedException {
        // Given
        String jsonResponse = """
        {
          "data": [
            {
              "venue_order_id": "7a52e92e-8639-4fe1-abaa-68d3a2d5234b",
              "client_order_id": "984a4d8a-2a9b-4950-822f-2a40037f02bd",
              "state": "new"
            }
          ]
        }
        """;

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(jsonResponse)
                .addHeader("Content-Type", "application/json"));

        // Create a test order request
        UUID clientOrderId = UUID.fromString("984a4d8a-2a9b-4950-822f-2a40037f02bd");
        NewOrder newOrder = new NewOrder(
                clientOrderId.toString(),
                "BTC-USD",
                "buy",
                new OrderConfiguration(
                        new LimitOrderConfiguration(
                                "0.1",
                                null,
                                "50000.50",
                                List.of("post_only")
                        ),
                        null
                )
        );

        // When
        Call<NewOrderResponse> call = apiService.createOrder(newOrder);
        NewOrderResponse response = call.execute().body();

        // Then
        assertNotNull(response);
        assertNotNull(response.data());
        assertEquals(1, response.data().size());

        // Verify order data
        NewOrderData newOrderData = response.data().get(0);
        assertEquals("7a52e92e-8639-4fe1-abaa-68d3a2d5234b", newOrderData.venueOrderId());
        assertEquals(clientOrderId.toString(), newOrderData.clientOrderId());
        assertEquals(OrderState.NEW, newOrderData.state());

        // Verify request
        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("POST", request.getMethod());
        assertEquals("/api/1.0/orders", request.getPath());
        assertEquals("application/json; charset=UTF-8", request.getHeader("Content-Type"));

        // Verify request body (optional - can check if order was serialized correctly)
        String requestBody = request.getBody().readUtf8();
        assertTrue(requestBody.contains("BTC-USD"));
        assertTrue(requestBody.contains("buy"));
        assertTrue(requestBody.contains("50000.50"));
    }

    @Test
    void getActiveOrders_ShouldReturnActiveOrdersResponse() throws IOException, InterruptedException {
        // Given
        String jsonResponse = """
        {
          "data": [
            {
              "id": "7a52e92e-8639-4fe1-abaa-68d3a2d5234b",
              "client_order_id": "984a4d8a-2a9b-4950-822f-2a40037f02bd",
              "symbol": "BTC/USD",
              "side": "buy",
              "type": "limit",
              "quantity": "0.002",
              "filled_quantity": "0",
              "leaves_quantity": "0.002",
              "price": "98745",
              "status": "new",
              "time_in_force": "gtc",
              "execution_instructions": ["allow_taker"],
              "created_date": 3318215482991
            },
            {
              "id": "8b63f93f-9740-5ff2-bcbb-79e4a3e6345c",
              "previous_order_id": "7a52e92e-8639-4fe1-abaa-68d3a2d5234b",
              "client_order_id": "a95b5e9b-3c6c-4951-933g-3b50048g13ce",
              "symbol": "ETH/EUR",
              "side": "sell",
              "type": "market",
              "quantity": "1.5",
              "filled_quantity": "0.75",
              "leaves_quantity": "0.75",
              "price": "2500",
              "average_fill_price": "2480",
              "status": "partially_filled",
              "reject_reason": null,
              "time_in_force": "gtc",
              "execution_instructions": ["post_only"],
              "created_date": 3318215483991
            }
          ],
          "metadata": {
            "timestamp": 3318215482991,
            "next_cursor": "GF0ZT0xNzY0OTMxNTAyODU0O2lkPTM3YjExMWJlLTcwMzYtNGYzNC1hYWYyLTM4ZDVjYTEyN2M1Yw=="
          }
        }
        """;

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(jsonResponse)
                .addHeader("Content-Type", "application/json"));

        // Create request
        ActiveOrderRequest request = new ActiveOrderRequest(
                List.of("BTC/USD", "ETH/EUR"),
                List.of("new", "partially_filled"),
                "buy",
                null,
                50
        );

        // When
        Call<ActiveOrders> responseCall = apiService.getActiveOrders(request.symbols(), request.orderStates(),
                request.side(), request.cursor(), request.limit());
        ActiveOrders response = responseCall.execute().body();

        // Then
        assertNotNull(response);
        assertNotNull(response.data());
        assertEquals(2, response.data().size());

        // Verify first order (limit buy)
        OrderInfo order1 = response.data().get(0);
        assertEquals("7a52e92e-8639-4fe1-abaa-68d3a2d5234b", order1.id());
        assertNull(order1.previousOrderId());
        assertEquals("984a4d8a-2a9b-4950-822f-2a40037f02bd", order1.clientOrderId());
        assertEquals("BTC/USD", order1.symbol());
        assertEquals(Side.BUY, order1.side());
        assertEquals(Type.LIMIT, order1.type());
        assertEquals("0.002", order1.quantity());
        assertEquals("0", order1.filledQuantity());
        assertEquals("0.002", order1.leavesQuantity());
        assertEquals("98745", order1.price());
        assertNull(order1.averageFillPrice());
        assertEquals(Status.NEW, order1.status());
        assertNull(order1.rejectReason());
        assertEquals("gtc", order1.timeInForce());
        assertEquals(List.of(ExecutionInstruction.ALLOW_TAKER), order1.executionInstructions());
        assertEquals(3318215482991L, order1.createdDate());

        // Verify second order (market sell, partially filled)
        OrderInfo order2 = response.data().get(1);
        assertEquals("8b63f93f-9740-5ff2-bcbb-79e4a3e6345c", order2.id());
        assertEquals("7a52e92e-8639-4fe1-abaa-68d3a2d5234b", order2.previousOrderId());
        assertEquals("a95b5e9b-3c6c-4951-933g-3b50048g13ce", order2.clientOrderId());
        assertEquals("ETH/EUR", order2.symbol());
        assertEquals(Side.SELL, order2.side());
        assertEquals(Type.MARKET, order2.type());
        assertEquals("1.5", order2.quantity());
        assertEquals("0.75", order2.filledQuantity());
        assertEquals("0.75", order2.leavesQuantity());
        assertEquals("2500", order2.price());
        assertEquals("2480", order2.averageFillPrice());
        assertEquals(Status.PARTIALLY_FILLED, order2.status());
        assertNull(order2.rejectReason());
        assertEquals("gtc", order2.timeInForce());
        assertEquals(List.of(ExecutionInstruction.POST_ONLY), order2.executionInstructions());
        assertEquals(3318215483991L, order2.createdDate());

        // Verify metadata
        assertNotNull(response.metadata());
        assertEquals(3318215482991L, response.metadata().timestamp());
        assertEquals("GF0ZT0xNzY0OTMxNTAyODU0O2lkPTM3YjExMWJlLTcwMzYtNGYzNC1hYWYyLTM4ZDVjYTEyN2M1Yw==",
                response.metadata().nextCursor());

        // Verify request parameters were passed correctly
        RecordedRequest httpRequest = mockWebServer.takeRequest();
        assertEquals("GET", httpRequest.getMethod());
        assertEquals("/api/1.0/orders?symbols=BTC%2FUSD&symbols=ETH%2FEUR&order_states=new&order_states=partially_filled&side=buy&limit=50", httpRequest.getPath());

        // Verify query parameters
        assertTrue(httpRequest.getRequestUrl().toString().contains("symbols=BTC%2FUSD"));
        assertTrue(httpRequest.getRequestUrl().toString().contains("symbols=ETH%2FEUR"));
        assertTrue(httpRequest.getRequestUrl().toString().contains("order_states=new"));
        assertTrue(httpRequest.getRequestUrl().toString().contains("order_states=partially_filled"));
        assertTrue(httpRequest.getRequestUrl().toString().contains("side=buy"));
        assertTrue(httpRequest.getRequestUrl().toString().contains("limit=50"));
    }

    @Test
    void getActiveOrders_EmptyResponse_ShouldHandleGracefully() throws IOException {
        // Given
        String jsonResponse = """
        {
          "data": [],
          "metadata": {
            "timestamp": 3318215482991,
            "next_cursor": null
          }
        }
        """;

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(jsonResponse));

        ActiveOrderRequest request = new ActiveOrderRequest(null, null, null, null, 10);

        // When
        Call<ActiveOrders> responseCall = apiService.getActiveOrders(request.symbols(), request.orderStates(),
                request.side(), request.cursor(), request.limit());
        ActiveOrders response = responseCall.execute().body();

        // Then
        assertNotNull(response);
        assertNotNull(response.data());
        assertTrue(response.data().isEmpty());
        assertNotNull(response.metadata());
        assertNull(response.metadata().nextCursor());
    }





    private RevxRetrofitApiService createTestService(String baseUrl) {

        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    okhttp3.Request original = chain.request();
                    okhttp3.Request request = original.newBuilder()
                            .build();
                    return chain.proceed(request);
                })
                .build();

        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        return retrofit.create(RevxRetrofitApiService.class);
    }
}
