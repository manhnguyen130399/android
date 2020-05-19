package freaktemplate.shopping.getset.Detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Optionvalue {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("price")
    @Expose
    private String price;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
