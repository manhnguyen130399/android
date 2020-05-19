package freaktemplate.shopping.getset.Orderlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubList {

    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("order_no")
    @Expose
    private Integer orderNo;
    @SerializedName("item")
    @Expose
    private Integer item;
    @SerializedName("bill")
    @Expose
    private String bill;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("image")
    @Expose
    private String image;

    /**
     * No args constructor for use in serialization
     */
    public SubList() {
    }

    /**
     * @param image
     * @param item
     * @param orderNo
     * @param bill
     * @param orderStatus
     * @param orderDate
     */
    public SubList(String orderDate, Integer orderNo, Integer item, String bill, String orderStatus, String image) {
        super();
        this.orderDate = orderDate;
        this.orderNo = orderNo;
        this.item = item;
        this.bill = bill;
        this.orderStatus = orderStatus;
        this.image = image;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
