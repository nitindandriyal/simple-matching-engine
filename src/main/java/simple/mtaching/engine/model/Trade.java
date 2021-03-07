package simple.mtaching.engine.model;

public class Trade {
    private final int buyer;
    private final int seller;
    private final int price;
    private final int volume;
    private final TradeStatus status;
    private final String comment;

    public Trade(int buyer, int seller, int price, int volume, TradeStatus status, String comment) {
        this.buyer = buyer;
        this.seller = seller;
        this.price = price;
        this.volume = volume;
        this.status = status;
        this.comment = comment;
    }

    public Trade(int buyer, int seller, int price, int volume, TradeStatus status) {
        this(buyer, seller, price, volume, status, null);
    }

    public int getBuyer() {
        return buyer;
    }

    public int getSeller() {
        return seller;
    }

    public int getPrice() {
        return price;
    }

    public int getVolume() {
        return volume;
    }

    public TradeStatus getTradeStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        if (null == comment) {
            return "trade : " + status + ", buyer id: " + buyer + ", seller id: " + seller + ", price: " + price + ", volume: " + volume;
        } else {
            return "trade : " + status + ", buyer id: " + buyer + ", seller id: " + seller + ", price: " + price + ", volume: " + volume + ", comment : " + comment;
        }
    }
}
