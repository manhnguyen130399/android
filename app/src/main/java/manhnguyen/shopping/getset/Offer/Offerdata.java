package manhnguyen.shopping.getset.Offer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Offerdata {

    @SerializedName("big_offer")
    @Expose
    private List<BigOffer> bigOffer = null;
    @SerializedName("normal_offer")
    @Expose
    private List<NormalOffer> normalOffer = null;
    @SerializedName("product")
    @Expose
    private List<Product> product = null;
    @SerializedName("sensonal_offer")
    @Expose
    private SensonalOffer sensonalOffer;

    public List<BigOffer> getBigOffer() {
        return bigOffer;
    }

    public void setBigOffer(List<BigOffer> bigOffer) {
        this.bigOffer = bigOffer;
    }

    public List<NormalOffer> getNormalOffer() {
        return normalOffer;
    }

    public void setNormalOffer(List<NormalOffer> normalOffer) {
        this.normalOffer = normalOffer;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public SensonalOffer getSensonalOffer() {
        return sensonalOffer;
    }

    public void setSensonalOffer(SensonalOffer sensonalOffer) {
        this.sensonalOffer = sensonalOffer;
    }

}
