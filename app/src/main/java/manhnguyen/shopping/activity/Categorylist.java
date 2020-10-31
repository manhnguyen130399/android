package manhnguyen.shopping.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.androidkun.xtablayout.XTabLayout;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.nshmura.snappysmoothscroller.SnappyLinearLayoutManager;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import manhnguyen.shopping.R;
import manhnguyen.shopping.adapter.CatItemAdapter;
import manhnguyen.shopping.adapter.CatOfferAdapter;
import manhnguyen.shopping.adapter.CategorylistAdapter;
import manhnguyen.shopping.fragment.DetailFragment;
import manhnguyen.shopping.fragment.MoreproductFragment;
import manhnguyen.shopping.getset.category.Categorypojjo;
import manhnguyen.shopping.interfaces.CoutomProductLister;
import manhnguyen.shopping.utils.RecyclerItemClickListener;
import manhnguyen.shopping.utils.UtilHelper;
import me.relex.circleindicator.CircleIndicator2;

public class Categorylist extends AppCompatActivity implements CoutomProductLister {
    private static final String TAG = "Categorylist";
    private XTabLayout tabs;
    private RecyclerView recycle_offers;
    private RecyclerView recycle_catitem;
    private RecyclerView rec_mainCategory;
    private List<Categorypojjo.Offer> offerlist;
    private Categorylist context;
    private CircleIndicator2 indicator;
    private Gson gson;
    private List<Categorypojjo.Datum> data;
    private TextView txt_nooffer;
    private String brand = "0";
    private String size = "0";
    private String colors = "0";
    private String filter = "0";
    private String ratting = "0";
    private String discount = "0";
    private String price = "0";
    private String category;
    private String subcategory = "0";
    private String coupon_id = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorylist);
        context = Categorylist.this;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        inite();
        getList();
    }

    private void getList() {
        UtilHelper.showdialog(context);
        String url = getString(R.string.link) + "categoryoffer";
        Log.e(TAG, "getList: " + url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse: " + response);
                        try {
                            Categorypojjo categorypojjo = gson.fromJson(String.valueOf(response), Categorypojjo.class);
                            if (categorypojjo.getStatus().equals(1)) {
                                data = categorypojjo.getData();
                                UtilHelper.hidedialog();
                                setcatlist();
                                if (data.size() != 0) {
                                    getoffer(tabs.getSelectedTabPosition());
                                    getcatItem(tabs.getSelectedTabPosition());
                                }
                            } else {
                                UtilHelper.hidedialog();
                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onError: " + anError.getErrorBody());
                        UtilHelper.hidedialog();
                    }
                });
    }

    private void getcatItem(final int selectedTabPosition) {
        final List<Categorypojjo.Subcategory> catitemList = data.get(selectedTabPosition).getSubcategory();
        if (catitemList.size() != 0) {
            SnappyLinearLayoutManager linearLayoutManager = new SnappyLinearLayoutManager(Categorylist.this, SnappyLinearLayoutManager.VERTICAL, false);
            recycle_catitem.setLayoutManager(linearLayoutManager);
            CatItemAdapter adapter = new CatItemAdapter(catitemList);
            recycle_catitem.setAdapter(adapter);
            recycle_catitem.addOnItemTouchListener(new RecyclerItemClickListener(context, recycle_catitem, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    try {
                        category = String.valueOf(catitemList.get(position).getParentCategory());
                        subcategory = String.valueOf(catitemList.get(position).getId());
                        Map<String, String> data = new HashMap<>();
                        data.put("category", category);
                        data.put("subcategory", subcategory);
                        data.put("brand", brand);
                        data.put("price", price);
                        data.put("discount", discount);
                        data.put("ratting", ratting);
                        data.put("filter", filter);
                        data.put("color", colors);
                        data.put("size", size);
                        data.put("mainCat", catitemList.get(position).getName());
                        data.put("layout", "cat");
                        data.put("coupon_id", coupon_id);
                        EventBus.getDefault().postSticky(data);
                        Fragment fragment = new MoreproductFragment();
                        if (getSupportFragmentManager().findFragmentById(R.id.container) != null) {
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .remove(getSupportFragmentManager().findFragmentById(R.id.container)).commit();
                        }
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, fragment)
                                .commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onItemLongClick(View view, int position) {
                }
            }));
        } else {
            recycle_catitem.setAdapter(null);
        }
    }

    private void setcatlist() {
        for (int i = 0; i < data.size(); i++) {
            tabs.addTab(tabs.newTab().setText(data.get(i).getName()));
            SnappyLinearLayoutManager snappyLinearLayoutManager = new SnappyLinearLayoutManager(Categorylist.this, SnappyLinearLayoutManager.HORIZONTAL, false);
            rec_mainCategory.setLayoutManager(snappyLinearLayoutManager);
            CategorylistAdapter adapter = new CategorylistAdapter(data);
            rec_mainCategory.setAdapter(adapter);
            tabs.setOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(XTabLayout.Tab tab) {
                    getoffer(tabs.getSelectedTabPosition());
                    getcatItem(tabs.getSelectedTabPosition());
                }

                @Override
                public void onTabUnselected(XTabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(XTabLayout.Tab tab) {

                }
            });
        }
    }

    private void getoffer(int selectedTabPosition) {
        try {
            offerlist = data.get(selectedTabPosition).getOffers();
            if (offerlist.size() != 0) {
                txt_nooffer.setVisibility(View.GONE);
                indicator.setVisibility(View.VISIBLE);
                final SnappyLinearLayoutManager verticalLayoutManager1 = new SnappyLinearLayoutManager(context, SnappyLinearLayoutManager.HORIZONTAL, false);
                recycle_offers.setLayoutManager(verticalLayoutManager1);
                final CatOfferAdapter specialOfferAdapter = new CatOfferAdapter(context, offerlist);
                recycle_offers.setAdapter(specialOfferAdapter);
                PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
                if (recycle_offers.getOnFlingListener() == null) {
                    pagerSnapHelper.attachToRecyclerView(recycle_offers);
                }
                indicator.attachToRecyclerView(recycle_offers, pagerSnapHelper);
                specialOfferAdapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());
                hanldeAutoScroll(offerlist, recycle_offers, 5000);
                specialOfferAdapter.setListener(this);
            } else {
                noOffer();

            }
        } catch (Exception e) {
            e.printStackTrace();
            noOffer();
        }


    }

    private void noOffer() {
        txt_nooffer.setVisibility(View.VISIBLE);
        recycle_offers.setAdapter(null);
        indicator.setVisibility(View.GONE);
    }


    private void openfrag(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void hanldeAutoScroll(final List<Categorypojjo.Offer> offerlist, final RecyclerView recycle_offers, final int duration) {

        final int[] position = {0};
        final Handler mHandler = new Handler(Looper.getMainLooper());
        final Runnable SCROLLING_RUNNABLE = new Runnable() {
            @Override
            public void run() {
                position[0]++;
                if (position[0] < offerlist.size()) {
                    recycle_offers.smoothScrollToPosition(position[0]);
                } else if (position[0] == offerlist.size()) {
                    position[0] = -1;
                }
                mHandler.postDelayed(this, duration);
            }
        };
        mHandler.postDelayed(SCROLLING_RUNNABLE, 3000);


    }

    private void inite() {
        tabs = findViewById(R.id.tabs);
        rec_mainCategory = findViewById(R.id.rec_mainCategory);
        recycle_catitem = findViewById(R.id.recycle_catitem);
        recycle_offers = findViewById(R.id.recycle_offers);
        indicator = findViewById(R.id.indicator);
        txt_nooffer = findViewById(R.id.txt_nooffer);
        ImageView ic_back = findViewById(R.id.ic_back);

        try {
            Map<String, String> data = EventBus.getDefault().getStickyEvent(HashMap.class);
            category = data.get("category");
            subcategory = data.get("subcategory");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void layoutClick(int position) {
        String isproduct = offerlist.get(position).getIsProduct();
        if (isproduct.equals("1")) {
            category = String.valueOf(offerlist.get(position).getCategoryId());
            discount = offerlist.get(position).getFixed();
            if (discount == null) {
                discount = "0";
            }
            Map<String, String> data = new HashMap<>();
            data.put("category", category);
            data.put("subcategory", "0");
            data.put("brand", brand);
            data.put("price", price);
            data.put("discount", discount);
            data.put("ratting", ratting);
            data.put("filter", filter);
            data.put("color", colors);
            data.put("size", size);
            data.put("mainCat", offerlist.get(position).getMainTitle());
            String lay_out = "cat";
            data.put("layout", lay_out);
            data.put("coupon_id", coupon_id);
            EventBus.getDefault().postSticky(data);
            Fragment fragment = new MoreproductFragment();
            openfrag(fragment);

        } else {
            Map<String, String> data = new HashMap<>();
            data.put("itemID", String.valueOf(offerlist.get(position).getProductId()));
            data.put("key", "cat");
            EventBus.getDefault().postSticky(data);
            Fragment fragment = new DetailFragment();
            openfrag(fragment);
        }
    }
}