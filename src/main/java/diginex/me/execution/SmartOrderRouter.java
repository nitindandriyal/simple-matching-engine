package diginex.me.execution;

import diginex.me.model.Order;
import diginex.me.model.OrderType;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SmartOrderRouter implements Runnable {

    private final LimitOrderBook limitOrderBook;
    private final BlockingQueue<Order> orders = new ArrayBlockingQueue<>(1024);
    private volatile boolean isStopped = false;

    public SmartOrderRouter(LimitOrderBook limitOrderBook) {
        this.limitOrderBook = limitOrderBook;
    }

    @Override
    public void run() {
        while (!isStopped) {
            try {
                Order order = orders.take();
                if (order.getType() == OrderType.MARKET) {
                    limitOrderBook.executeOnTob(order);
                } else {
                    limitOrderBook.execute(order);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        isStopped = true;
    }

    public void execute(Order order) {
        assert order != null : "Order can not be null";
        try {
            orders.put(order);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
