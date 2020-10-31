package manhnguyen.shopping.getset.Filter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subcategory {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("parent_category")
    @Expose
    private Integer parentCategory;

    /**
     * No args constructor for use in serialization
     */
    public Subcategory() {
    }

    /**
     * @param name
     * @param parentCategory
     * @param id
     */
    public Subcategory(Integer id, String name, Integer parentCategory) {
        super();
        this.id = id;
        this.name = name;
        this.parentCategory = parentCategory;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Integer parentCategory) {
        this.parentCategory = parentCategory;
    }

}
