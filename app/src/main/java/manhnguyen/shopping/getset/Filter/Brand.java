package manhnguyen.shopping.getset.Filter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Brand {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;

    /**
     * No args constructor for use in serialization
     */
    public Brand() {
    }

    /**
     * @param brandName
     * @param id
     * @param categoryId
     */
    public Brand(Integer id, String brandName, Integer categoryId) {
        super();
        this.id = id;
        this.brandName = brandName;
        this.categoryId = categoryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

}
