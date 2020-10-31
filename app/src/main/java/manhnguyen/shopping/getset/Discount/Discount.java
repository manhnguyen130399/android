package manhnguyen.shopping.getset.Discount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Discount {

    @SerializedName("discount_price")
    @Expose
    private String discountPrice;
    @SerializedName("freeshipping")
    @Expose
    private String freeshipping;

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getFreeshipping() {
        return freeshipping;
    }

    public void setFreeshipping(String freeshipping) {
        this.freeshipping = freeshipping;
    }

}
