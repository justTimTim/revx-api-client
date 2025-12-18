# revx-api-client

> 🚧 **Development Status**  
> This project is in an **early development stage** and is **not yet production-ready**.  
> APIs, behavior, and structure may change.

A Java client library for interacting with the Revolut X (RevX) API.
Official API documentation:
https://developer.revolut.com/docs/x-api/revolut-x-crypto-exchange-rest-api

Designed for easy integration into Java projects.

---

## 📦 Installation

### Build locally

./gradlew build

Build and publish to the local Maven repository (~/.m2):

./gradlew publishToMavenLocal

---

### Maven

<dependency>
    <groupId>com.revx</groupId>
    <artifactId>revx-api-client</artifactId>
    <version>1.0.0</version>
</dependency>

---

### Gradle

implementation("com.revx:revx-api-client:1.0.0")

---

## 🚀 Usage

### Create client

RevxClientFactory revxApi =
RevxClientFactory.newInstance(apiKey, secret);

RevxApiRestClient restClient =
revxApi.newRestClient("https://revx.revolut.com");

---

### Examples

Get balances:
<pre> 
Collection<Balance> balances = restClient.getBalances();
 </pre>
Get order book:
<pre> 
OrderBook orderBook = restClient.getOrderBook("LINK-USD");
 </pre>
Create limit order:
<pre> 
NewOrder newOrder = limitOrder(
        UUID.randomUUID().toString(),
        "BTC-USDT",
        OrderSide.BUY,
        new LimitOrderConfiguration(
                "1",
                null,
                "10000.0",
                null
        )
);
restClient.createOrder(newOrder);
 </pre>


---

## 🧩 Requirements

- Java 17+
- Gradle 8+

---

## 📬 Disclaimer

This project is not affiliated with or endorsed by Revolut.
