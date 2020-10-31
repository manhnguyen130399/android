package manhnguyen.shopping.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import androidx.fragment.app.FragmentTransaction;

import com.airbnb.lottie.LottieAnimationView;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.button.MaterialButton;
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

import manhnguyen.shopping.MyApplication;
import manhnguyen.shopping.R;
import manhnguyen.shopping.activity.Categorylist;
import manhnguyen.shopping.adapter.ItemListAdapter;
import manhnguyen.shopping.adapter.ProductAdapter;
import manhnguyen.shopping.adapter.SubCategoryAdapter;
import manhnguyen.shopping.getset.Filter.Brand;
import manhnguyen.shopping.getset.Filter.Color;
import manhnguyen.shopping.getset.Filter.Datum;
import manhnguyen.shopping.getset.Filter.Details;
import manhnguyen.shopping.getset.Filter.FilterPojo;
import manhnguyen.shopping.getset.Filter.Product;
import manhnguyen.shopping.getset.Filter.Subcategory;
import manhnguyen.shopping.getset.Wish.WishPojo;
import manhnguyen.shopping.interfaces.CoutomProductLister;
import manhnguyen.shopping.interfaces.WishlistButtonListener;
import manhnguyen.shopping.utils.RecyclerItemClickListener;
import manhnguyen.shopping.utils.SPmanager;
import manhnguyen.shopping.utils.UtilHelper;

import static manhnguyen.shopping.activity.HomeActivity.ic_cart;
import static manhnguyen.shopping.activity.HomeActivity.ic_favrite;
import static manhnguyen.shopping.activity.HomeActivity.ic_home;
import static manhnguyen.shopping.activity.HomeActivity.ic_user;
import static manhnguyen.shopping.activity.HomeActivity.rel_cart;
import static manhnguyen.shopping.activity.HomeActivity.rel_favorite;
import static manhnguyen.shopping.activity.HomeActivity.rel_home;
import static manhnguyen.shopping.activity.HomeActivity.rel_user;
import static manhnguyen.shopping.activity.HomeActivity.txt_cart;
import static manhnguyen.shopping.activity.HomeActivity.txt_favrite;
import static manhnguyen.shopping.activity.HomeActivity.txt_home;
import static manhnguyen.shopping.activity.HomeActivity.txt_user;
import static manhnguyen.shopping.activity.HomeActivity.txt_wishitem;


public class MoreproductFragment extends Fragment implements CoutomProductLister, WishlistButtonListener {
    private static final String TAG = "ViewMoreActivity";
    private com.cooltechworks.views.shimmer.ShimmerRecyclerView recycle_grid;
    private com.cooltechworks.views.shimmer.ShimmerRecyclerView recycle_category;
    private TextView txt_catname;
    private TextView txt_items;
    private List<Brand> brndlist;
    private List<Datum> productlist1;
    private View view;
    private Gson gson;
    private Context context;
    private LottieAnimationView content_load;
    private LottieAnimationView progressa;
    private String category = String.valueOf(1);
    private String subcategory = String.valueOf(0);
    private String userID;
    private String brand_id = String.valueOf(0);
    private List<Subcategory> subcategoryList;
    private MyApplication myApp;
    private List<String> priceList;
    private List<String> sizeList;
    private ProductAdapter productAdapter;
    private String price_id = String.valueOf(0);
    private String discount_id = String.valueOf(0);
    private String rating_id = String.valueOf(0);
    private String filter_id = String.valueOf(0);
    private String color_id = String.valueOf(0);
    private String size_id = String.valueOf(0);
    private String coupon_id = String.valueOf(0);
    private List<Color> colorList;
    private String mainCat;
    private String nextpage_url;
    private String lay_out = "more";

    private void detailsfrag() {
        Fragment fragment = new DetailFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_view_more, container, false);
        context = view.getContext();
        brndlist = new ArrayList<>();
        productlist1 = new ArrayList<>();
        myApp = MyApplication.getInstance();
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
            coupon_id = data.get("coupon_id");
            lay_out = data.get("layout");

        } catch (Exception e) {
            e.printStackTrace();
        }

        userID = SPmanager.getPreference(context, "userID");
        if (userID != null) {
            if (userID.equals("null")) {
                userID = "0";
            }
        } else {
            userID = "0";
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        initlise();
        getList();

        return view;
    }

    private void getList() {
        recycle_grid.showShimmerAdapter();
        recycle_category.showShimmerAdapter();
        String url = getString(R.string.link) + "productfilter";
        Log.e(TAG, "getSubList: " + url);
        AndroidNetworking.post(url)
                .addQueryParameter("category", category)
                .addQueryParameter("subcategory", subcategory)
                .addQueryParameter("brand", brand_id)
                .addQueryParameter("price", price_id)
                .addQueryParameter("discount", discount_id)
                .addQueryParameter("ratting", rating_id)
                .addQueryParameter("filter", filter_id)
                .addQueryParameter("user_id", userID)
                .addQueryParameter("color", color_id)
                .addQueryParameter("size", size_id)
                .addQueryParameter("coupon_id", coupon_id)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse: " + response);
                        productlist1.clear();
                        try {
                            FilterPojo responspojo = gson.fromJson(String.valueOf(response), FilterPojo.class);
                            if (responspojo.getStatus().equals(1)) {
                                Details detailpojo = responspojo.getDetails();
                                subcategoryList = responspojo.getDetails().getSubcategory();
                                Product product = responspojo.getDetails().getProduct();
                                txt_items.setText(product.getTotal() + " " + getString(R.string.items));

                                try {
                                    List<Datum> listProduct = product.getData();

                                    for (int i = 0; i < listProduct.size(); i++) {
                                        Datum datum = listProduct.get(i);
                                        if (getString(R.string.show_admmob_ads).equals("yes")) {
                                            if (i % 4 == 0 && i != 0) {
                                                productlist1.add(null);
                                            }
                                        }
                                        productlist1.add(datum);
                                    }
                                    brndlist = detailpojo.getBrand();
                                    colorList = detailpojo.getColor();
                                    priceList = detailpojo.getPricelist();
                                    sizeList = detailpojo.getSize();
                                    myApp.setBrndlist(brndlist);
                                    myApp.setColorList(colorList);
                                    myApp.setPriceList(priceList);
                                    myApp.setSizeList(sizeList);
                                    nextpage_url = product.getNextPageUrl();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                setcatlist();
                                setproductList(subcategory);
                            } else {
                                recycle_category.hideShimmerAdapter();
                                recycle_grid.hideShimmerAdapter();
                                Toast.makeText(context, R.string.no_data, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG, "onResponse: " + e.getMessage());
                            recycle_category.hideShimmerAdapter();
                            recycle_grid.hideShimmerAdapter();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onError: " + anError.getErrorBody());
                        Log.e(TAG, "onError: " + anError.getErrorDetail());
                        recycle_category.hideShimmerAdapter();
                        recycle_grid.hideShimmerAdapter();
                    }
                });
    }

    private void setproductList(String subcategory) {
        content_load.setVisibility(View.GONE);
        recycle_grid.hideShimmerAdapter();
        if (productlist1.size() != 0) {
            recycle_grid.setVisibility(View.VISIBLE);
            SnappyGridLayoutManager snappyGridLayoutManager = new SnappyGridLayoutManager(context, 2);
            recycle_grid.setLayoutManager(snappyGridLayoutManager);
            snappyGridLayoutManager.setSpanSizeLookup(new SnappyGridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (recycle_grid.getAdapter().getItemViewType(position) == 1) {
                        return 1;
                    }
                    return 2;
                }
            });
            productAdapter = new ProductAdapter(recycle_grid, context, productlist1);
            recycle_grid.setAdapter(productAdapter);
            productAdapter.CoutomProductButtonListener(this);
            productAdapter.setCustomWishButtonListener(this);
            if (nextpage_url != null) {
                productAdapter.setOnLoadMoreListener(new ItemListAdapter.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        loadmore(subcategory);
                        progressa.setVisibility(View.VISIBLE);
                    }
                });
            }
        }else {
            recycle_grid.setVisibility(View.GONE);
        }
    }

    private void setcatlist() {
        recycle_category.hideShimmerAdapter();
        if (subcategoryList.size() != 0) {
            SnappyLinearLayoutManager snappyLinearLayoutManager = new SnappyLinearLayoutManager(context, SnappyLinearLayoutManager.HORIZONTAL, false);
            recycle_category.setLayoutManager(snappyLinearLayoutManager);
            SubCategoryAdapter categoryAdapter = new SubCategoryAdapter(context, subcategoryList);
            recycle_category.setAdapter(categoryAdapter);
            if (category != null && !category.equals("0")) {
                for (int i = 0; i < subcategoryList.size(); i++) {
                    if (mainCat.equals(subcategoryList.get(i).getName())) {
                        categoryAdapter.swapItems(0, i);
                        recycle_category.smoothScrollToPosition(i);
                    }
                }

            }
            recycle_category.addOnItemTouchListener(new RecyclerItemClickListener(context, recycle_category, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    txt_catname.setText(subcategoryList.get(position).getName());
                    subcategory = String.valueOf(subcategoryList.get(position).getId());
                    getproductbycate(subcategory);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            }));
        }
    }

    private void getproductbycate(String subcategory) {
        recycle_grid.showShimmerAdapter();
        String url = getString(R.string.link) + "productfilter";
        Log.e(TAG, "getSubList: " + url);
        AndroidNetworking.post(url)
                .addQueryParameter("category", category)
                .addQueryParameter("subcategory", subcategory)
                .addQueryParameter("brand", brand_id)
                .addQueryParameter("price", price_id)
                .addQueryParameter("discount", discount_id)
                .addQueryParameter("ratting", rating_id)
                .addQueryParameter("filter", filter_id)
                .addQueryParameter("user_id", userID)
                .addQueryParameter("color", color_id)
                .addQueryParameter("size", size_id)
                .addQueryParameter("coupon_id", coupon_id)

                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse: " + response);

                        try {
                            FilterPojo responspojo = gson.fromJson(String.valueOf(response), FilterPojo.class);
                            if (responspojo.getStatus().equals(1)) {
                                Details detailpojo = responspojo.getDetails();
                                subcategoryList = responspojo.getDetails().getSubcategory();
                                Product product = responspojo.getDetails().getProduct();
                                txt_items.setText(product.getTotal() + " " + getString(R.string.items));
                                productlist1 = product.getData();
                                brndlist = detailpojo.getBrand();
                                colorList = detailpojo.getColor();
                                priceList = detailpojo.getPricelist();
                                sizeList = detailpojo.getSize();
                                myApp.setBrndlist(brndlist);
                                myApp.setColorList(colorList);
                                myApp.setPriceList(priceList);
                                myApp.setSizeList(sizeList);
                                nextpage_url = product.getNextPageUrl();
                                setproductList(subcategory);
                            } else {
                                Toast.makeText(context, getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                                recycle_grid.hideShimmerAdapter();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            recycle_grid.hideShimmerAdapter();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onError: " + anError.getErrorBody());
                        Log.e(TAG, "onError: " + anError.getErrorDetail());
                        recycle_grid.hideShimmerAdapter();
                    }
                });
    }

    private void initlise() {

        rel_cart.setBackgroundResource(0);
        rel_favorite.setBackgroundResource(0);
        rel_user.setBackgroundResource(0);
        rel_home.setBackgroundResource(0);

        txt_home.setVisibility(View.GONE);
        txt_user.setVisibility(View.GONE);
        txt_cart.setVisibility(View.GONE);
        txt_favrite.setVisibility(View.GONE);

        ic_favrite.setBackgroundResource(R.drawable.like_not);
        ic_cart.setBackgroundResource(R.drawable.cart_not);
        ic_user.setBackgroundResource(R.drawable.user_not);
        ic_home.setBackgroundResource(R.drawable.home_not);

        recycle_grid = view.findViewById(R.id.recycle_grid);
        recycle_category = view.findViewById(R.id.recycle_category);
        ImageView ic_back = view.findViewById(R.id.ic_back);
        txt_catname = view.findViewById(R.id.txt_catname);
        content_load = view.findViewById(R.id.content_load);
        progressa = view.findViewById(R.id.progressa);
        txt_items = view.findViewById(R.id.txt_items);
        LinearLayout lay_refine = view.findViewById(R.id.lay_refine);
        txt_catname.setText(mainCat);
        lay_refine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> data = new HashMap<>();
                data.put("category", category);
                data.put("subcategory", subcategory);
                data.put("brand", "0");
                data.put("price", "0");
                data.put("discount", "0");
                data.put("ratting", "0");
                data.put("filter", "0");
                data.put("color", "0");
                data.put("size", "0");
                data.put("mainCat", mainCat);
                data.put("layout", "more");
                data.put("coupon_id", "0");

                EventBus.getDefault().postSticky(data);
                Fragment fragment = new FilterFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                if (fragmentManager.findFragmentById(R.id.container) != null) {
                    fragmentManager.beginTransaction().remove(fragmentManager.findFragmentById(R.id.container)).commit();
                }
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        });
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
                try {
                    if (lay_out.equals("cat")) {
                        Intent intent = new Intent(context, Categorylist.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    getFragmentManager().popBackStack();
                }

            }
        });

    }

    private void loadmore(String subcatId) {
        Log.e(TAG, "getSubList: " + nextpage_url);
        List<Datum> loadmoreList = new ArrayList<>();
        AndroidNetworking.post(nextpage_url)
                .addQueryParameter("category", category)
                .addQueryParameter("subcategory", subcatId)
                .addQueryParameter("brand", brand_id)
                .addQueryParameter("price", price_id)
                .addQueryParameter("discount", discount_id)
                .addQueryParameter("ratting", rating_id)
                .addQueryParameter("filter", filter_id)
                .addQueryParameter("user_id", userID)
                .addQueryParameter("color", color_id)
                .addQueryParameter("size", size_id)
                .addQueryParameter("coupon_id", coupon_id)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse: " + response);
                        FilterPojo responspojo = gson.fromJson(String.valueOf(response), FilterPojo.class);
                        if (responspojo.getStatus().equals(1)) {
                            Details detailpojo = responspojo.getDetails();
                            subcategoryList = responspojo.getDetails().getSubcategory();
                            Product product = responspojo.getDetails().getProduct();
                            brndlist = detailpojo.getBrand();
                            colorList = detailpojo.getColor();
                            priceList = detailpojo.getPricelist();
                            sizeList = detailpojo.getSize();
                            myApp.setBrndlist(brndlist);
                            myApp.setColorList(colorList);
                            myApp.setPriceList(priceList);
                            myApp.setSizeList(sizeList);
                            nextpage_url = product.getNextPageUrl();
                            List<Datum> listProduct = product.getData();
                            progressa.setVisibility(View.GONE);
                            for (int i = 0; i < listProduct.size(); i++) {
                                Datum datum = listProduct.get(i);
                                if (getString(R.string.show_admmob_ads).equals("yes")) {
                                    if (i % 4 == 0 && i != 0) {
                                        loadmoreList.add(null);
                                    }
                                }
                                loadmoreList.add(datum);
                            }
                            if (loadmoreList.size() != 0) {
                                Log.e("adapter", "" + loadmoreList.size());
                                productAdapter.setLoaded();
                                productAdapter.addItem(loadmoreList, productlist1.size());
                            }
                        } else {
                            progressa.setVisibility(View.GONE);
                            Toast.makeText(context, getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onError: " + anError.getErrorBody());
                        Toast.makeText(context, getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                        progressa.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void layoutClick(int position) {
        Map<String, String> data = new HashMap<>();
        data.put("itemID", String.valueOf(productlist1.get(position).getId()));
        data.put("name", productlist1.get(position).getName());
        data.put("priceID", productlist1.get(position).getPrice());
        data.put("image", productlist1.get(position).getBasicImage());
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
        detailsfrag();
    }

    @Override
    public void onResume() {
        super.onResume();
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    assert getFragmentManager() != null;
                    getFragmentManager().popBackStack();
                    try {
                        if (lay_out.equals("cat")) {
                            Intent intent = new Intent(context, Categorylist.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            Fragment fragment = new HomeFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            if (fragmentManager.findFragmentById(R.id.container) != null) {
                                fragmentManager.beginTransaction().remove(fragmentManager.findFragmentById(R.id.container)).commit();
                            }
                            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG, "onKey: " + e.getMessage());

                    }

                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onAddClicked(int position, ImageView like, ImageView unlike) {
        String id = String.valueOf(productlist1.get(position).getId());
        if (userID != null && !userID.equals("0")) {
            addwish(id, userID, like, unlike);
        } else {
            Showdialog();
        }
    }

    private void addwish(String id, String userID, final ImageView like, final ImageView unlike) {
        userID = SPmanager.getPreference(context, "userID");
        UtilHelper.showLikedialog(context);
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
                            txt_wishitem.setText(String.valueOf(wish));
                            if (remove.equals("no")) {
                                Toast.makeText(context, getString(R.string.add_wishlist), Toast.LENGTH_SHORT).show();
                                like.setImageDrawable(getResources().getDrawable(R.drawable.fill_heart));


                            } else if (remove.equals("yes")) {
                                like.setImageDrawable(getResources().getDrawable(R.drawable.unlike));
                                Toast.makeText(context, getString(R.string.remove_wishlist), Toast.LENGTH_SHORT).show();

                            }
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

    private void Showdialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        MaterialButton rele_close = dialog.findViewById(R.id.rele_close);
        MaterialButton rele_submit = dialog.findViewById(R.id.rele_submit);
        TextView txt_settingtitle = dialog.findViewById(R.id.txt_settingtitle);
        TextView txt_desc = dialog.findViewById(R.id.txt_desc);
        txt_settingtitle.setText(getString(R.string.warning));
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
                openFragment(fragment);
                dialog.dismiss();
            }
        });
    }

    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onremovelist(int position, Integer id) {

    }
}