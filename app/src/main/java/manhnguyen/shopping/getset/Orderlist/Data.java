package manhnguyen.shopping.getset.Orderlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("orders")
    @Expose
    private List<Order> orders = null;

    /**
     * No args constructor for use in serialization
     */
    public Data() {
    }

    /**
     * @param msg
     * @param orders
     * @param status
     */
    public Data(Integer status, String msg, List<Order> orders) {
        super();
        this.status = status;
        this.msg = msg;
        this.orders = orders;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

}
