package simple.mtaching.engine.model;

import java.util.Objects;

public class Order {

    private final int id;
    private final String pair;
    private final OrderType type;
    private final char side;
    private int price;
    private int volume;

    public Order(int id, String pair, OrderType type, char side, int price, int volume) {
        this.id = id;
        this.pair = pair;
        this.type = type;
        this.side = side;
        this.price = price;
        this.volume = volume;
    }

    public int getId() {
        return id;
    }

    public String getPair() {
        return pair;
    }

    public OrderType getType() {
        return type;
    }

    public char getSide() {
        return side;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return side == order.side &&
                pair.equals(order.pair) &&
                type == order.type &&
                price == order.price &&
                volume == order.volume &&
                Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pair, type, side, price, volume);
    }

    @Override
    public String toString() {
        return id + "," + pair + "," + type + "," + side + "," + price + "," + volume;
    }
}
