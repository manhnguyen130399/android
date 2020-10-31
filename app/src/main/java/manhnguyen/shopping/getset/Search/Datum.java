package manhnguyen.shopping.getset.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("MRP")
    @Expose
    private String mRP;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("basic_image")
    @Expose
    private String basicImage;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("product_color")
    @Expose
    private String productColor;
    @SerializedName("ratting")
    @Expose
    private Object ratting;
    @SerializedName("wish")
    @Expose
    private Integer wish;
    @SerializedName("totalreview")
    @Expose
    private Integer totalreview;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMRP() {
        return mRP;
    }

    public void setMRP(String mRP) {
        this.mRP = mRP;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBasicImage() {
        return basicImage;
    }

    public void setBasicImage(String basicImage) {
        this.basicImage = basicImage;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public Object getRatting() {
        return ratting;
    }

    public void setRatting(Object ratting) {
        this.ratting = ratting;
    }

    public Integer getWish() {
        return wish;
    }

    public void setWish(Integer wish) {
        this.wish = wish;
    }

    public Integer getTotalreview() {
        return totalreview;
    }

    public void setTotalreview(Integer totalreview) {
        this.totalreview = totalreview;
    }

}
