package manhnguyen.shopping.getset.Filter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FilterPojo {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("details")
    @Expose
    private Details details;

    /**
     * No args constructor for use in serialization
     */
    public FilterPojo() {
    }

    /**
     * @param details
     * @param status
     */
    public FilterPojo(Integer status, Details details) {
        super();
        this.status = status;
        this.details = details;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

}
