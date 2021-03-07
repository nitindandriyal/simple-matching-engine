### Assumptions
Some assumptions as below, based on common knowledge and ease of understanding
- Order ID, Prices, Volume are integer values
- Order can be Partially filled, with remaining amount reflected in the order book 
- Market order is IOC, will be rejected if no match found

### Simple design
Limit Order Book uses sparse arrays to store the prices to take the advantage of prices for an instrument being usually close to each other
There is one array for bids and other for asks just to maintain simplicity
The interface that covers all the service methods MatchingEngine with below methods:
```
- void sendOrder(Order order)
- OrderBookSnapshot getOrderBook(String pair)
- List<Trade> getTradeHistory()
- Order getOrder(int id)
```

```SimpleMatchingEngine``` provides the implementation for ```MatchingEngine```

For each market instrument, a ```Thread``` is assigned and started to partition the executions of different instruments

- BTCUSD -> ```Thread1(SmartOrderRouter1)``` -> ```LimitOrderBook1(BTCUSD)```
- ETHUSD -> ```Thread2(SmartOrderRouter2)``` -> ```LimitOrderBook2(ETHUSD)```

```SimpleMatchingEngine.sendOrder()``` dispatches the Order to ```SmartOrderRouter``` via a ```BlockingQueue``` which can potentially be replaced with a ```RingBuffer```

```SmartOrderRouter``` based on ```OrderType [MARKET|LIMIT]``` routes the order to ```LimitOrderBook``` for execution

```OrderBookSnapshot``` provides the current snapshot of the order book with the all available levels

```TradeBook``` maintains list of trades(Filled/Rejected) 

```OrderHistory``` maintains the historical Map of order-id -> order for all incoming orders
 
### Test
```SimpleMatchingEngineTest``` covers the tests scenarios
