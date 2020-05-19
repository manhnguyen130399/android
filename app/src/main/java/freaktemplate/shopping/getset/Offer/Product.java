package freaktemplate.shopping.getset.Offer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("MRP")
    @Expose
    private String mRP;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("basic_image")
    @Expose
    private String basicImage;
    @SerializedName("total_review")
    @Expose
    private Integer totalReview;
    @SerializedName("avgStar")
    @Expose
    private Integer avgStar;
    @SerializedName("wish")
    @Expose
    private Integer wish;
    @SerializedName("price")
    @Expose
    private String price;

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

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getBasicImage() {
        return basicImage;
    }

    public void setBasicImage(String basicImage) {
        this.basicImage = basicImage;
    }

    public Integer getTotalReview() {
        return totalReview;
    }

    public void setTotalReview(Integer totalReview) {
        this.totalReview = totalReview;
    }

    public Integer getAvgStar() {
        return avgStar;
    }

    public void setAvgStar(Integer avgStar) {
        this.avgStar = avgStar;
    }

    public Integer getWish() {
        return wish;
    }

    public void setWish(Integer wish) {
        this.wish = wish;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
