package freaktemplate.shopping.getset.bestseling;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("productdetail")
    @Expose
    private Productdetail productdetail;

    private int ads;

    /**
     * No args constructor for use in serialization
     */
    public Datum() {
    }

    /**
     * @param productdetail
     * @param total
     * @param productId
     */
    public Datum(Integer productId, Integer total, Productdetail productdetail) {
        super();
        this.productId = productId;
        this.total = total;
        this.productdetail = productdetail;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Productdetail getProductdetail() {
        return productdetail;
    }

    public void setProductdetail(Productdetail productdetail) {
        this.productdetail = productdetail;
    }

    public int getAds() {
        return ads;
    }

    public void setAds(int ads) {
        this.ads = ads;
    }
}
