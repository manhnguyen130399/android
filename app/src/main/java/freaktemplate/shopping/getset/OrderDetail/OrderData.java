package freaktemplate.shopping.getset.OrderDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderData {

    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_details")
    @Expose
    private OrderDetails orderDetails;
    @SerializedName("order_status_details")
    @Expose
    private OrderStatusDetails orderStatusDetails;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public OrderStatusDetails getOrderStatusDetails() {
        return orderStatusDetails;
    }

    public void setOrderStatusDetails(OrderStatusDetails orderStatusDetails) {
        this.orderStatusDetails = orderStatusDetails;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

}
