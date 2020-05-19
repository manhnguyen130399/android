package freaktemplate.shopping.getset.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Categorypojjo {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    /**
     * No args constructor for use in serialization
     */
    public Categorypojjo() {
    }

    /**
     * @param data
     * @param status
     */
    public Categorypojjo(Integer status, List<Datum> data) {
        super();
        this.status = status;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public static class Offer {

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
        private Object productId;
        @SerializedName("category_id")
        @Expose
        private Integer categoryId;
        @SerializedName("new_price")
        @Expose
        private Object newPrice;
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
        public Offer(Integer id, String offerType, String title, String mainTitle, String banner, String startDate, String endDate, String fixed, String isProduct, Object productId, Integer categoryId, Object newPrice, String createdAt, String updatedAt, String isActive) {
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

        public Object getProductId() {
            return productId;
        }

        public void setProductId(Object productId) {
            this.productId = productId;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public Object getNewPrice() {
            return newPrice;
        }

        public void setNewPrice(Object newPrice) {
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

    public class Datum {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("parent_category")
        @Expose
        private Integer parentCategory;
        @SerializedName("is_active")
        @Expose
        private String isActive;
        @SerializedName("is_delete")
        @Expose
        private String isDelete;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("offers")
        @Expose
        private List<Offer> offers = null;
        @SerializedName("subcategory")
        @Expose
        private List<Subcategory> subcategory = null;

        /**
         * No args constructor for use in serialization
         */
        public Datum() {
        }

        /**
         * @param offers
         * @param createdAt
         * @param isDelete
         * @param name
         * @param parentCategory
         * @param id
         * @param isActive
         * @param subcategory
         * @param updatedAt
         */
        public Datum(Integer id, String name, Integer parentCategory, String isActive, String isDelete, String createdAt, String updatedAt, List<Offer> offers, List<Subcategory> subcategory) {
            super();
            this.id = id;
            this.name = name;
            this.parentCategory = parentCategory;
            this.isActive = isActive;
            this.isDelete = isDelete;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.offers = offers;
            this.subcategory = subcategory;
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

        public String getIsActive() {
            return isActive;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }

        public String getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
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

        public List<Offer> getOffers() {
            return offers;
        }

        public void setOffers(List<Offer> offers) {
            this.offers = offers;
        }

        public List<Subcategory> getSubcategory() {
            return subcategory;
        }

        public void setSubcategory(List<Subcategory> subcategory) {
            this.subcategory = subcategory;
        }
    }

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
        @SerializedName("is_active")
        @Expose
        private String isActive;
        @SerializedName("is_delete")
        @Expose
        private String isDelete;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        /**
         * No args constructor for use in serialization
         */
        public Subcategory() {
        }

        /**
         * @param createdAt
         * @param isDelete
         * @param name
         * @param parentCategory
         * @param id
         * @param isActive
         * @param updatedAt
         */
        public Subcategory(Integer id, String name, Integer parentCategory, String isActive, String isDelete, String createdAt, String updatedAt) {
            super();
            this.id = id;
            this.name = name;
            this.parentCategory = parentCategory;
            this.isActive = isActive;
            this.isDelete = isDelete;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
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

        public String getIsActive() {
            return isActive;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }

        public String getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
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
}
