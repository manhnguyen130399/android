package freaktemplate.shopping;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.FirebaseApp;
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker;

import io.fabric.sdk.android.Fabric;
import java.util.List;

import freaktemplate.shopping.getset.Detail.Attribute;
import freaktemplate.shopping.getset.Detail.Attributename;
import freaktemplate.shopping.getset.Detail.Option;
import freaktemplate.shopping.getset.Detail.Optionvalue;
import freaktemplate.shopping.getset.Detail.Review;
import freaktemplate.shopping.getset.Filter.Brand;
import freaktemplate.shopping.getset.Filter.Color;
import freaktemplate.shopping.getset.OrderDetail.OrderDetails;
import freaktemplate.shopping.getset.OrderDetail.OrderStatusDetails;
import freaktemplate.shopping.utils.SaveValues;

public class MyApplication extends Application {

    private static MyApplication mInstance = null;

   /* static {
        System.loadLibrary("native-lib");
    }

    public native String stringFromJNI();

    public native String baseApiJNI();*/

    List<Option> optionlist;
    List<Optionvalue> colorlist, romlist, storagelist;
    List<Attribute> attributesList;
    List<Attributename> attributesnameList;
    List<Review> reviewList;
    List<Brand> brndlist;
    List<Color> colorList;
    List<String> priceList;
    List<String> sizeList;
    OrderDetails orderDetails;
    OrderStatusDetails orderStatusDetails;
    String sku;
    String category;
    String tag;
    String desc;
    String avg_star;
    String color;
    String ram;
    String rom;
    String product_id;
    String orderdate;
    String orderstatus;
    String product_price;
    String optionvalue1;
    String optionvalue2;
    String optionvalue3;

    public static MyApplication getmInstance() {
        return mInstance;
    }

    public static void setmInstance(MyApplication mInstance) {
        MyApplication.mInstance = mInstance;
    }

    public static MyApplication getInstance() {
        if (mInstance == null) {
            mInstance = new MyApplication();
        }
        return mInstance;
    }

    public String getOptionvalue1() {
        return optionvalue1;
    }

    public void setOptionvalue1(String optionvalue1) {
        this.optionvalue1 = optionvalue1;
    }

    public String getOptionvalue2() {
        return optionvalue2;
    }

    public void setOptionvalue2(String optionvalue2) {
        this.optionvalue2 = optionvalue2;
    }

    public String getOptionvalue3() {
        return optionvalue3;
    }

    public void setOptionvalue3(String optionvalue3) {
        this.optionvalue3 = optionvalue3;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public OrderStatusDetails getOrderStatusDetails() {
        return orderStatusDetails;
    }

    public void setOrderStatusDetails(OrderStatusDetails orderStatusDetails) {
        this.orderStatusDetails = orderStatusDetails;
    }

    public List<String> getSizeList() {
        return sizeList;
    }

    public void setSizeList(List<String> sizeList) {
        this.sizeList = sizeList;
    }

    public List<String> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<String> priceList) {
        this.priceList = priceList;
    }

    public List<Color> getColorList() {
        return colorList;
    }

    public void setColorList(List<Color> colorList) {
        this.colorList = colorList;
    }

    public List<Brand> getBrndlist() {
        return brndlist;
    }

    public void setBrndlist(List<Brand> brndlist) {
        this.brndlist = brndlist;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        InternetAvailabilityChecker.init(this);
        FirebaseApp.initializeApp(this);
//        SaveValues.adbaseConfig(this, baseApiJNI(),stringFromJNI());

    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getRom() {
        return rom;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }

    public String getAvg_star() {
        return avg_star;
    }

    public void setAvg_star(String avg_star) {
        this.avg_star = avg_star;
    }

    public List<Optionvalue> getStoragelist() {
        return storagelist;
    }

    public void setStoragelist(List<Optionvalue> storagelist) {
        this.storagelist = storagelist;
    }

    public List<Optionvalue> getColorlist() {
        return colorlist;
    }

    public void setColorlist(List<Optionvalue> colorlist) {
        this.colorlist = colorlist;
    }

    public List<Optionvalue> getRomlist() {
        return romlist;
    }

    public void setRomlist(List<Optionvalue> romlist) {
        this.romlist = romlist;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public List<Option> getOptionlist() {
        return optionlist;
    }

    public void setOptionlist(List<Option> optionlist) {
        this.optionlist = optionlist;
    }

    public List<Attribute> getAttributesList() {
        return attributesList;
    }

    public void setAttributesList(List<Attribute> attributesList) {
        this.attributesList = attributesList;
    }

    public List<Attributename> getAttributesnameList() {
        return attributesnameList;
    }

    public void setAttributesnameList(List<Attributename> attributesnameList) {
        this.attributesnameList = attributesnameList;
    }
}
