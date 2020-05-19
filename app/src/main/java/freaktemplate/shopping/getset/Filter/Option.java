package freaktemplate.shopping.getset.Filter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("is_required")
    @Expose
    private String isRequired;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("price_type")
    @Expose
    private String priceType;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    /**
     * No args constructor for use in serialization
     */
    public Option() {
    }

    /**
     * @param isRequired
     * @param createdAt
     * @param productId
     * @param price
     * @param name
     * @param priceType
     * @param id
     * @param label
     * @param type
     * @param updatedAt
     */
    public Option(Integer id, Integer productId, String name, String type, String isRequired, String label, String price, String priceType, String createdAt, String updatedAt) {
        super();
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.type = type;
        this.isRequired = isRequired;
        this.label = label;
        this.price = price;
        this.priceType = priceType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
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

}
