package manhnguyen.shopping.getset.Detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Attribute {

    @SerializedName("set")
    @Expose
    private String set;
    @SerializedName("attributename")
    @Expose
    private List<Attributename> attributename = null;

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public List<Attributename> getAttributename() {
        return attributename;
    }

    public void setAttributename(List<Attributename> attributename) {
        this.attributename = attributename;
    }

}
