package freaktemplate.shopping.getset.offerlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Offerpojjo {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("offers")
    @Expose
    private List<Offer> offers = null;

    /**
     * No args constructor for use in serialization
     */
    public Offerpojjo() {
    }

    /**
     * @param offers
     * @param status
     */
    public Offerpojjo(Integer status, List<Offer> offers) {
        super();
        this.status = status;
        this.offers = offers;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

}
