package freaktemplate.shopping.activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nshmura.snappysmoothscroller.SnappyGridLayoutManager;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freaktemplate.shopping.R;
import freaktemplate.shopping.adapter.SearchAdapter;
import freaktemplate.shopping.fragment.DetailFragment;
import freaktemplate.shopping.getset.Search.Datum;
import freaktemplate.shopping.getset.Search.SearchPojo;
import freaktemplate.shopping.getset.Wish.WishPojo;
import freaktemplate.shopping.interfaces.CoutomProductLister;
import freaktemplate.shopping.interfaces.WishlistButtonListener;
import freaktemplate.shopping.utils.ConnectionDetector;
import freaktemplate.shopping.utils.SPmanager;
import freaktemplate.shopping.utils.UtilHelper;


public class SearchActivity extends AppCompatActivity implements View.OnClickListener, CoutomProductLister, WishlistButtonListener {
    private static final String TAG = "SearchActivity";
    private EditText edt_search;
    private RecyclerView recycle_search;
    private String search;
    private int pageCount;
    private SearchActivity context;
    private LottieAnimationView progressa;
    private Gson gson;
    private List<Datum> searchlist;
    private SearchAdapter searchAdapter;
    private String userID;
    private String nextpageurl;

    public static boolean checkInternet(Context context) {
        // TODO Auto-generated method stub
        ConnectionDetector cd = new ConnectionDetector(context);
        return cd.isConnectingToInternet();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        context = SearchActivity.this;
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
        pageCount = 1;
        searchlist = new ArrayList<>();
        inite();
    }

    private void inite() {
        recycle_search = findViewById(R.id.recycle_search);
        progressa = findViewById(R.id.progressa);
        edt_search = findViewById(R.id.edt_search);
        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH && checkInternet(context)) {
                    searchlist.clear();
                    getsearch();
                    return true;
                }
                return false;
            }
        });

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                search = s.toString();
                if (search.length() == 3) {
                    if (checkInternet(context)) {
                        searchlist.clear();
                        getsearch();
                    }
                } /*else {
                    if (checkInternet(context)) {
                        searchlist.clear();
                        getsearch();
                    }
                }*/
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) { // TODO
               /* search = s.toString();
                if (search.length() != 3) {
                    if (checkInternet(context)) {
                        searchlist.clear();
                        getsearch();
                    }
                } else {
                    if (checkInternet(context)) {
                        searchlist.clear();
                        getsearch();
                    }
                }*/
            }
        });
    }

    private void getsearch() {
        UtilHelper.showdialog(context);
        recycle_search.setVisibility(View.GONE);
        String url = getString(R.string.link) + "searchproduct";
        Log.e(TAG, "getsearch: " + url);
        AndroidNetworking.get(url)
                .addQueryParameter("search", edt_search.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse: " + response);
                        UtilHelper.hidedialog();
                        SearchPojo searchPojo = gson.fromJson(String.valueOf(response), SearchPojo.class);
                        if (searchPojo.getData().getStatus().equals(1)) {
                            searchlist = searchPojo.getData().getData().getData();
                            nextpageurl = searchPojo.getData().getData().getNextPageUrl();
                            setAdapter();
                        } else {
                            Toast.makeText(context, getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onError: " + anError.getMessage());
                        Log.e(TAG, "onError: " + anError.getErrorBody());
                        UtilHelper.hidedialog();
                        Toast.makeText(context, getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setAdapter() {
        UtilHelper.hidedialog();
        recycle_search.setVisibility(View.VISIBLE);
        SnappyGridLayoutManager snappyGridLayoutManager = new SnappyGridLayoutManager(context, 2);
        recycle_search.setLayoutManager(snappyGridLayoutManager);
        searchAdapter = new SearchAdapter(recycle_search, SearchActivity.this, searchlist);
        searchAdapter.CoutomProductButtonListener(this);
        searchAdapter.WishButtonListener(this);
        recycle_search.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();
        if (nextpageurl != null) {
            searchAdapter.setOnLoadMoreListener(new SearchAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    loadmore(pageCount);
                    progressa.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void loadmore(int pageCount) {
        Log.e(TAG, "getList: " + nextpageurl);
        AndroidNetworking.get(nextpageurl)
                .addQueryParameter("search", edt_search.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse: " + response);
                        SearchPojo searchPojo = gson.fromJson(String.valueOf(response), SearchPojo.class);
                        if (searchPojo.getData().getStatus().equals(1)) {
                            List<Datum> loadmorlist = searchPojo.getData().getData().getData();
                            nextpageurl = searchPojo.getData().getData().getNextPageUrl();
                            progressa.setVisibility(View.GONE);
                            if (loadmorlist.size() != 0) {
                                searchAdapter.setLoaded();
                                searchAdapter.addItem(loadmorlist, searchlist.size());
                                searchAdapter.notifyDataSetChanged();
                            }
                        } else {
                            progressa.setVisibility(View.GONE);
                            Toast.makeText(context, getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onError: " + anError.getMessage());
                        Log.e(TAG, "onError: " + anError.getMessage());
                        progressa.setVisibility(View.GONE);
                        Toast.makeText(context, getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void detailsfrag() {
        Fragment fragment = new DetailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void backpress(View view) {
        onBackPressed();
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void layoutClick(int position) {
        Map<String, String> data = new HashMap<>();
        data.put("itemID", String.valueOf(searchlist.get(position).getId()));
        data.put("name", searchlist.get(position).getName());
        data.put("price", searchlist.get(position).getPrice());
        data.put("image", searchlist.get(position).getBasicImage());
        EventBus.getDefault().postSticky(data);
        detailsfrag();
    }

    @Override
    public void onAddClicked(int position, ImageView like, ImageView unlike) {
        String id = String.valueOf(searchlist.get(position).getId());
        addwish(id, userID, like, unlike);
    }

    private void addwish(String id, String userID, ImageView like, ImageView unlike) {
        userID = SPmanager.getPreference(context, "userID");
        UtilHelper.showLikedialog(SearchActivity.this);
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
                                Toast.makeText(context, getString(R.string.add_wishlist), Toast.LENGTH_SHORT).show();
                                like.setImageDrawable(getResources().getDrawable(R.drawable.fill_heart));
                            } else if (remove.equals("yes")) {
                                like.setImageDrawable(getResources().getDrawable(R.drawable.like));
                                Toast.makeText(context, getString(R.string.remove_wishlist), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
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

    @Override
    public void onremovelist(int position, Integer id) {
    }
}