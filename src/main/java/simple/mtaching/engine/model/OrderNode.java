package simple.mtaching.engine.model;

public class OrderNode {
    private final Order order;
    private OrderNode next = null;

    public OrderNode(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public OrderNode getNext() {
        return next;
    }

    public void setNext(OrderNode next) {
        this.next = next;
    }
}
