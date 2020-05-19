package freaktemplate.shopping.getset.bestseling;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BestSellingpojo {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("product")
    @Expose
    private Product product;

    /**
     * No args constructor for use in serialization
     */
    public BestSellingpojo() {
    }

    /**
     * @param product
     * @param status
     */
    public BestSellingpojo(Integer status, Product product) {
        super();
        this.status = status;
        this.product = product;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
