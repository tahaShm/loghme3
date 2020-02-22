package scheduledExecutors;

import utils.Order;

import java.util.TimerTask;

public class ChangeCourierStatus extends TimerTask {
    private Order order;

    public ChangeCourierStatus(Order order) {this.order = order;}

    public void run() {
        order.setStatus("done");
        cancel();
    }
}
