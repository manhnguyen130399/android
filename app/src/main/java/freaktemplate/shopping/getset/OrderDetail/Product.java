package freaktemplate.shopping.getset.OrderDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("basic_image")
    @Expose
    private String basicImage;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("amount")
    @Expose
    private String amount;

    public String getBasicImage() {
        return basicImage;
    }

    public void setBasicImage(String basicImage) {
        this.basicImage = basicImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
