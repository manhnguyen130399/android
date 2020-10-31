package manhnguyen.shopping.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interceptors.GzipRequestInterceptor;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nshmura.snappysmoothscroller.SnappyGridLayoutManager;
import com.nshmura.snappysmoothscroller.SnappyLinearLayoutManager;

import net.skoumal.fragmentback.BackFragment;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import manhnguyen.shopping.R;
import manhnguyen.shopping.activity.OfferActivity;
import manhnguyen.shopping.activity.SearchActivity;
import manhnguyen.shopping.adapter.ItemListAdapter;
import manhnguyen.shopping.adapter.MainOfferAdapter;
import manhnguyen.shopping.adapter.ProductOfferAdapter;
import manhnguyen.shopping.getset.Wish.WishPojo;
import manhnguyen.shopping.getset.bestseling.BestSellingpojo;
import manhnguyen.shopping.getset.bestseling.Data;
import manhnguyen.shopping.getset.bestseling.Datum;
import manhnguyen.shopping.getset.notification.Tokendata;
import manhnguyen.shopping.getset.offerlist.Offer;
import manhnguyen.shopping.getset.offerlist.Offerpojjo;
import manhnguyen.shopping.interfaces.CoutomProductLister;
import manhnguyen.shopping.interfaces.WishlistButtonListener;
import manhnguyen.shopping.utils.AdManager;
import manhnguyen.shopping.utils.SPmanager;
import manhnguyen.shopping.utils.UtilHelper;
import me.relex.circleindicator.CircleIndicator2;
import okhttp3.OkHttpClient;

import static manhnguyen.shopping.activity.HomeActivity.ic_cart;
import static manhnguyen.shopping.activity.HomeActivity.ic_favrite;
import static manhnguyen.shopping.activity.HomeActivity.ic_home;
import static manhnguyen.shopping.activity.HomeActivity.ic_user;
import static manhnguyen.shopping.activity.HomeActivity.rel_cart;
import static manhnguyen.shopping.activity.HomeActivity.rel_favorite;
import static manhnguyen.shopping.activity.HomeActivity.rel_home;
import static manhnguyen.shopping.activity.HomeActivity.rel_user;
import static manhnguyen.shopping.activity.HomeActivity.txt_cart;
import static manhnguyen.shopping.activity.HomeActivity.txt_cartitem;
import static manhnguyen.shopping.activity.HomeActivity.txt_favrite;
import static manhnguyen.shopping.activity.HomeActivity.txt_home;
import static manhnguyen.shopping.activity.HomeActivity.txt_user;
import static manhnguyen.shopping.activity.HomeActivity.txt_wishitem;
import static manhnguyen.shopping.utils.UtilHelper.UNRELIABLE_INTEGER_FACTORY;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener, BackFragment, CoutomProductLister, WishlistButtonListener {
    private static final String TAG = "HomeFragment";
    private View view;
    private ShimmerRecyclerView recycle_products;
    private ShimmerRecyclerView recycle_offer;
    private ShimmerRecyclerView recycle_ads;
    private List<Datum> itemList;
    private CircleIndicator2 indicator;
    private Context context;
    private String userID;
    private int pageCount;
    private Gson gson;
    private OkHttpClient okHttpClient;
    private LottieAnimationView progress;
    private ItemListAdapter itemListAdapter;
    private TextView txt_total;
    private TextView txt_noffer;
    private TextView txt_nooffer;
    private List<Offer> adsList;
    private List<Offer> offerList;
    private ImageView ic_dummy;
    private String nextpage_url;


    public HomeFragment() {
        // Required empty public constructor
    }

    private void ViewProductfrag() {
        Fragment fragment = new MoreproductFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void detailsfrag() {
        Fragment fragment = new DetailFragment();
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        context = view.getContext();
        pageCount = 1;
        userID = SPmanager.getPreference(context, "userID");
        setview();
        if (userID == null) {
            userID = "0";
        }
        itemList = new ArrayList<>();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder
                .registerTypeAdapterFactory(UNRELIABLE_INTEGER_FACTORY)

                .create();
        okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(new GzipRequestInterceptor())
                .build();
        if (!SPmanager.getFirsttime(context)) {
            String token = SPmanager.getPreference(context, "token");
            if (token != null) {
                sendTokentoserver(SPmanager.getPreference(context, "token"));
            }
        }
        init();
        SPmanager.setBilling(view.getContext(), true);
        getOfferList();
        getBestsellingList();
        return view;
    }


    private void sendTokentoserver(String token) {
        String url = getString(R.string.link) + "save_token?token=" + token + "&type=1";
        Log.e(TAG, "sendTokentoserver: " + url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse: " + response);
                        try {
                            Tokendata tokendata = gson.fromJson(String.valueOf(response), Tokendata.class);
                            Integer status = tokendata.getData().getStatus();
                            if (status.equals(1)) {
                                SPmanager.setFirsttime(context, true);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onError: " + anError.getErrorBody());
                    }
                });
    }

    private void setview() {
        rel_cart.setBackgroundResource(0);
        rel_favorite.setBackgroundResource(0);
        rel_user.setBackgroundResource(0);
        rel_home.setBackgroundResource(R.drawable.homerect);

        txt_home.setVisibility(View.VISIBLE);
        txt_user.setVisibility(View.GONE);
        txt_cart.setVisibility(View.GONE);
        txt_favrite.setVisibility(View.GONE);

        ic_favrite.setBackgroundResource(R.drawable.like_not);
        ic_cart.setBackgroundResource(R.drawable.cart_not);
        ic_user.setBackgroundResource(R.drawable.user_not);
        ic_home.setBackgroundResource(R.drawable.home);
    }


    private void getOfferList() {
        recycle_ads.showShimmerAdapter();
        recycle_offer.showShimmerAdapter();
        String url = getString(R.string.link) + "mainoffers";
        Log.e(TAG, "getOfferList: " + url);
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse: " + response);
                        adsList = new ArrayList<>();
                        offerList = new ArrayList<>();
                        try {
                            Offerpojjo offerpojjo = gson.fromJson(String.valueOf(response), Offerpojjo.class);
                            if (offerpojjo.getStatus().equals(1)) {
                                List<Offer> offer = offerpojjo.getOffers();
                                for (int i = 0; i < offer.size(); i++) {

                                    String offertype = offer.get(i).getOfferType();
                                    if (offertype != null) {
                                        if (offertype.equals("1")) {
                                            Offer offer1 = new Offer();
                                            getoffer(offer1, offer, i);
                                            adsList.add(offer1);

                                        } else if (offertype.equals("2")) {
                                            Offer offer2 = new Offer();
                                            getoffer(offer2, offer, i);
                                            offerList.add(offer2);
                                        }
                                    }
                                }
                                setMainOfferList();
                                setproductofferList();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            recycle_ads.hideShimmerAdapter();
                            recycle_offer.hideShimmerAdapter();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onError: " + anError.getErrorBody());
                        recycle_ads.hideShimmerAdapter();
                        recycle_offer.hideShimmerAdapter();

                    }
                });

    }

    private void getoffer(Offer offer1, List<Offer> offer, int i) {
        offer1.setId(offer.get(i).getId());
        offer1.setTitle(offer.get(i).getTitle());
        offer1.setMainTitle(offer.get(i).getMainTitle());
        offer1.setBanner(offer.get(i).getBanner());
        offer1.setStartDate(offer.get(i).getStartDate());
        offer1.setEndDate(offer.get(i).getEndDate());
        offer1.setFixed(offer.get(i).getFixed());
        offer1.setIsProduct(offer.get(i).getIsProduct());
        offer1.setProductId(offer.get(i).getProductId());
        offer1.setCategoryId(offer.get(i).getCategoryId());
        offer1.setNewPrice(offer.get(i).getNewPrice());
        offer1.setIsActive(offer.get(i).getIsActive());
    }

    private void setproductofferList() {
        recycle_offer.hideShimmerAdapter();
        if (offerList.size() != 0) {
            txt_noffer.setVisibility(View.GONE);
            final SnappyLinearLayoutManager verticalLayoutManager1 = new SnappyLinearLayoutManager(context, SnappyLinearLayoutManager.HORIZONTAL, false);
            recycle_offer.setLayoutManager(verticalLayoutManager1);
            final ProductOfferAdapter specialOfferAdapter = new ProductOfferAdapter(view.getContext(), offerList);
            recycle_offer.setAdapter(specialOfferAdapter);
        } else {
            txt_noffer.setVisibility(View.VISIBLE);
            recycle_offer.setAdapter(null);
        }
    }

    private void setMainOfferList() {
        recycle_ads.hideShimmerAdapter();
        if (adsList.size() != 0) {
            txt_nooffer.setVisibility(View.GONE);
            final SnappyLinearLayoutManager verticalLayoutManager1 = new SnappyLinearLayoutManager(context, SnappyLinearLayoutManager.HORIZONTAL, false);
            recycle_ads.setLayoutManager(verticalLayoutManager1);
            final MainOfferAdapter specialOfferAdapter = new MainOfferAdapter(view.getContext(), adsList);
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
            recycle_ads.setAdapter(null);
            indicator.setVisibility(View.GONE);
            ic_dummy.setVisibility(View.VISIBLE);
        }
    }

    private void init() {
        rel_cart.setBackgroundResource(0);
        rel_favorite.setBackgroundResource(0);
        rel_user.setBackgroundResource(0);
        rel_home.setBackgroundResource(R.drawable.homerect);

        txt_home.setVisibility(View.VISIBLE);
        txt_user.setVisibility(View.GONE);
        txt_cart.setVisibility(View.GONE);
        txt_favrite.setVisibility(View.GONE);

        ic_favrite.setBackgroundResource(R.drawable.like_not);
        ic_cart.setBackgroundResource(R.drawable.cart_not);
        ic_user.setBackgroundResource(R.drawable.user_not);
        ic_home.setBackgroundResource(R.drawable.home);

        progress = view.findViewById(R.id.progress);
        recycle_ads = view.findViewById(R.id.recycle_ads);
        recycle_offer = view.findViewById(R.id.recycle_offer);
        recycle_products = view.findViewById(R.id.recycle_products);
        indicator = view.findViewById(R.id.indicator);
        LinearLayout lay_viewmore = view.findViewById(R.id.lay_viewmore);
        LinearLayout lay_viewmore1 = view.findViewById(R.id.lay_viewmore1);
        txt_total = view.findViewById(R.id.txt_total);
        ic_dummy = view.findViewById(R.id.ic_dummy);
        ImageView img_search = view.findViewById(R.id.img_search);
        txt_nooffer = view.findViewById(R.id.txt_nooffer);
        txt_noffer = view.findViewById(R.id.txt_noffer);
        lay_viewmore.setOnClickListener(this);
        lay_viewmore1.setOnClickListener(this);
        img_search.setOnClickListener(this);
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
                    showDialog();
                    return true;
                }
                return false;
            }
        });
    }

    private void getBestsellingList() {
        recycle_products.showShimmerAdapter();
        String url = getString(R.string.link) + "bestselling/" + userID + "?page=" + pageCount;
        Log.e(TAG, "getBestsellingList: " + url);
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .setOkHttpClient(okHttpClient)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse: " + response);
                        try {
                            BestSellingpojo responseObject = gson.fromJson(String.valueOf(response), BestSellingpojo.class);

                            if (responseObject.getStatus().equals(1)) {
                                Data data = responseObject.getProduct().getData();
                                List<Datum> datas = data.getData();

                                for (int i = 0; i < datas.size(); i++) {
                                    Datum datum = datas.get(i);

                                    if (getString(R.string.show_admmob_ads).equals("yes") || getString(R.string.show_facebook_ads).equals("yes")) {
                                        if (i % 4 == 0 && i != 0) {
                                            itemList.add(null);
                                        }
                                    }
                                    itemList.add(datum);
                                }
                                txt_total.setText(data.getTotal() + " " + context.getResources().getString(R.string.items_found));
                                txt_wishitem.setText(String.valueOf(responseObject.getProduct().getTotalwish()));
                                txt_cartitem.setText(String.valueOf(responseObject.getProduct().getCarttotal()));
                                nextpage_url = data.getNextPageUrl();
                                setBestselingUi();


                            } else {
                                txt_wishitem.setText(String.valueOf(responseObject.getProduct().getTotalwish()));
                                txt_cartitem.setText(String.valueOf(responseObject.getProduct().getCarttotal()));
                                recycle_products.hideShimmerAdapter();
                                txt_total.setText(0 + " " + context.getResources().getString(R.string.items_found));
                                Toast.makeText(context, context.getResources().getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            txt_total.setText(0 + " " + context.getResources().getString(R.string.items_found));
                            recycle_products.hideShimmerAdapter();
                            Toast.makeText(context, R.string.no_data, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        recycle_products.hideShimmerAdapter();
                        txt_total.setText(0 + " " + getString(R.string.items_found));
                        Log.e(TAG, "onError: " + anError.getErrorBody());
                        Toast.makeText(view.getContext(), getString(R.string.no_data), Toast.LENGTH_SHORT).show();

                    }
                });


    }

    private void setBestselingUi() {
        recycle_products.hideShimmerAdapter();
        SnappyGridLayoutManager snappyGridLayoutManager = new SnappyGridLayoutManager(context, 2);
        recycle_products.setLayoutManager(snappyGridLayoutManager);
        snappyGridLayoutManager.setSpanSizeLookup(new SnappyGridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (recycle_products.getAdapter().getItemViewType(position) == 1) {
                    return 1;
                }
                return 2;
            }
        });
        itemListAdapter = new ItemListAdapter(recycle_products, context, itemList, "home");
        recycle_products.setAdapter(itemListAdapter);

        itemListAdapter.setOnLoadMoreListener(new ItemListAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                if (nextpage_url != null) {
                    progress.setVisibility(View.VISIBLE);
                    loadmorebest(nextpage_url);
                }

            }
        });

        itemListAdapter.CoutomProductButtonListener(this);
        itemListAdapter.setCustomWishButtonListener(this);
    }

    private void loadmorebest(String nextPageUrl) {
        List<Datum> loadmoreList = new ArrayList<>();
        AndroidNetworking.get(nextPageUrl)
                .setPriority(Priority.MEDIUM)
                .setOkHttpClient(okHttpClient)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            BestSellingpojo responseObject = gson.fromJson(String.valueOf(response), BestSellingpojo.class);
                            if (responseObject.getStatus().equals(1)) {
                                Data data = responseObject.getProduct().getData();
                                List<Datum> datas = data.getData();
                                for (int i = 0; i < datas.size(); i++) {
                                    Datum datum = datas.get(i);
                                    if (getString(R.string.show_admmob_ads).equals("yes") || getString(R.string.show_facebook_ads).equals("yes")) {
                                        if (i % 4 == 0 && i != 0) {
                                            loadmoreList.add(null);
                                        }
                                    }
                                    loadmoreList.add(datum);
                                }
                                nextpage_url = data.getNextPageUrl();
                                progress.setVisibility(View.GONE);

                                if (loadmoreList.size() != 0) {
                                    Log.e("adapter", "" + loadmoreList.size());

                                    itemListAdapter.addItem(loadmoreList, itemList.size());
                                    itemListAdapter.setLoaded();
                                }

                            } else {
                                progress.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        progress.setVisibility(View.GONE);
                        Log.e(TAG, "onError: " + anError.getErrorBody());
                    }
                });
    }

    @Override
    public void onClick(View v) {
        int viewID = v.getId();
        switch (viewID) {
            case R.id.lay_viewmore:
                Intent intent = new Intent(getActivity(), OfferActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                AdManager.increaseCount(context);
                AdManager.showInterstial(context);
                break;
            case R.id.lay_viewmore1:
                Viwallproduct();
                break;
            case R.id.img_search:
                intent = new Intent(getActivity(), SearchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                AdManager.increaseCount(context);
                AdManager.showInterstial(context);
                break;
        }
    }

    private void Viwallproduct() {
        Map<String, String> data = new HashMap<>();
        data.put("category", "1");
        data.put("subcategory", "0");
        data.put("brand", "0");
        data.put("price", "0");
        data.put("discount", "0");
        data.put("ratting", "0");
        data.put("filter", "0");
        data.put("color", "0");
        data.put("size", "0");
        data.put("mainCat", "All Brand");
        data.put("layout", "home");
        data.put("coupon_id", "0");
        EventBus.getDefault().postSticky(data);
        AdManager.increaseCount(context);
        AdManager.showInterstial(context);
        ViewProductfrag();
    }

    @Override
    public void layoutClick(int position) {
        Map<String, String> data = new HashMap<>();
        data.put("itemID", String.valueOf(itemList.get(position).getProductdetail().getId()));
        data.put("name", itemList.get(position).getProductdetail().getName());
        data.put("price", itemList.get(position).getProductdetail().getPrice());
        data.put("image", itemList.get(position).getProductdetail().getImage());
        data.put("key", "home");
        EventBus.getDefault().postSticky(data);
        AdManager.increaseCount(context);
        AdManager.showInterstial(context);
        detailsfrag();
    }


    private void addwish(String id, String userID, final ImageView like, final ImageView unlike) {
        userID = SPmanager.getPreference(context, "userID");
        UtilHelper.showLikedialog(context);
        String url = getString(R.string.link) + "addwish?product_id=" + id + "&user_id=" + userID;
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
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
    public void onAddClicked(int position, ImageView like, ImageView unlike) {
        String id = String.valueOf(itemList.get(position).getProductdetail().getId());
        if (userID != null && !userID.equals("0")) {
            addwish(id, userID, like, unlike);
        } else {
            Showdialog();
        }
    }

    @Override
    public void onremovelist(int position, Integer id) {

    }

    private void showDialog() {
        Dialog dialog;
        MaterialButton rele_close, rele_submit;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        rele_submit = dialog.findViewById(R.id.rele_submit);
        rele_close = dialog.findViewById(R.id.rele_close);
        dialog.show();

        rele_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        rele_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                getActivity().finishAffinity();
            }
        });

    }

    @Override
    public boolean onBackPressed() {
        showDialog();
        return false;
    }

    @Override
    public int getBackPriority() {
        return HIGH_BACK_PRIORITY;
    }
}