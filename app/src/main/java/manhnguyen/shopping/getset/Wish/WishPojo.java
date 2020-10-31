package manhnguyen.shopping.getset.Wish;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WishPojo {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("remove")
    @Expose
    private String remove;
    @SerializedName("wish")
    @Expose
    private Integer wish;

    public Integer getWish() {
        return wish;
    }

    public void setWish(Integer wish) {
        this.wish = wish;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemove() {
        return remove;
    }

    public void setRemove(String remove) {
        this.remove = remove;
    }

}
