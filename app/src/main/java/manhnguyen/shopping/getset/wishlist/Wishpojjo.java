package manhnguyen.shopping.getset.wishlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Wishpojjo {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("Wish")
    @Expose
    private List<Wish> wish = null;

    /**
     * No args constructor for use in serialization
     */
    public Wishpojjo() {
    }

    /**
     * @param wish
     * @param status
     */
    public Wishpojjo(Integer status, List<Wish> wish) {
        super();
        this.status = status;
        this.wish = wish;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Wish> getWish() {
        return wish;
    }

    public void setWish(List<Wish> wish) {
        this.wish = wish;
    }

    public class Wish {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("product_id")
        @Expose
        private Integer productId;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("product")
        @Expose
        private Product product;

        /**
         * No args constructor for use in serialization
         */
        public Wish() {
        }

        /**
         * @param createdAt
         * @param product
         * @param productId
         * @param id
         * @param userId
         * @param updatedAt
         */
        public Wish(Integer id, Integer userId, Integer productId, String createdAt, String updatedAt, Product product) {
            super();
            this.id = id;
            this.userId = userId;
            this.productId = productId;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.product = product;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
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

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

    }

    public class Product {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("basic_image")
        @Expose
        private String basicImage;
        @SerializedName("selling_price")
        @Expose
        private Integer discount;

        /**
         * No args constructor for use in serialization
         */
        public Product() {
        }

        /**
         * @param basicImage
         * @param name
         * @param discount
         * @param id
         */
        public Product(Integer id, String name, String basicImage, Integer discount) {
            super();
            this.id = id;
            this.name = name;
            this.basicImage = basicImage;
            this.discount = discount;
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

        public String getBasicImage() {
            return basicImage;
        }

        public void setBasicImage(String basicImage) {
            this.basicImage = basicImage;
        }

        public Integer getDiscount() {
            return discount;
        }

        public void setDiscount(Integer discount) {
            this.discount = discount;
        }

    }

}
