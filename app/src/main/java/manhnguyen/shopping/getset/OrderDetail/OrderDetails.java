package manhnguyen.shopping.getset.OrderDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetails {

    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    @SerializedName("subtotal")
    @Expose
    private String subtotal;
    @SerializedName("shipping_charge")
    @Expose
    private String shippingCharge;
    @SerializedName("shipping_method")
    @Expose
    private Integer shippingMethod;
    @SerializedName("freeshipping")
    @Expose
    private String freeshipping;
    @SerializedName("taxes_charge")
    @Expose
    private String taxesCharge;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("to_ship")
    @Expose
    private String toShip;
    @SerializedName("shipping_name")
    @Expose
    private String shippingName;
    @SerializedName("shipping_address")
    @Expose
    private String shippingAddress;
    @SerializedName("shipping_city")
    @Expose
    private String shippingCity;
    @SerializedName("shipping_pincode")
    @Expose
    private String shippingPincode;
    @SerializedName("billing_name")
    @Expose
    private String billingName;
    @SerializedName("billing_address")
    @Expose
    private String billingAddress;
    @SerializedName("billing_city")
    @Expose
    private String billingCity;
    @SerializedName("billing_pincode")
    @Expose
    private String billingPincode;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(String shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    public Integer getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(Integer shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getFreeshipping() {
        return freeshipping;
    }

    public void setFreeshipping(String freeshipping) {
        this.freeshipping = freeshipping;
    }

    public String getTaxesCharge() {
        return taxesCharge;
    }

    public void setTaxesCharge(String taxesCharge) {
        this.taxesCharge = taxesCharge;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getToShip() {
        return toShip;
    }

    public void setToShip(String toShip) {
        this.toShip = toShip;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public void setShippingCity(String shippingCity) {
        this.shippingCity = shippingCity;
    }

    public String getShippingPincode() {
        return shippingPincode;
    }

    public void setShippingPincode(String shippingPincode) {
        this.shippingPincode = shippingPincode;
    }

    public String getBillingName() {
        return billingName;
    }

    public void setBillingName(String billingName) {
        this.billingName = billingName;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBillingPincode() {
        return billingPincode;
    }

    public void setBillingPincode(String billingPincode) {
        this.billingPincode = billingPincode;
    }

}
