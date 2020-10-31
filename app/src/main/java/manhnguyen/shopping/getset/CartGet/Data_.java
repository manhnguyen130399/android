package manhnguyen.shopping.getset.CartGet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data_ {

    @SerializedName("cartdata")
    @Expose
    private List<Cartdatum> cartdata = null;
    @SerializedName("shipping")
    @Expose
    private List<Shipping> shipping = null;
    @SerializedName("totalcart")
    @Expose
    private String totalcart;

    public List<Cartdatum> getCartdata() {
        return cartdata;
    }

    public void setCartdata(List<Cartdatum> cartdata) {
        this.cartdata = cartdata;
    }

    public List<Shipping> getShipping() {
        return shipping;
    }

    public void setShipping(List<Shipping> shipping) {
        this.shipping = shipping;
    }

    public String getTotalcart() {
        return totalcart;
    }

    public void setTotalcart(String totalcart) {
        this.totalcart = totalcart;
    }
}
