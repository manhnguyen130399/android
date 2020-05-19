package freaktemplate.shopping.getset.offerlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Offer {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("offer_type")
    @Expose
    private String offerType;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("main_title")
    @Expose
    private String mainTitle;
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("fixed")
    @Expose
    private String fixed;
    @SerializedName("is_product")
    @Expose
    private String isProduct;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("coupon_id")
    @Expose
    private Integer coupon_id;

    public Integer getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(Integer coupon_id) {
        this.coupon_id = coupon_id;
    }

    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("new_price")
    @Expose
    private String newPrice;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("is_active")
    @Expose
    private String isActive;

    /**
     * No args constructor for use in serialization
     */
    public Offer() {
    }

    /**
     * @param productId
     * @param endDate
     * @param banner
     * @param title
     * @param isActive
     * @param newPrice
     * @param offerType
     * @param createdAt
     * @param mainTitle
     * @param fixed
     * @param isProduct
     * @param id
     * @param startDate
     * @param categoryId
     * @param updatedAt
     */
    public Offer(Integer id, String offerType, String title, String mainTitle, String banner, String startDate, String endDate, String fixed, String isProduct, Integer productId, Integer categoryId, String newPrice, String createdAt, String updatedAt, String isActive) {
        super();
        this.id = id;
        this.offerType = offerType;
        this.title = title;
        this.mainTitle = mainTitle;
        this.banner = banner;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fixed = fixed;
        this.isProduct = isProduct;
        this.productId = productId;
        this.categoryId = categoryId;
        this.newPrice = newPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getFixed() {
        return fixed;
    }

    public void setFixed(String fixed) {
        this.fixed = fixed;
    }

    public String getIsProduct() {
        return isProduct;
    }

    public void setIsProduct(String isProduct) {
        this.isProduct = isProduct;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
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
