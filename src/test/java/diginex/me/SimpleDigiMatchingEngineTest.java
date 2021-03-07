package diginex.me;

import diginex.me.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class SimpleDigiMatchingEngineTest {

    private final Set<String> supportedPairs = new HashSet<>();
    private SimpleDigiMatchingEngine simpleDigiMatchingEngine;

    @Before
    public void setUp() {
        supportedPairs.add("BTCUSD");
        simpleDigiMatchingEngine = new SimpleDigiMatchingEngine(supportedPairs);
        simpleDigiMatchingEngine.init();
    }

    @Test
    public void testLimitOrderMatching() throws InterruptedException {
        simpleDigiMatchingEngine.sendOrder(new Order(10000, "BTCUSD", OrderType.LIMIT, 'B', 49000, 25500));
        simpleDigiMatchingEngine.sendOrder(new Order(10001, "BTCUSD", OrderType.LIMIT, 'S', 53000, 20000));
        simpleDigiMatchingEngine.sendOrder(new Order(10002, "BTCUSD", OrderType.LIMIT, 'S', 50000, 500));
        simpleDigiMatchingEngine.sendOrder(new Order(10003, "BTCUSD", OrderType.LIMIT, 'S', 51000, 10000));
        simpleDigiMatchingEngine.sendOrder(new Order(10004, "BTCUSD", OrderType.LIMIT, 'B', 49500, 50000));
        simpleDigiMatchingEngine.sendOrder(new Order(10005, "BTCUSD", OrderType.LIMIT, 'S', 51500, 100));
        simpleDigiMatchingEngine.sendOrder(new Order(10006, "BTCUSD", OrderType.LIMIT, 'B', 53000, 16000));

        Thread.sleep(50);
        simpleDigiMatchingEngine.printOrderBook("BTCUSD");
        OrderBookSnapshot orderBookSnapshot = simpleDigiMatchingEngine.getOrderBook("BTCUSD");

        assertEquals(2, orderBookSnapshot.getOrderBooks().size());

        OrderBookSnapshot.DepthLevel level = orderBookSnapshot.getOrderBooks().get(0);
        assertEquals(10004, level.bidOrderId);
        assertEquals(49500, level.bid);
        assertEquals(50000, level.bidVolume);
        assertEquals(10001, level.askOrderId);
        assertEquals(53000, level.ask);
        assertEquals(14600, level.askVolume);

        level = orderBookSnapshot.getOrderBooks().get(1);
        assertEquals(10000, level.bidOrderId);
        assertEquals(49000, level.bid);
        assertEquals(25500, level.bidVolume);
        assertEquals(-1, level.askOrderId);
        assertEquals(-1, level.ask);
        assertEquals(-1, level.askVolume);

        List<Trade> tradeList = simpleDigiMatchingEngine.getTradeHistory();
        tradeList.forEach(System.out::println);

        assertEquals(4, tradeList.size());

        Trade trade = tradeList.get(0);
        assertEquals(10006, trade.getBuyer());
        assertEquals(10002, trade.getSeller());
        assertEquals(50000, trade.getPrice());
        assertEquals(500, trade.getVolume());
        assertEquals(TradeStatus.FILLED, trade.getTradeStatus());

        trade = tradeList.get(1);
        assertEquals(10006, trade.getBuyer());
        assertEquals(10003, trade.getSeller());
        assertEquals(51000, trade.getPrice());
        assertEquals(10000, trade.getVolume());
        assertEquals(TradeStatus.FILLED, trade.getTradeStatus());

        trade = tradeList.get(2);
        assertEquals(10006, trade.getBuyer());
        assertEquals(10005, trade.getSeller());
        assertEquals(51500, trade.getPrice());
        assertEquals(100, trade.getVolume());
        assertEquals(TradeStatus.FILLED, trade.getTradeStatus());

        trade = tradeList.get(3);
        assertEquals(10006, trade.getBuyer());
        assertEquals(10001, trade.getSeller());
        assertEquals(53000, trade.getPrice());
        assertEquals(5400, trade.getVolume());
        assertEquals(TradeStatus.FILLED, trade.getTradeStatus());
    }

    @Test
    public void testMarketOrderMatching() throws InterruptedException {
        simpleDigiMatchingEngine.sendOrder(new Order(10000, "BTCUSD", OrderType.LIMIT, 'B', 49000, 25500));
        simpleDigiMatchingEngine.sendOrder(new Order(10001, "BTCUSD", OrderType.MARKET, 'S', 53000, 20000));

        Thread.sleep(100);
        simpleDigiMatchingEngine.printOrderBook("BTCUSD");
        OrderBookSnapshot orderBookSnapshot = simpleDigiMatchingEngine.getOrderBook("BTCUSD");
        assertEquals(1, orderBookSnapshot.getOrderBooks().size());

        OrderBookSnapshot.DepthLevel level = orderBookSnapshot.getOrderBooks().get(0);
        assertEquals(10000, level.bidOrderId);
        assertEquals(49000, level.bid);
        assertEquals(5500, level.bidVolume);
        assertEquals(-1, level.askOrderId);
        assertEquals(-1, level.ask);
        assertEquals(-1, level.askVolume);

        List<Trade> tradeList = simpleDigiMatchingEngine.getTradeHistory();
        tradeList.forEach(System.out::println);

        assertEquals(1, tradeList.size());

        Trade trade = tradeList.get(0);
        assertEquals(10000, trade.getBuyer());
        assertEquals(10001, trade.getSeller());
        assertEquals(49000, trade.getPrice());
        assertEquals(20000, trade.getVolume());
        assertEquals(TradeStatus.FILLED, trade.getTradeStatus());
    }

    @Test
    public void testMarketOrderReject() throws InterruptedException {
        simpleDigiMatchingEngine.sendOrder(new Order(10001, "BTCUSD", OrderType.MARKET, 'S', 53000, 20000));
        Thread.sleep(100);
        simpleDigiMatchingEngine.printOrderBook("BTCUSD");
        OrderBookSnapshot orderBookSnapshot = simpleDigiMatchingEngine.getOrderBook("BTCUSD");
        assertEquals(0, orderBookSnapshot.getOrderBooks().size());

        List<Trade> tradeList = simpleDigiMatchingEngine.getTradeHistory();
        tradeList.forEach(System.out::println);
        assertEquals(1, tradeList.size());

        Trade trade = tradeList.get(0);
        assertEquals(10001, trade.getBuyer());
        assertEquals(-1, trade.getSeller());
        assertEquals(-1, trade.getPrice());
        assertEquals(20000, trade.getVolume());
        assertEquals(TradeStatus.REJECTED, trade.getTradeStatus());
    }

    @After
    public void destroy() {
        simpleDigiMatchingEngine.dispose();
    }
}