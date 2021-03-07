package diginex.me.trade;


import diginex.me.model.Order;
import diginex.me.model.Trade;
import diginex.me.model.TradeStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TradeBook {
    private final List<Trade> listOfTrades = new ArrayList<>();
    private final CopyOnWriteArrayList<TradeListener> tradeListeners = new CopyOnWriteArrayList<>();

    public void fillTrade(Order buyOrder, Order sellOrder, int price, int volume) {
        Trade trade = new Trade(buyOrder.getId(), sellOrder.getId(), price, volume, TradeStatus.FILLED);
        listOfTrades.add(trade);
        for (TradeListener tradeListener : tradeListeners) {
            tradeListener.onTrade(trade);
        }
    }

    public void rejectTrade(int buyOrder, int sellOrder, int price, int volume, String comment) {
        Trade trade = new Trade(buyOrder, sellOrder, price, volume, TradeStatus.REJECTED, comment);
        listOfTrades.add(trade);
        for (TradeListener tradeListener : tradeListeners) {
            tradeListener.onTrade(trade);
        }
    }

    public List<Trade> doneTrades() {
        return Collections.unmodifiableList(listOfTrades);
    }

    public void subscribe(TradeListener tradeListener) {
        tradeListeners.add(tradeListener);
    }
}
