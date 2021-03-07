package simple.mtaching.engine;

import simple.mtaching.engine.execution.LimitOrderBook;
import simple.mtaching.engine.execution.OrderHistory;
import simple.mtaching.engine.execution.SmartOrderRouter;
import simple.mtaching.engine.model.Order;
import simple.mtaching.engine.model.OrderBookSnapshot;
import simple.mtaching.engine.model.Trade;
import simple.mtaching.engine.trade.TradeBook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SimpleDigiMatchingEngine implements DigiMatchingEngine {

    private final Set<String> supportedPairs;
    private final TradeBook tradeBook = new TradeBook();
    private final Map<String, SmartOrderRouter> orderRouterMap = new HashMap<>();
    private final Map<String, LimitOrderBook> limitOrderBookMap = new HashMap<>();
    private final OrderHistory orderHistory = new OrderHistory();

    public SimpleDigiMatchingEngine(Set<String> supportedPairs) {
        this.supportedPairs = supportedPairs;
    }

    public void init() {
        for (String pair : supportedPairs) {
            LimitOrderBook limitOrderBook = new LimitOrderBook(tradeBook);
            SmartOrderRouter smartOrderRouter = new SmartOrderRouter(limitOrderBook);
            new Thread(smartOrderRouter).start();
            orderRouterMap.put(pair, smartOrderRouter);
            limitOrderBookMap.put(pair, limitOrderBook);
        }
    }

    @Override
    public void sendOrder(Order order) {
        SmartOrderRouter smartOrderRouter = orderRouterMap.get(order.getPair());
        assert smartOrderRouter != null : "No executor found";
        smartOrderRouter.execute(order);
        orderHistory.add(order);
    }

    @Override
    public OrderBookSnapshot getOrderBook(String pair) {
        return limitOrderBookMap.get(pair).getOrderBookSnapshot();
    }

    @Override
    public List<Trade> getTradeHistory() {
        return tradeBook.doneTrades();
    }

    @Override
    public Order getOrder(int id) {
        return orderHistory.getOrder(id);
    }

    public void dispose() {
        for (SmartOrderRouter smartOrderRouter : orderRouterMap.values()) {
            // graceful shutdown
            smartOrderRouter.stop();
        }
    }

    public void printOrderBook(String pair) {
        System.out.println(limitOrderBookMap.get(pair).toString());
    }
}
