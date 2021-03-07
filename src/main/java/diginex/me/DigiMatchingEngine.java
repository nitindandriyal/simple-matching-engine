package diginex.me;

import diginex.me.model.Order;
import diginex.me.model.OrderBookSnapshot;
import diginex.me.model.Trade;

import java.util.List;

public interface DigiMatchingEngine {
    void sendOrder(Order order);

    OrderBookSnapshot getOrderBook(String pair);

    List<Trade> getTradeHistory();

    Order getOrder(int id);
}
