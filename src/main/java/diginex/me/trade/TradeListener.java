package diginex.me.trade;

import diginex.me.model.Trade;

public interface TradeListener {
    void onTrade(Trade trade);
}
