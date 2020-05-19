package freaktemplate.shopping.getset.bestseling;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Productdetail {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("MRP")
    @Expose
    private String mRP;
    @SerializedName("ratting")
    @Expose
    private Integer ratting;
    @SerializedName("totalreview")
    @Expose
    private Integer totalreview;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("wish")
    @Expose
    private Integer wish;
    @SerializedName("discount")
    @Expose
    private Integer discount;

    /**
     * No args constructor for use in serialization
     */
    public Productdetail() {
    }

    /**
     * @param wish
     * @param image
     * @param totalreview
     * @param ratting
     * @param price
     * @param name
     * @param discount
     * @param mRP
     * @param id
     */
    public Productdetail(Integer id, String name, String image, String mRP, Integer ratting, Integer totalreview, String price, Integer wish, Integer discount) {
        super();
        this.id = id;
        this.name = name;
        this.image = image;
        this.mRP = mRP;
        this.ratting = ratting;
        this.totalreview = totalreview;
        this.price = price;
        this.wish = wish;
        this.discount = discount;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMRP() {
        return mRP;
    }

    public void setMRP(String mRP) {
        this.mRP = mRP;
    }

    public Integer getRatting() {
        return ratting;
    }

    public void setRatting(Integer ratting) {
        this.ratting = ratting;
    }

    public Integer getTotalreview() {
        return totalreview;
    }

    public void setTotalreview(Integer totalreview) {
        this.totalreview = totalreview;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getWish() {
        return wish;
    }

    public void setWish(Integer wish) {
        this.wish = wish;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

}
