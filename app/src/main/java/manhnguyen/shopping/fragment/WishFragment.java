package manhnguyen.shopping.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.airbnb.lottie.LottieAnimationView;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.todkars.shimmer.ShimmerRecyclerView;

import net.skoumal.fragmentback.BackFragment;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import manhnguyen.shopping.R;
import manhnguyen.shopping.activity.HomeActivity;
import manhnguyen.shopping.adapter.WishlistAdapter;
import manhnguyen.shopping.getset.Wish.WishPojo;
import manhnguyen.shopping.getset.wishlist.Wishpojjo;
import manhnguyen.shopping.interfaces.CoutomProductLister;
import manhnguyen.shopping.interfaces.WishlistButtonListener;
import manhnguyen.shopping.utils.SPmanager;
import manhnguyen.shopping.utils.UtilHelper;

public class WishFragment extends Fragment implements BackFragment, WishlistButtonListener, CoutomProductLister {
    private static final String TAG = "WishFragment";
    private ShimmerRecyclerView recycle_wish;
    private View rootview;
    private Context context;
    private LottieAnimationView content_load;
    private Gson gson;
    private WishlistAdapter adapter;
    private List<Wishpojjo.Wish> wish;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.activity_wish_list, container, false);
        context = rootview.getContext();
        wish = new ArrayList<>();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        setview();
        inite();
        return rootview;
    }

    private void setview() {
        HomeActivity.rel_cart.setBackgroundResource(0);
        HomeActivity.rel_favorite.setBackgroundResource(R.drawable.homerect);
        HomeActivity.rel_user.setBackgroundResource(0);
        HomeActivity.rel_home.setBackgroundResource(0);

        HomeActivity.txt_home.setVisibility(View.GONE);
        HomeActivity.txt_user.setVisibility(View.GONE);
        HomeActivity.txt_cart.setVisibility(View.GONE);
        HomeActivity.txt_favrite.setVisibility(View.VISIBLE);

        HomeActivity.ic_favrite.setBackgroundResource(R.drawable.like);
        HomeActivity.ic_cart.setBackgroundResource(R.drawable.cart_not);
        HomeActivity.ic_user.setBackgroundResource(R.drawable.user_not);
        HomeActivity.ic_home.setBackgroundResource(R.drawable.home_not);
    }

    private void inite() {
        recycle_wish = rootview.findViewById(R.id.recycle_wish);
        content_load = rootview.findViewById(R.id.content_load);
        ImageView ic_back = rootview.findViewById(R.id.ic_back);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(rootview.getContext(), HomeActivity.class);
                startActivity(i);
            }
        });
        getList();
    }

    private void getList() {
        recycle_wish.showShimmer();
        final String userID = SPmanager.getPreference(context, "userID");
        String url = getString(R.string.link) + "getwishlist?user_id=" + userID;
        AndroidNetworking.get(url)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse: " + response);
                        wish.clear();
                        try {
                            Wishpojjo wishpojjo = gson.fromJson(String.valueOf(response), Wishpojjo.class);
                            if (wishpojjo.getStatus().equals(1)) {
                                List<Wishpojjo.Wish> wishlist = wishpojjo.getWish();
                                for (int i = 0; i < wishlist.size(); i++) {
                                    Wishpojjo.Wish wish1 = wishlist.get(i);
                                    if (getString(R.string.show_admmob_ads).equals("yes") || getString(R.string.show_facebook_ads).equals("yes")) {
                                        if (i % 4 == 0 && i != 0) {
                                            wish.add(null);
                                        }
                                    }
                                    wish.add(wish1);
                                }
                                setwishList(wish);
                            } else {
                                Toast.makeText(context, R.string.no_wishlist, Toast.LENGTH_SHORT).show();
                                recycle_wish.hideShimmer();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            recycle_wish.hideShimmer();
                            Log.e(TAG, "onResponse: " + e.getMessage());
                            try {
                                if (userID.equals("null")) {
                                    Toast.makeText(context, R.string.login_warning, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, getString(R.string.no_wishlist), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                Toast.makeText(context, R.string.login_warning, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onError: " + anError.getErrorBody());
                        Log.e(TAG, "onError: " + anError.getErrorDetail());
                        recycle_wish.hideShimmer();
                        try {
                            if (userID.equals("null")) {
                                Toast.makeText(context, R.string.login_warning, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(context, getString(R.string.no_wishlist), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Toast.makeText(context, R.string.login_warning, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void setwishList(List<Wishpojjo.Wish> wish) {
        content_load.setVisibility(View.GONE);
        recycle_wish.hideShimmer();
        LinearLayoutManager layoutManager = new LinearLayoutManager(rootview.getContext());
        recycle_wish.setLayoutManager(layoutManager);
        adapter = new WishlistAdapter(rootview.getContext(), wish);
        recycle_wish.setAdapter(adapter);
        adapter.setCustomWishButtonListener(this);
        adapter.CoutomProductButtonListener(this);
    }

    @Override
    public boolean onBackPressed() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getActivity().startActivity(intent);
        return false;
    }

    @Override
    public int getBackPriority() {
        return NORMAL_BACK_PRIORITY;
    }

    @Override
    public void onAddClicked(int position, ImageView like, ImageView unlike) {

    }

    @Override
    public void onremovelist(int position, Integer id) {
        UtilHelper.showdialog(context);
        String userID = SPmanager.getPreference(context, "userID");
        String url = getString(R.string.link) + "addwish?product_id=" + id + "&user_id=" + userID;
        Log.e(TAG, "addwish: " + url);
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        WishPojo wishPojo = gson.fromJson(String.valueOf(response), WishPojo.class);
                        if (wishPojo.getStatus().equals(1)) {
                            HomeActivity.txt_wishitem.setText(String.valueOf(wishPojo.getWish()));
                            wish.remove(position);
                            adapter.notifyItemRemoved(position);
                            adapter.notifyItemRangeChanged(position, wish.size());
                            UtilHelper.hidedialog();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(context, R.string.something_wrong, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onError: " + anError.getErrorBody());
                        Log.e(TAG, "onError1: " + anError.getMessage());
                        UtilHelper.hidedialog();
                    }
                });
    }

    @Override
    public void layoutClick(int position) {
        Map<String, String> data = new HashMap<>();
        data.put("itemID", String.valueOf(wish.get(position).getProduct().getId()));
        data.put("name", wish.get(position).getProduct().getName());
        data.put("price", String.valueOf(wish.get(position).getProduct().getDiscount()));
        data.put("image", wish.get(position).getProduct().getBasicImage());
        data.put("key", "home");
        EventBus.getDefault().postSticky(data);
        Fragment fragment = new DetailFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}