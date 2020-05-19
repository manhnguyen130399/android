package freaktemplate.shopping.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.androidkun.xtablayout.XTabLayout;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interceptors.GzipRequestInterceptor;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nshmura.snappysmoothscroller.SnappyLinearLayoutManager;
import com.willy.ratingbar.ScaleRatingBar;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freaktemplate.shopping.MyApplication;
import freaktemplate.shopping.R;
import freaktemplate.shopping.activity.HomeActivity;
import freaktemplate.shopping.adapter.ImageAdapter;
import freaktemplate.shopping.getset.Cart.CartPojo;
import freaktemplate.shopping.getset.Detail.Attribute;
import freaktemplate.shopping.getset.Detail.Detailpojo;
import freaktemplate.shopping.getset.Detail.Offers;
import freaktemplate.shopping.getset.Detail.Option;
import freaktemplate.shopping.getset.Detail.Review;
import freaktemplate.shopping.getset.Wish.WishPojo;
import freaktemplate.shopping.utils.SPmanager;
import freaktemplate.shopping.utils.UtilHelper;
import freaktemplate.shopping.utils.WrapContentHeightViewPager;
import me.relex.circleindicator.CircleIndicator2;
import okhttp3.OkHttpClient;

@SuppressLint("StaticFieldLeak")
public class DetailFragment extends Fragment {
    private static final String TAG = "DetailFragment";
    @SuppressLint("StaticFieldLeak")
    public static TextView txt_price;
    private WrapContentHeightViewPager viewpager1;
    private RecyclerView recycle_img;
    private XTabLayout tabs;
    private List<String> imagelist;
    private ImageView ic_back;
    private TextView txt_original;
    private TextView txt_discount;
    private ScaleRatingBar rate_detail;
    private List<String> list;
    private MyApplication myApp;
    private CircleIndicator2 indicator;
    private View rootview;
    private String itemID;
    private String name;
    private String price;
    private String image;
    private TextView txt_itemname;
    private TextView txt_totalreview;
    private Gson gson;
    private TextView add_to_cart;
    private TextView txt_buynow;
    private Context context;
    private ImageView ic_like;
    private String userID;
    private ArrayList<String> labelist;
    private ArrayList<String> optionlist1;
    private OkHttpClient okHttpClient;
    private String label;
    private String option;
    private String category = String.valueOf(1);
    private String subcategory = String.valueOf(0);
    private String brand_id = String.valueOf(0);
    private String price_id = String.valueOf(0);
    private String discount_id = String.valueOf(0);
    private String rating_id = String.valueOf(0);
    private String filter_id = String.valueOf(0);
    private String color_id = String.valueOf(0);
    private String size_id = String.valueOf(0);
    private String coupon_id = String.valueOf(0);
    private String mainCat;
    private String lay_out = "detail";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.activity_detail, container, false);
        context = rootview.getContext();
        myApp = MyApplication.getInstance();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder
                .registerTypeAdapterFactory(UtilHelper.UNRELIABLE_INTEGER_FACTORY)
                .create();
        okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(new GzipRequestInterceptor())
                .build();
        userID = SPmanager.getPreference(context, "userID");
        if (userID != null) {
            if (userID.equals("null")) {
                userID = "0";
            }
        } else {
            userID = "0";
        }
        inite();
        getList();
        return rootview;
    }


    @Override
    public void onStart() {
        super.onStart();
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            EventBus.getDefault().unregister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (myApp != null) {
            if (myApp.getOptionlist() != null) {
                myApp.getOptionlist().clear();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        rootview.setFocusableInTouchMode(true);
        rootview.requestFocus();
        rootview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    Map<String, String> data = new HashMap<>();
                    data.put("itemID", itemID);
                    data.put("name", name);
                    data.put("priceID", price);
                    data.put("image", image);
                    data.put("category", category);
                    data.put("subcategory", subcategory);
                    data.put("brand", brand_id);
                    data.put("price", price_id);
                    data.put("discount", discount_id);
                    data.put("ratting", rating_id);
                    data.put("filter", filter_id);
                    data.put("color", color_id);
                    data.put("size", size_id);
                    data.put("mainCat", mainCat);
                    data.put("layout", lay_out);
                    data.put("coupon_id", coupon_id);
                    EventBus.getDefault().postSticky(data);
                    assert getFragmentManager() != null;
                    getFragmentManager().popBackStack();
                    return true;
                    // handle back button
                }
                return false;
            }
        });
    }

    private void inite() {
        imagelist = new ArrayList<>();
        recycle_img = rootview.findViewById(R.id.viewpager);
        txt_original = rootview.findViewById(R.id.txt_original);
        indicator = rootview.findViewById(R.id.indicator);
        rate_detail = rootview.findViewById(R.id.rate_detail);
        viewpager1 = rootview.findViewById(R.id.viewpager1);
        txt_itemname = rootview.findViewById(R.id.txt_itemname);
        add_to_cart = rootview.findViewById(R.id.add_to_cart);
        txt_totalreview = rootview.findViewById(R.id.txt_totalreview);
        txt_discount = rootview.findViewById(R.id.txt_discount);
        txt_buynow = rootview.findViewById(R.id.txt_buynow);
        tabs = rootview.findViewById(R.id.tabs);
        ic_like = rootview.findViewById(R.id.ic_like);
        txt_price = rootview.findViewById(R.id.txt_price);
        ic_back = rootview.findViewById(R.id.ic_back);
        labelist = new ArrayList<>();
        optionlist1 = new ArrayList<>();
        myApp.setProduct_id(itemID);
        txt_original.setPaintFlags(txt_original.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        clickevent();
        try {
            Map<String, String> data = EventBus.getDefault().getStickyEvent(HashMap.class);
            category = data.get("category");
            subcategory = data.get("subcategory");
            brand_id = data.get("brand");
            price_id = data.get("price");
            discount_id = data.get("discount");
            rating_id = data.get("ratting");
            filter_id = data.get("filter");
            color_id = data.get("color");
            size_id = data.get("size");
            mainCat = data.get("mainCat");
            lay_out = data.get("layout");
            itemID = data.get("itemID");
            name = data.get("name");
            price = data.get("priceID");
            image = data.get("image");
            coupon_id = data.get("coupon_id");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clickevent() {
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });
        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userID.equals("0")) {
                    Showdialog();
                } else {
                    String color = myApp.getColor();
                    String ram = myApp.getRam();
                    String storage = myApp.getRom();
                    if (color != null) {
                        optionlist1.add(myApp.getOptionvalue3());
                        option = TextUtils.join(",", optionlist1);
                        labelist.add(color);
                        label = TextUtils.join(",", labelist);
                    }
                    if (ram != null) {
                        optionlist1.add(myApp.getOptionvalue1());
                        option = TextUtils.join(",", optionlist1);
                        labelist.add(ram);
                        label = TextUtils.join(",", labelist);
                    }
                    if (storage != null) {
                        optionlist1.add(myApp.getOptionvalue2());
                        option = TextUtils.join(",", optionlist1);
                        labelist.add(storage);
                        label = TextUtils.join(",", labelist);
                    }
                    Addcart(label, option, "addtocart");
                }
            }
        });
        txt_buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userID.equals("0")) {
                    Showdialog();
                } else {
                    String color = myApp.getColor();
                    String ram = myApp.getRam();
                    String storage = myApp.getRom();
                    if (color != null) {
                        optionlist1.add(myApp.getOptionvalue3());
                        option = TextUtils.join(",", optionlist1);
                        labelist.add(color);
                        label = TextUtils.join(",", labelist);
                    }
                    if (ram != null) {
                        optionlist1.add(myApp.getOptionvalue1());
                        option = TextUtils.join(",", optionlist1);
                        labelist.add(ram);
                        label = TextUtils.join(",", labelist);
                    }
                    if (storage != null) {
                        optionlist1.add(myApp.getOptionvalue2());
                        option = TextUtils.join(",", optionlist1);
                        labelist.add(storage);
                        label = TextUtils.join(",", labelist);
                    }
                    Addcart(label, option, "buynow");
                }
            }
        });
        ic_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userID.equals("0")) {
                    Showdialog();
                } else {
                    addWish();
                }

            }
        });
    }

    private void Showdialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        MaterialButton rele_close = dialog.findViewById(R.id.rele_close);
        MaterialButton rele_submit = dialog.findViewById(R.id.rele_submit);
        TextView txt_settingtitle = dialog.findViewById(R.id.txt_settingtitle);
        TextView txt_desc = dialog.findViewById(R.id.txt_desc);
        txt_settingtitle.setText(R.string.warning);
        txt_desc.setText(getString(R.string.need_login_for_wish));
        rele_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        rele_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new LoginFragment();
                Loginfrag(fragment);
                dialog.dismiss();
            }
        });
    }

    private void Loginfrag(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void Addcart(String label, String option, String type) {
        UtilHelper.showdialog(context);
        String url = getString(R.string.link) + "addcart?user_id=" + userID + "&product_id=" + itemID + "&option=" + option + "&label=" + label + "&qty=" + "1" + "&product_price=" + price;
        Log.e(TAG, "Addcart: " + url);
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse: " + response);
                        UtilHelper.hidedialog();
                        try {
                            CartPojo cartPojo = gson.fromJson(String.valueOf(response), CartPojo.class);
                            if (cartPojo.getData().getStatus().equals(1)) {
                                if (type.equals("buynow")) {
                                    cartfrag();
                                    HomeActivity.txt_cartitem.setText(String.valueOf(cartPojo.getData().getData()));
                                } else {
                                    HomeActivity.txt_cartitem.setText(String.valueOf(cartPojo.getData().getData()));
                                    Toast.makeText(rootview.getContext(), cartPojo.getData().getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(rootview.getContext(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                            UtilHelper.hidedialog();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        UtilHelper.hidedialog();
                        Log.e(TAG, "onError: " + anError.getErrorBody());
                        Log.e(TAG, "onError: " + anError.getMessage());
                        Toast.makeText(rootview.getContext(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void cartfrag() {
        Fragment fragment;
        fragment = new CartFragment();
        if (getActivity().getSupportFragmentManager().findFragmentById(R.id.container) != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.container)).commit();
        }
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    private void addWish() {
        UtilHelper.showLikedialog(context);

        String url = getString(R.string.link) + "addwish?product_id=" + itemID + "&user_id=" + userID;
        Log.e(TAG, "addwish: " + url);
        final String finalUserID = userID;
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .setOkHttpClient(okHttpClient)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        UtilHelper.hideLikedialog();
                        Log.e(TAG, "onResponse: " + response);
                        try {
                            WishPojo wishPojo = gson.fromJson(String.valueOf(response), WishPojo.class);
                            if (wishPojo.getStatus().equals(1)) {
                                String remove = wishPojo.getRemove();
                                Integer wish = wishPojo.getWish();
                                HomeActivity.txt_wishitem.setText(String.valueOf(wish));
                                if (remove.equals("no")) {
                                    Toast.makeText(context, R.string.add_wishlist, Toast.LENGTH_SHORT).show();
                                    ic_like.setImageDrawable(getResources().getDrawable(R.drawable.fill_heart));
                                } else if (remove.equals("yes")) {
                                    ic_like.setImageDrawable(getResources().getDrawable(R.drawable.like));
                                    Toast.makeText(context, R.string.remove_wishlist, Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        UtilHelper.hideLikedialog();
                        try {
                            if (finalUserID.equals("null")) {
                                Toast.makeText(context, R.string.login_warning, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, R.string.something_wrong, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Resources.NotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(context, R.string.something_wrong, Toast.LENGTH_SHORT).show();
                        }
                        Log.e(TAG, "onError: " + anError.getErrorBody());
                        Log.e(TAG, "onError1: " + anError.getMessage());

                    }
                });
    }

    private void getList() {
        UtilHelper.showdialog(context);
        String url = getString(R.string.link) + "viewproduct/" + itemID + "/" + userID;
        Log.e(TAG, "registerUser: " + url);
        AndroidNetworking.get(url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse: " + response);
                        Detailpojo detailpojo = gson.fromJson(String.valueOf(response), Detailpojo.class);

                        if (detailpojo.getStatus().equals(1)) {
                            Offers offers = detailpojo.getOffers();
                            String id = String.valueOf(offers.getId());
                            String des = offers.getDescription();
                            String name = offers.getName();
                            String category = offers.getCategory();
                            String MRP = offers.getMRP();
                            price = offers.getPrice();
                            String sku = (String) offers.getSku();
                            String txt_discount1 = offers.getDiscount();
                            String meta_keyword = String.valueOf(offers.getMetaKeyword());
                            String additional_image = offers.getAdditionalImage();
                            String avgStar = String.valueOf(offers.getAvgStar());

                            myApp.setDesc(des);
                            myApp.setAvg_star(avgStar);
                            myApp.setSku(sku);
                            myApp.setCategory(category);
                            myApp.setTag(meta_keyword);
                            myApp.setProduct_price(price);
                            myApp.setProduct_id(id);
                            txt_price.setText(getString(R.string.dolar) + "" + price);
                            txt_original.setText(getString(R.string.dolar) + "" + MRP);
                            txt_totalreview.setText(offers.getTotal_review() + " " + getString(R.string.reviews));
                            List<Attribute> attributes = offers.getAttributes();
                            myApp.setAttributesList(attributes);
                            if (offers.getOptions() != null) {
                                List<Option> options = offers.getOptions();
                                for (int i = 0; i < options.size(); i++) {
                                    myApp.setOptionlist(options);
                                }
                            }
                            ic_like.setImageDrawable(offers.getWish().equals("0") ? getResources().getDrawable(R.drawable.like) : getResources().getDrawable(R.drawable.fill_heart));
                            List<Review> reviews = offers.getReview();
                            myApp.setReviewList(reviews);
                            txt_itemname.setText(name);
                            rate_detail.setRating(Float.parseFloat(avgStar));
                            txt_discount.setText(txt_discount1 + getString(R.string.percentage));
                            if (additional_image != null) {
                                list = new ArrayList<>(Arrays.asList(additional_image.split(",")));
                                imagelist.addAll(list);
                            }

                            setAdapter();
                            setFragment();
                            UtilHelper.hidedialog();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        UtilHelper.hidedialog();
                        Log.e(TAG, "onError: " + anError.getErrorBody());
                        Toast.makeText(getActivity(), R.string.try_again, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setAdapter() {
        SnappyLinearLayoutManager snappyLinearLayoutManager = new SnappyLinearLayoutManager(rootview.getContext(), SnappyLinearLayoutManager.HORIZONTAL, false);
        recycle_img.setLayoutManager(snappyLinearLayoutManager);
        recycle_img.setItemAnimator(new DefaultItemAnimator());
        ImageAdapter adapter = new ImageAdapter(rootview.getContext(), imagelist);
        recycle_img.setAdapter(adapter);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recycle_img);
        indicator.attachToRecyclerView(recycle_img, pagerSnapHelper);
        adapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());
    }

    private void setFragment() {
        setupViewPager(viewpager1);
        viewpager1.setOffscreenPageLimit(2);
        tabs.setupWithViewPager(viewpager1);
    }

    private void setupViewPager(WrapContentHeightViewPager viewPager) {
        if (!viewPager.isFakeDragging()) {
            viewPager.setAdapter(new ViewPagerAdapter(getActivity().getSupportFragmentManager()));
            viewPager.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            viewPager.setCurrentItem(0);
        } else {
            viewPager.endFakeDrag();
        }
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        String[] name = {getString(R.string.description), getString(R.string.additional_information), getString(R.string.review)};
        private int mCurrentPosition = -1;

        ViewPagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public void destroyItem(@NotNull ViewGroup container, int position, @NotNull Object object) {
            if (position >= getCount()) {
                FragmentManager manager = ((Fragment) object).getFragmentManager();
                assert manager != null;
                FragmentTransaction trans = manager.beginTransaction();
                trans.remove((Fragment) object);
                trans.commit();

            }
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {

                case 0:
                    return new DetailDescription();
                case 1:
                    return new DetailInformation();
                case 2:
                    return new DetailReview();
            }
            return null;
        }

        @Override
        public void setPrimaryItem(@NotNull ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            if (position != mCurrentPosition) {
                Fragment fragment = (Fragment) object;
                WrapContentHeightViewPager pager = (WrapContentHeightViewPager) container;
                if (fragment.getView() != null) {
                    mCurrentPosition = position;
                    pager.measureCurrentView(fragment.getView());
                }
            }
        }


        @Override
        public int getCount() {
            return name.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return name[position];
        }
    }

}
