package freaktemplate.shopping.getset.Detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profilepojo {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("offers")
    @Expose
    private Offers offers;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Offers getOffers() {
        return offers;
    }

    public void setOffers(Offers offers) {
        this.offers = offers;
    }

}
