package simple.mtaching.engine;

import simple.mtaching.engine.model.Order;
import simple.mtaching.engine.model.OrderBookSnapshot;
import simple.mtaching.engine.model.Trade;

import java.util.List;

public interface DigiMatchingEngine {
    void sendOrder(Order order);

    OrderBookSnapshot getOrderBook(String pair);

    List<Trade> getTradeHistory();

    Order getOrder(int id);
}
