package manhnguyen.shopping.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nshmura.snappysmoothscroller.SnappyGridLayoutManager;
import com.nshmura.snappysmoothscroller.SnappyLinearLayoutManager;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import manhnguyen.shopping.R;
import manhnguyen.shopping.adapter.BigOfferAdapter;
import manhnguyen.shopping.adapter.NormalOfferAdapter;
import manhnguyen.shopping.adapter.ProductListAdapter;
import manhnguyen.shopping.fragment.DetailFragment;
import manhnguyen.shopping.fragment.MoreproductFragment;
import manhnguyen.shopping.getset.Offer.BigOffer;
import manhnguyen.shopping.getset.Offer.NormalOffer;
import manhnguyen.shopping.getset.Offer.OfferPojo;
import manhnguyen.shopping.getset.Offer.Product;
import manhnguyen.shopping.getset.Offer.SensonalOffer;
import manhnguyen.shopping.getset.Wish.WishPojo;
import manhnguyen.shopping.interfaces.CoutomProductLister;
import manhnguyen.shopping.interfaces.WishlistButtonListener;
import manhnguyen.shopping.utils.SPmanager;
import manhnguyen.shopping.utils.UtilHelper;
import me.relex.circleindicator.CircleIndicator2;

public class OfferActivity extends AppCompatActivity implements CoutomProductLister, WishlistButtonListener {

    private static final String TAG = "OfferActivity";
    private ShimmerRecyclerView recycle_offer;
    private ShimmerRecyclerView recycle_products;
    private ShimmerRecyclerView recycle_ads;
    private String userID;
    private int pageCount;
    private Gson gson;
    private CircleIndicator2 indicator;
    private List<BigOffer> bigoffer;
    private TextView txt_nooffer;
    private List<NormalOffer> normaloffer;
    private List<NormalOffer> normaloffer1;
    private TextView txt_noffer;
    private TextView txt_percentoff;
    private TextView txt_collection;
    private Button btn_shop;
    private ImageView ic_dummy;
    private ImageView img_banner;
    private List<Product> productlist;
    private ProductListAdapter productListAdapter;
    private LottieAnimationView progress;
    private List<Product> loadmorlist;
    private RelativeLayout relSummy;
    private RelativeLayout rel_banner2;
    private RelativeLayout relOfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        pageCount = 1;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder
                .registerTypeAdapterFactory(UtilHelper.UNRELIABLE_INTEGER_FACTORY)
                .create();
        userID = SPmanager.getPreference(OfferActivity.this, "userID");
        if (userID != null) {
            if (userID.equals("null")) {
                userID = "0";
            }
        } else {
            userID = "0";
        }
        inite();
        BigOfferList();
    }

    private void inite() {
        progress = findViewById(R.id.progress);
        recycle_ads = findViewById(R.id.recycle_ads);
        txt_nooffer = findViewById(R.id.txt_nooffer);
        recycle_offer = findViewById(R.id.recycle_offer);
        recycle_products = findViewById(R.id.recycle_products);
        txt_noffer = findViewById(R.id.txt_noffer);
        relOfer = findViewById(R.id.relOfer);
        indicator = findViewById(R.id.indicator);
        txt_percentoff = findViewById(R.id.txt_percentoff);
        txt_collection = findViewById(R.id.txt_collection);
        rel_banner2 = findViewById(R.id.rel_banner2);
        relSummy = findViewById(R.id.relSummy);
        btn_shop = findViewById(R.id.btn_shop);
        img_banner = findViewById(R.id.img_banner);
        LinearLayout lay_viewmore1 = findViewById(R.id.lay_viewmore1);
        ic_dummy = findViewById(R.id.ic_dummy);
        ImageView img_back = findViewById(R.id.img_back);

        lay_viewmore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewAllProduct();
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void ViewAllProduct() {
        Map<String, String> data = new HashMap<>();
        data.put("category", "1");
        data.put("subcategory", "2");
        data.put("brand", "0");
        data.put("price", "0");
        data.put("discount", "0");
        data.put("ratting", "0");
        data.put("filter", "0");
        data.put("color", "0");
        data.put("size", "0");
        data.put("mainCat", "0");
        data.put("coupon_id", "0");
        EventBus.getDefault().postSticky(data);
        ViewProductfrag();
    }

    private void ViewProductfrag() {
        Fragment fragment = new MoreproductFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void BigOfferList() {
        recycle_offer.showShimmerAdapter();
        recycle_products.showShimmerAdapter();
        recycle_ads.showShimmerAdapter();
        String url = getString(R.string.link) + "offers/" + userID + "/" + pageCount;
        Log.e(TAG, "BigOfferList: " + url);
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse: " + response);
                        OfferPojo offerPojo = gson.fromJson(String.valueOf(response), OfferPojo.class);
                        bigoffer = new ArrayList<>();
                        normaloffer = new ArrayList<>();
                        normaloffer1 = new ArrayList<>();
                        productlist = new ArrayList<>();
                        if (offerPojo.getData().getStatus().equals(1)) {
                            bigoffer = offerPojo.getData().getOfferdata().getBigOffer();
                            normaloffer = offerPojo.getData().getOfferdata().getNormalOffer();
                            SensonalOffer seasonal = offerPojo.getData().getOfferdata().getSensonalOffer();
                            productlist = offerPojo.getData().getOfferdata().getProduct();
                            setBigOfferAdapter();
                            setnormalAdapter();
                            if (seasonal != null) {
                                relSummy.setVisibility(View.GONE);
                                rel_banner2.setVisibility(View.VISIBLE);
                                showSeasonaloffer(seasonal);
                            } else {
                                rel_banner2.setVisibility(View.GONE);
                                relSummy.setVisibility(View.GONE);
                            }
                            setProductAdapter();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onError: " + anError.getErrorBody());
                        Log.e(TAG, "onError: " + anError.getMessage());
                        Toast.makeText(OfferActivity.this, R.string.no_data, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setBigOfferAdapter() {
        recycle_ads.hideShimmerAdapter();
        if (bigoffer.size() != 0) {
            txt_nooffer.setVisibility(View.GONE);
            ic_dummy.setVisibility(View.GONE);
            final SnappyLinearLayoutManager verticalLayoutManager1 = new SnappyLinearLayoutManager(OfferActivity.this, SnappyLinearLayoutManager.HORIZONTAL, false);
            recycle_ads.setLayoutManager(verticalLayoutManager1);
            final BigOfferAdapter specialOfferAdapter = new BigOfferAdapter(OfferActivity.this, bigoffer);
            recycle_ads.setAdapter(specialOfferAdapter);
            PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
            pagerSnapHelper.attachToRecyclerView(recycle_ads);
            indicator.attachToRecyclerView(recycle_ads, pagerSnapHelper);
            specialOfferAdapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());
            final Timer timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {

                    if (verticalLayoutManager1.findLastCompletelyVisibleItemPosition() < (specialOfferAdapter.getItemCount() - 1)) {

                        verticalLayoutManager1.smoothScrollToPosition(recycle_ads, new RecyclerView.State(), verticalLayoutManager1.findLastCompletelyVisibleItemPosition() + 1);
                    } else if (verticalLayoutManager1.findLastCompletelyVisibleItemPosition() == (specialOfferAdapter.getItemCount() - 1)) {

                        verticalLayoutManager1.smoothScrollToPosition(recycle_ads, new RecyclerView.State(), 0);
                    }
                }
            }, 0, 5000);
        } else {
            txt_nooffer.setVisibility(View.VISIBLE);
            ic_dummy.setVisibility(View.VISIBLE);
            recycle_ads.setAdapter(null);
            indicator.setVisibility(View.GONE);
        }
    }

    private void setnormalAdapter() {
        recycle_offer.hideShimmerAdapter();
        if (normaloffer.size() != 0) {

            relOfer.setVisibility(View.GONE);
            final SnappyLinearLayoutManager verticalLayoutManager1 = new SnappyLinearLayoutManager(OfferActivity.this, SnappyLinearLayoutManager.VERTICAL, false);
            recycle_offer.setLayoutManager(verticalLayoutManager1);
            final NormalOfferAdapter specialOfferAdapter = new NormalOfferAdapter(OfferActivity.this, normaloffer);
            recycle_offer.setAdapter(specialOfferAdapter);
        } else {
            relOfer.setVisibility(View.VISIBLE);
            recycle_offer.setAdapter(null);
        }
    }

    private void showSeasonaloffer(SensonalOffer seasonal) {
        String title = seasonal.getTitle();
        String fixed_to = String.valueOf(seasonal.getFixedTo());
        String cat_id = String.valueOf(seasonal.getCategory());
        String banner = seasonal.getBanner();
        String sub_catid = String.valueOf(seasonal.getSubCategory());

        txt_collection.setText(title);
        txt_percentoff.setText(fixed_to + getString(R.string.percentage) + getString(R.string.offf));
        Glide.with(getApplicationContext()).load(getString(R.string.imagelink) + "public/upload/offer/image/" + banner).into(img_banner);

        btn_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> data = new HashMap<>();
                data.put("category", cat_id);
                data.put("subcategory", sub_catid);
                data.put("brand", "0");
                data.put("price", "0");
                data.put("discount", fixed_to);
                data.put("ratting", "0");
                data.put("filter", "0");
                data.put("color", "0");
                data.put("size", "0");
                data.put("mainCat", title);
                EventBus.getDefault().postSticky(data);
                Fragment fragment = new MoreproductFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void setProductAdapter() {
        recycle_products.hideShimmerAdapter();
        SnappyGridLayoutManager snappyGridLayoutManager = new SnappyGridLayoutManager(OfferActivity.this, 2);
        recycle_products.setLayoutManager(snappyGridLayoutManager);
        productListAdapter = new ProductListAdapter(recycle_products, OfferActivity.this, productlist);
        recycle_products.setAdapter(productListAdapter);
        productListAdapter.setOnLoadMoreListener(new ProductListAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageCount = pageCount + 1;
                progress.setVisibility(View.VISIBLE);
                loadmorebest();
            }
        });

        productListAdapter.CoutomProductButtonListener(this);
        productListAdapter.setCustomWishButtonListener(this);
    }

    private void loadmorebest() {
        String url = getString(R.string.link) + "offers/" + userID + "/" + pageCount;
        Log.e(TAG, "BigOfferList: " + url);
        loadmorlist = new ArrayList<>();
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse: " + response);
                        try {
                            OfferPojo offerPojo = gson.fromJson(String.valueOf(response), OfferPojo.class);
                            if (offerPojo.getData().getStatus().equals(1)) {
                                loadmorlist = offerPojo.getData().getOfferdata().getProduct();
                                progress.setVisibility(View.GONE);
                                if (loadmorlist.size() != 0) {
                                    Log.e("adapter", "" + loadmorlist.size());
                                    productListAdapter.setLoaded();
                                    productListAdapter.addItem(loadmorlist, productlist.size());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(OfferActivity.this, R.string.no_data, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onError: " + anError.getMessage());
                        Log.e(TAG, "onError: " + anError.getErrorBody());
                        Toast.makeText(OfferActivity.this, R.string.no_data, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void layoutClick(int position) {
        Map<String, String> data = new HashMap<>();
        data.put("itemID", String.valueOf(productlist.get(position).getId()));
        data.put("name", productlist.get(position).getName());
        data.put("price", productlist.get(position).getPrice());
        data.put("image", productlist.get(position).getBasicImage());
        data.put("key", "home");
        EventBus.getDefault().postSticky(data);
        detailsfrag();
    }

    private void detailsfrag() {
        Fragment fragment = new DetailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onAddClicked(int position, ImageView like, ImageView unlike) {
        String id = String.valueOf(productlist.get(position).getId());
        addwish(id, like);
    }

    @Override
    public void onremovelist(int position, Integer id) {

    }

    private void addwish(String id, ImageView like) {
        String userID = SPmanager.getPreference(OfferActivity.this, "userID");
        UtilHelper.showLikedialog(OfferActivity.this);
        String url = getString(R.string.link) + "addwish?product_id=" + id + "&user_id=" + userID;
        Log.e(TAG, "addwish: " + url);
        final String finalUserID = userID;
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        UtilHelper.hideLikedialog();
                        WishPojo wishPojo = gson.fromJson(String.valueOf(response), WishPojo.class);
                        if (wishPojo.getStatus().equals(1)) {
                            String remove = wishPojo.getRemove();
                            Integer wish = wishPojo.getWish();
                            HomeActivity.txt_wishitem.setText(String.valueOf(wish));
                            if (remove.equals("no")) {
                                Toast.makeText(OfferActivity.this, getString(R.string.add_wishlist), Toast.LENGTH_SHORT).show();
                                like.setImageDrawable(getResources().getDrawable(R.drawable.fill_heart));

                            } else if (remove.equals("yes")) {
                                like.setImageDrawable(getResources().getDrawable(R.drawable.like));
                                Toast.makeText(OfferActivity.this, getString(R.string.remove_wishlist), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        try {
                            if (finalUserID.equals("null")) {
                                Toast.makeText(OfferActivity.this, R.string.login_warning, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(OfferActivity.this, R.string.something_wrong, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Resources.NotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(OfferActivity.this, R.string.something_wrong, Toast.LENGTH_SHORT).show();
                        }
                        Log.e(TAG, "onError: " + anError.getErrorBody());
                        Log.e(TAG, "onError1: " + anError.getMessage());

                    }
                });
    }
}