package diginex.me.execution;

import diginex.me.model.Order;

import java.util.HashMap;
import java.util.Map;

public class OrderHistory {
    private final Map<Integer, Order> orderMap = new HashMap<>();

    public void add(Order order) {
        orderMap.put(order.getId(), order);
    }

    public Order getOrder(int id) {
        return orderMap.get(id);
    }
}
