package simple.matching.engine;

import simple.matching.engine.model.Order;
import simple.matching.engine.model.OrderBookSnapshot;
import simple.matching.engine.model.Trade;

import java.util.List;

public interface DigiMatchingEngine {
    void sendOrder(Order order);

    OrderBookSnapshot getOrderBook(String pair);

    List<Trade> getTradeHistory();

    Order getOrder(int id);
}
