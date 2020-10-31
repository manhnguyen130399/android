package manhnguyen.shopping.getset.Filter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Details {

    @SerializedName("subcategory")
    @Expose
    private List<Subcategory> subcategory = null;
    @SerializedName("brand")
    @Expose
    private List<Brand> brand = null;
    @SerializedName("product")
    @Expose
    private Product product;
    @SerializedName("pricelist")
    @Expose
    private List<String> pricelist = null;
    @SerializedName("color")
    @Expose
    private List<Color> color = null;
    @SerializedName("size")
    @Expose
    private List<String> size = null;

    /**
     * No args constructor for use in serialization
     */
    public Details() {
    }

    /**
     * @param pricelist
     * @param product
     * @param color
     * @param size
     * @param subcategory
     * @param brand
     */
    public Details(List<Subcategory> subcategory, List<Brand> brand, Product product, List<String> pricelist, List<Color> color, List<String> size) {
        super();
        this.subcategory = subcategory;
        this.brand = brand;
        this.product = product;
        this.pricelist = pricelist;
        this.color = color;
        this.size = size;
    }

    public List<Subcategory> getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(List<Subcategory> subcategory) {
        this.subcategory = subcategory;
    }

    public List<Brand> getBrand() {
        return brand;
    }

    public void setBrand(List<Brand> brand) {
        this.brand = brand;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<String> getPricelist() {
        return pricelist;
    }

    public void setPricelist(List<String> pricelist) {
        this.pricelist = pricelist;
    }

    public List<Color> getColor() {
        return color;
    }

    public void setColor(List<Color> color) {
        this.color = color;
    }

    public List<String> getSize() {
        return size;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }

}
