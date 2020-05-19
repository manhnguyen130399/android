package freaktemplate.shopping.getset.Detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Userdata {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("facebook_id")
    @Expose
    private Object facebookId;
    @SerializedName("google_id")
    @Expose
    private Object googleId;
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
    private Object profilePic;
    @SerializedName("permissions")
    @Expose
    private Object permissions;
    @SerializedName("last_login")
    @Expose
    private String lastLogin;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("phone")
    @Expose
    private Object phone;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(Object facebookId) {
        this.facebookId = facebookId;
    }

    public Object getGoogleId() {
        return googleId;
    }

    public void setGoogleId(Object googleId) {
        this.googleId = googleId;
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

    public Object getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Object profilePic) {
        this.profilePic = profilePic;
    }

    public Object getPermissions() {
        return permissions;
    }

    public void setPermissions(Object permissions) {
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

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
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

}
