package freaktemplate.shopping.getset.Offer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("offerdata")
    @Expose
    private Offerdata offerdata;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Offerdata getOfferdata() {
        return offerdata;
    }

    public void setOfferdata(Offerdata offerdata) {
        this.offerdata = offerdata;
    }

}
