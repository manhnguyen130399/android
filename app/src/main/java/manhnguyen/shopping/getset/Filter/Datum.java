package manhnguyen.shopping.getset.Filter;

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
    private String ratting;
    @SerializedName("wish")
    @Expose
    private Integer wish;
    @SerializedName("totalreview")
    @Expose
    private Integer totalreview;

    /**
     * No args constructor for use in serialization
     */
    public Datum() {
    }

    /**
     * @param wish
     * @param totalreview
     * @param basicImage
     * @param price
     * @param ratting
     * @param name
     * @param productColor
     * @param discount
     * @param mRP
     * @param id
     */
    public Datum(Integer id, String name, String mRP, String price, String basicImage, Integer discount, String productColor, String ratting, Integer wish, Integer totalreview) {
        super();
        this.id = id;
        this.name = name;
        this.mRP = mRP;
        this.price = price;
        this.basicImage = basicImage;
        this.discount = discount;
        this.productColor = productColor;
        this.ratting = ratting;
        this.wish = wish;
        this.totalreview = totalreview;
    }

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

    public String getRatting() {
        return ratting;
    }

    public void setRatting(String ratting) {
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
