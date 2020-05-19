package freaktemplate.shopping.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freaktemplate.shopping.MyApplication;
import freaktemplate.shopping.R;
import freaktemplate.shopping.adapter.BrandByAdapter;
import freaktemplate.shopping.adapter.ColorAdapter;
import freaktemplate.shopping.adapter.PriceByAdapter;
import freaktemplate.shopping.adapter.RatingByAdapter;
import freaktemplate.shopping.adapter.SizeByAdapter;
import freaktemplate.shopping.adapter.SortByAdapter;
import freaktemplate.shopping.getset.Filter.Brand;
import freaktemplate.shopping.getset.Filter.Color;
import freaktemplate.shopping.getset.RateGetSet;
import freaktemplate.shopping.getset.refine.Sortbypojjo;
import freaktemplate.shopping.interfaces.CoutomSortbyListner;

public class FilterFragment extends Fragment implements CoutomSortbyListner, View.OnClickListener {

    private RecyclerView recycle_sizer;
    private RecyclerView recycle_brandby;
    private RecyclerView recycle_rates;
    private RecyclerView recycle_sortby;
    private RecyclerView recycle_color;
    private RecyclerView recycle_price;

    private List<String> sizeList;
    private List<RateGetSet> rateList;
    private List<Brand> brandList;

    private Context context;
    private MyApplication myApp;
    private String filterId = "0";
    private TextView txt_colorby;
    private TextView txt_priceby;
    private TextView txt_sizeby;
    private String category = String.valueOf(1);
    private String subcategory = String.valueOf(0);
    private String brand = String.valueOf(0);
    private String price = String.valueOf(0);
    private String discount = String.valueOf(0);
    private String ratting = String.valueOf(0);
    private String colors = String.valueOf(0);
    private String size = String.valueOf(0);
    private String coupon_id = String.valueOf(0);
    private View view;
    private String mainCat;
    private String layouts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_filter, container, false);
        context = view.getContext();
        myApp = MyApplication.getInstance();
        init();
        getsortbyList();
        getcolorlist();
        getsizeList();
        getpriceList();
        getrateList();
        getbrandlist();
        return view;
    }

    private void init() {
        recycle_price = view.findViewById(R.id.recycle_price);
        recycle_color = view.findViewById(R.id.recycle_color);
        recycle_sortby = view.findViewById(R.id.recycle_sortby);
        recycle_sizer = view.findViewById(R.id.recycle_sizer);
        recycle_rates = view.findViewById(R.id.recycle_rates);
        recycle_brandby = view.findViewById(R.id.recycle_brandby);
        TextView txt_showresult = view.findViewById(R.id.txt_showresult);
        txt_sizeby = view.findViewById(R.id.txt_sizeby);
        txt_priceby = view.findViewById(R.id.txt_priceby);
        txt_colorby = view.findViewById(R.id.txt_colorby);
        ImageView ic_back = view.findViewById(R.id.ic_back);
        getFragmentdata();
        txt_showresult.setOnClickListener(this);
        ic_back.setOnClickListener(this);
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
//                    getFragmentManager().popBackStack();
                    Map<String, String> data = new HashMap<>();
                    data = new HashMap<>();
                    data.put("category", category);
                    data.put("subcategory", subcategory);
                    data.put("brand", "0");
                    data.put("price", "0");
                    data.put("discount", "0");
                    data.put("ratting", "0");
                    data.put("filter", "0");
                    data.put("color", "0");
                    data.put("size", "0");
                    data.put("layout", layouts);
                    data.put("mainCat", mainCat);
                    data.put("coupon_id", coupon_id);
                    EventBus.getDefault().postSticky(data);
                    viewMorefrag();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_showresult:
                Map<String, String> data = new HashMap<>();
                data.put("category", category);
                data.put("subcategory", subcategory);
                data.put("brand", brand);
                data.put("price", price);
                data.put("discount", discount);
                data.put("ratting", ratting);
                data.put("filter", filterId);
                data.put("color", colors);
                data.put("size", size);
                data.put("mainCat", mainCat);
                data.put("layout", layouts);
                data.put("coupon_id", coupon_id);
                EventBus.getDefault().postSticky(data);
                viewMorefrag();
                break;
            case R.id.ic_back:
                data = new HashMap<>();
                data.put("category", category);
                data.put("subcategory", subcategory);
                data.put("brand", "0");
                data.put("price", "0");
                data.put("discount", "0");
                data.put("ratting", "0");
                data.put("filter", "0");
                data.put("color", "0");
                data.put("size", "0");
                data.put("layout", layouts);
                data.put("mainCat", mainCat);
                data.put("coupon_id", coupon_id);
                EventBus.getDefault().postSticky(data);
                viewMorefrag();
                break;
        }
    }

    private void getFragmentdata() {
        try {
            Map<String, String> data = EventBus.getDefault().getStickyEvent(HashMap.class);
            category = data.get("category");
            subcategory = data.get("subcategory");
            brand = data.get("brand");
            price = data.get("price");
            discount = data.get("discount");
            ratting = data.get("ratting");
            filterId = data.get("filter");
            colors = data.get("color");
            size = data.get("size");
            mainCat = data.get("mainCat");
            layouts = data.get("layout");
            coupon_id = data.get("coupon_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getbrandlist() {
        try {
            brandList = myApp.getBrndlist();
            if (brandList.size() != 0) {
                LinearLayoutManager verticalLayoutManager1 = new LinearLayoutManager(context);
                recycle_brandby.setLayoutManager(verticalLayoutManager1);
                BrandByAdapter sortByAdapter = new BrandByAdapter(context, brandList);
                recycle_brandby.setAdapter(sortByAdapter);
                sortByAdapter.setSortListener(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getrateList() {
        rateList = new ArrayList<>();
        RateGetSet sortbypojjo = new RateGetSet();
        sortbypojjo.setRate("5");
        rateList.add(sortbypojjo);
        RateGetSet sortbypojjo2 = new RateGetSet();
        sortbypojjo2.setRate("4");
        rateList.add(sortbypojjo2);
        RateGetSet sortbypojjo3 = new RateGetSet();
        sortbypojjo3.setRate("3");
        rateList.add(sortbypojjo3);
        RateGetSet sortbypojjo4 = new RateGetSet();
        sortbypojjo4.setRate("2");
        rateList.add(sortbypojjo4);
        RateGetSet sortbypojjo5 = new RateGetSet();
        sortbypojjo5.setRate("1");
        rateList.add(sortbypojjo5);

        LinearLayoutManager verticalLayoutManager1 = new LinearLayoutManager(context);
        recycle_rates.setLayoutManager(verticalLayoutManager1);
        RatingByAdapter sortByAdapter = new RatingByAdapter(rateList);
        recycle_rates.setAdapter(sortByAdapter);
        sortByAdapter.setSortListener(this);
    }

    private void getpriceList() {
        try {
            sizeList = myApp.getPriceList();
            if (sizeList.size() != 0) {
                txt_priceby.setVisibility(View.VISIBLE);
                LinearLayoutManager verticalLayoutManager1 = new LinearLayoutManager(context);
                recycle_price.setLayoutManager(verticalLayoutManager1);
                PriceByAdapter sortByAdapter = new PriceByAdapter(sizeList);
                recycle_price.setAdapter(sortByAdapter);
                sortByAdapter.setSortListener(this);
            } else {
                txt_priceby.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            txt_priceby.setVisibility(View.GONE);
        }
    }

    private void getsizeList() {
        try {
            sizeList = myApp.getSizeList();
            if (sizeList.size() != 0) {
                txt_sizeby.setVisibility(View.VISIBLE);
                LinearLayoutManager verticalLayoutManager1 = new LinearLayoutManager(context);
                recycle_sizer.setLayoutManager(verticalLayoutManager1);
                SizeByAdapter sortByAdapter = new SizeByAdapter(sizeList);
                recycle_sizer.setAdapter(sortByAdapter);
                sortByAdapter.setSortListener(this);
            } else {
                txt_sizeby.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            txt_sizeby.setVisibility(View.GONE);
        }
    }

    private void getcolorlist() {
        try {
            List<Color> colorList = myApp.getColorList();
            if (colorList.size() != 0) {
                txt_colorby.setVisibility(View.VISIBLE);
                LinearLayoutManager verticalLayoutManager1 = new LinearLayoutManager(context);
                recycle_color.setLayoutManager(verticalLayoutManager1);
                ColorAdapter sortByAdapter = new ColorAdapter(colorList);
                recycle_color.setAdapter(sortByAdapter);
                sortByAdapter.setSortListener(this);
            } else {
                txt_colorby.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            txt_colorby.setVisibility(View.GONE);
        }
    }

    private void getsortbyList() {
        List<Sortbypojjo> sortList = new ArrayList<>();
        Sortbypojjo sortbypojjo = new Sortbypojjo();
        sortbypojjo.setName(getString(R.string.popular_item));
        sortList.add(sortbypojjo);
        Sortbypojjo sortbypojjo3 = new Sortbypojjo();
        sortbypojjo3.setName(getString(R.string.low_to_high));
        sortList.add(sortbypojjo3);
        Sortbypojjo sortbypojjo4 = new Sortbypojjo();
        sortbypojjo4.setName(getString(R.string.high_to_low));
        sortList.add(sortbypojjo4);
        Sortbypojjo sortbypojjo5 = new Sortbypojjo();
        sortbypojjo5.setName(getString(R.string.latest));
        sortList.add(sortbypojjo5);
        LinearLayoutManager verticalLayoutManager1 = new LinearLayoutManager(context);
        recycle_sortby.setLayoutManager(verticalLayoutManager1);
        SortByAdapter sortByAdapter = new SortByAdapter(sortList);
        recycle_sortby.setAdapter(sortByAdapter);
        sortByAdapter.setSortListener(this);

    }


    @Override
    public void sortclick(int position) {
        filterId = String.valueOf(position + 1);
    }

    @Override
    public void brandClick(int position) {
        brand = String.valueOf(brandList.get(position).getId());
    }

    @Override
    public void colorClick(int position) {
        colors = myApp.getColorList().get(position).getCode();
    }

    @Override
    public void sizeClick(int position) {
        size = myApp.getSizeList().get(position);
    }

    @Override
    public void priceClick(int position) {
        price = myApp.getPriceList().get(position);
    }

    @Override
    public void reviewClick(int position) {
        ratting = rateList.get(position).getRate();
    }

    private void viewMorefrag() {
        Fragment fragment = new MoreproductFragment();
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
}