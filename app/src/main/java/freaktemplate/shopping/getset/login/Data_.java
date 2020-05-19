
package freaktemplate.shopping.getset.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data_ {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("soical_id")
    @Expose
    private String soicalId;
    @SerializedName("login_type")
    @Expose
    private String loginType;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("billing_address")
    @Expose
    private String billingAddress;
    @SerializedName("shipping_address")
    @Expose
    private String shippingAddress;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;
    @SerializedName("permissions")
    @Expose
    private String permissions;
    @SerializedName("last_login")
    @Expose
    private String lastLogin;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("is_email_verified")
    @Expose
    private String isEmailVerified;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("cart")
    @Expose
    private Integer cart;
    @SerializedName("totalwish")
    @Expose
    private Integer totalwish;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSoicalId() {
        return soicalId;
    }

    public void setSoicalId(String soicalId) {
        this.soicalId = soicalId;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getIsEmailVerified() {
        return isEmailVerified;
    }

    public void setIsEmailVerified(String isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
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

    public Integer getCart() {
        return cart;
    }

    public void setCart(Integer cart) {
        this.cart = cart;
    }

    public Integer getTotalwish() {
        return totalwish;
    }

    public void setTotalwish(Integer totalwish) {
        this.totalwish = totalwish;
    }

}
