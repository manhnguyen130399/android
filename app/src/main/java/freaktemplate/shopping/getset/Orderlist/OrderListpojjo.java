package freaktemplate.shopping.getset.Orderlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderListpojjo {

    @SerializedName("data")
    @Expose
    private Data data;

    /**
     * No args constructor for use in serialization
     */
    public OrderListpojjo() {
    }

    /**
     * @param data
     */
    public OrderListpojjo(Data data) {
        super();
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
