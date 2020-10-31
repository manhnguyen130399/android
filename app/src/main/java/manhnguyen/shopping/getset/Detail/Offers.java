package manhnguyen.shopping.getset.Detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Offers {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("subcategory")
    @Expose
    private String subcategory;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("tax_class")
    @Expose
    private String taxClass;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("MRP")
    @Expose
    private String mRP;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("special_price")
    @Expose
    private Object specialPrice;
    @SerializedName("special_price_start")
    @Expose
    private Object specialPriceStart;
    @SerializedName("special_price_to")
    @Expose
    private Object specialPriceTo;
    @SerializedName("sku")
    @Expose
    private Object sku;
    @SerializedName("inventory")
    @Expose
    private String inventory;
    @SerializedName("stock")
    @Expose
    private String stock;
    @SerializedName("basic_image")
    @Expose
    private String basicImage;
    @SerializedName("additional_image")
    @Expose
    private String additionalImage;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("meta_title")
    @Expose
    private Object metaTitle;
    @SerializedName("meta_keyword")
    @Expose
    private Object metaKeyword;
    @SerializedName("meta_description")
    @Expose
    private Object metaDescription;
    @SerializedName("related_product")
    @Expose
    private Object relatedProduct;
    @SerializedName("up_sells")
    @Expose
    private Object upSells;
    @SerializedName("cross_sells")
    @Expose
    private Object crossSells;
    @SerializedName("short_description")
    @Expose
    private Object shortDescription;
    @SerializedName("product_new_from")
    @Expose
    private Object productNewFrom;
    @SerializedName("product_new_to")
    @Expose
    private Object productNewTo;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("wish")
    @Expose
    private String wish;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("total_review")
    @Expose
    private String total_review;
    @SerializedName("is_deleted")
    @Expose
    private String isDeleted;
    @SerializedName("attributes")
    @Expose
    private List<Attribute> attributes = null;
    @SerializedName("options")
    @Expose
    private List<Option> options = null;
    @SerializedName("review")
    @Expose
    private List<Review> review = null;
    @SerializedName("avgStar")
    @Expose
    private Integer avgStar;


    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getmRP() {
        return mRP;
    }

    public void setmRP(String mRP) {
        this.mRP = mRP;
    }

    public String getWish() {
        return wish;
    }

    public void setWish(String wish) {
        this.wish = wish;
    }

    public String getTotal_review() {
        return total_review;
    }

    public void setTotal_review(String total_review) {
        this.total_review = total_review;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTaxClass() {
        return taxClass;
    }

    public void setTaxClass(String taxClass) {
        this.taxClass = taxClass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMRP() {
        return mRP;
    }

    public void setMRP(String mRP) {
        this.mRP = mRP;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Object getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(Object specialPrice) {
        this.specialPrice = specialPrice;
    }

    public Object getSpecialPriceStart() {
        return specialPriceStart;
    }

    public void setSpecialPriceStart(Object specialPriceStart) {
        this.specialPriceStart = specialPriceStart;
    }

    public Object getSpecialPriceTo() {
        return specialPriceTo;
    }

    public void setSpecialPriceTo(Object specialPriceTo) {
        this.specialPriceTo = specialPriceTo;
    }

    public Object getSku() {
        return sku;
    }

    public void setSku(Object sku) {
        this.sku = sku;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getBasicImage() {
        return basicImage;
    }

    public void setBasicImage(String basicImage) {
        this.basicImage = basicImage;
    }

    public String getAdditionalImage() {
        return additionalImage;
    }

    public void setAdditionalImage(String additionalImage) {
        this.additionalImage = additionalImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(Object metaTitle) {
        this.metaTitle = metaTitle;
    }

    public Object getMetaKeyword() {
        return metaKeyword;
    }

    public void setMetaKeyword(Object metaKeyword) {
        this.metaKeyword = metaKeyword;
    }

    public Object getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(Object metaDescription) {
        this.metaDescription = metaDescription;
    }

    public Object getRelatedProduct() {
        return relatedProduct;
    }

    public void setRelatedProduct(Object relatedProduct) {
        this.relatedProduct = relatedProduct;
    }

    public Object getUpSells() {
        return upSells;
    }

    public void setUpSells(Object upSells) {
        this.upSells = upSells;
    }

    public Object getCrossSells() {
        return crossSells;
    }

    public void setCrossSells(Object crossSells) {
        this.crossSells = crossSells;
    }

    public Object getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(Object shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Object getProductNewFrom() {
        return productNewFrom;
    }

    public void setProductNewFrom(Object productNewFrom) {
        this.productNewFrom = productNewFrom;
    }

    public Object getProductNewTo() {
        return productNewTo;
    }

    public void setProductNewTo(Object productNewTo) {
        this.productNewTo = productNewTo;
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

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public List<Review> getReview() {
        return review;
    }

    public void setReview(List<Review> review) {
        this.review = review;
    }

    public Integer getAvgStar() {
        return avgStar;
    }

    public void setAvgStar(Integer avgStar) {
        this.avgStar = avgStar;
    }

}
