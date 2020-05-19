package freaktemplate.shopping.getset.Orderlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("list")
    @Expose
    private java.util.List<SubList> subList = null;

    /**
     * No args constructor for use in serialization
     */
    public Order() {
    }

    /**
     * @param name
     * @param subList
     */
    public Order(String name, java.util.List<SubList> subList) {
        super();
        this.name = name;
        this.subList = subList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.util.List<SubList> getSubList() {
        return subList;
    }

    public void setSubList(java.util.List<SubList> subList) {
        this.subList = subList;
    }

}
