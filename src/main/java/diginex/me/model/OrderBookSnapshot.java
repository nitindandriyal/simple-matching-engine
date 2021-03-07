package diginex.me.model;

import java.util.LinkedList;
import java.util.List;

public class OrderBookSnapshot {
    private final List<DepthLevel> levels = new LinkedList<>();

    public void addLevel(int bid, int bidVolume, int bidOrderId, int ask, int askVolume, int askOrderId) {
        levels.add(new DepthLevel(bid, bidVolume, bidOrderId, ask, askVolume, askOrderId));
    }

    public List<DepthLevel> getOrderBooks() {
        return levels;
    }

    public static class DepthLevel {
        public final int bid;
        public final int bidVolume;
        public final int bidOrderId;
        public final int ask;
        public final int askVolume;
        public final int askOrderId;

        public DepthLevel(int bid, int bidVolume, int bidOrderId, int ask, int askVolume, int askOrderId) {
            this.bid = bid;
            this.bidVolume = bidVolume;
            this.bidOrderId = bidOrderId;
            this.ask = ask;
            this.askVolume = askVolume;
            this.askOrderId = askOrderId;
        }
    }
}
