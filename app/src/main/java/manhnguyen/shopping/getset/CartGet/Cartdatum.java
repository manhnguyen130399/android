package manhnguyen.shopping.getset.CartGet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cartdatum {

    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("option")
    @Expose
    private String option;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("tax_name")
    @Expose
    private Object taxName;
    @SerializedName("tax")
    @Expose
    private Object tax;
    @SerializedName("cart_id")
    @Expose
    private Integer cart_id;

    public Integer getCart_id() {
        return cart_id;
    }

    public void setCart_id(Integer cart_id) {
        this.cart_id = cart_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Object getTaxName() {
        return taxName;
    }

    public void setTaxName(Object taxName) {
        this.taxName = taxName;
    }

    public Object getTax() {
        return tax;
    }

    public void setTax(Object tax) {
        this.tax = tax;
    }

}
