package freaktemplate.shopping.getset.Offer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SensonalOffer {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("fixed_form")
    @Expose
    private Integer fixedForm;
    @SerializedName("fixed_to")
    @Expose
    private Integer fixedTo;
    @SerializedName("category")
    @Expose
    private Integer category;
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("start_date")
    @Expose
    private Object startDate;
    @SerializedName("end_date")
    @Expose
    private Object endDate;
    @SerializedName("sub_category")
    @Expose
    private Integer subCategory;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("is_active")
    @Expose
    private String isActive;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getFixedForm() {
        return fixedForm;
    }

    public void setFixedForm(Integer fixedForm) {
        this.fixedForm = fixedForm;
    }

    public Integer getFixedTo() {
        return fixedTo;
    }

    public void setFixedTo(Integer fixedTo) {
        this.fixedTo = fixedTo;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public Object getStartDate() {
        return startDate;
    }

    public void setStartDate(Object startDate) {
        this.startDate = startDate;
    }

    public Object getEndDate() {
        return endDate;
    }

    public void setEndDate(Object endDate) {
        this.endDate = endDate;
    }

    public Integer getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(Integer subCategory) {
        this.subCategory = subCategory;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

}
