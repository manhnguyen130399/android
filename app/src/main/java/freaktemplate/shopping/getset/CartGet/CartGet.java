package freaktemplate.shopping.getset.CartGet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartGet {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
