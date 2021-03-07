package simple.matching.engine.trade;

import simple.matching.engine.model.Trade;

public interface TradeListener {
    void onTrade(Trade trade);
}
