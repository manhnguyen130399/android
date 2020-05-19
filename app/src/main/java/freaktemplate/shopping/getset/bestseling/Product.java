package freaktemplate.shopping.getset.bestseling;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("totalwish")
    @Expose
    private Integer totalwish;
    @SerializedName("carttotal")
    @Expose
    private Integer carttotal;

    /**
     * No args constructor for use in serialization
     */
    public Product() {
    }

    /**
     * @param data
     * @param totalwish
     */
    public Product(Data data, Integer totalwish) {
        super();
        this.data = data;
        this.totalwish = totalwish;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getTotalwish() {
        return totalwish;
    }

    public void setTotalwish(Integer totalwish) {
        this.totalwish = totalwish;
    }

    public Integer getCarttotal() {
        return carttotal;
    }

    public void setCarttotal(Integer carttotal) {
        this.carttotal = carttotal;
    }
}
