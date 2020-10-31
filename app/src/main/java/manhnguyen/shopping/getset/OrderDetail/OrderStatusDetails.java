package manhnguyen.shopping.getset.OrderDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderStatusDetails {

    @SerializedName("order_placed")
    @Expose
    private String orderPlaced;
    @SerializedName("pending")
    @Expose
    private String pending;
    @SerializedName("onhold")
    @Expose
    private String onhold;
    @SerializedName("processing")
    @Expose
    private String processing;

    public String getProcessing() {
        return processing;
    }

    public void setProcessing(String processing) {
        this.processing = processing;
    }

    @SerializedName("completed_datetime")
    @Expose
    private String completedDatetime;
    @SerializedName("cancel_datetime")
    @Expose
    private String cancelDatetime;
    @SerializedName("refund")
    @Expose
    private String refund;

    public String getOrderPlaced() {
        return orderPlaced;
    }

    public void setOrderPlaced(String orderPlaced) {
        this.orderPlaced = orderPlaced;
    }

    public String getPending() {
        return pending;
    }

    public void setPending(String pending) {
        this.pending = pending;
    }

    public String getOnhold() {
        return onhold;
    }

    public void setOnhold(String onhold) {
        this.onhold = onhold;
    }

    public String getCompletedDatetime() {
        return completedDatetime;
    }

    public void setCompletedDatetime(String completedDatetime) {
        this.completedDatetime = completedDatetime;
    }

    public String getCancelDatetime() {
        return cancelDatetime;
    }

    public void setCancelDatetime(String cancelDatetime) {
        this.cancelDatetime = cancelDatetime;
    }

    public String getRefund() {
        return refund;
    }

    public void setRefund(String refund) {
        this.refund = refund;
    }

}
