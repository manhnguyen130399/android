package freaktemplate.shopping.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import freaktemplate.shopping.MyApplication;
import freaktemplate.shopping.R;
import freaktemplate.shopping.fragment.OrderDetail;
import freaktemplate.shopping.fragment.OrderStatus;
import freaktemplate.shopping.getset.OrderDetail.OrderDetailPojo;
import freaktemplate.shopping.getset.OrderDetail.OrderDetails;
import freaktemplate.shopping.getset.OrderDetail.OrderStatusDetails;
import freaktemplate.shopping.utils.UtilHelper;

public class MyOrderActivity extends AppCompatActivity {
    private static final String TAG = "MyOrderActivity";
    private ViewPager viewPager;
    private XTabLayout tab;
    private TextView text_id;
    private TextView text_date;
    private String order_id;
    private Gson gson;
    private MyApplication myApp;
    private String key;
    private ImageView ic_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        myApp = MyApplication.getInstance();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        getIntents();
        inite();
        getList();
    }

    private void getIntents() {
        Intent intent = getIntent();
        order_id = intent.getStringExtra("order_id");
        key = intent.getStringExtra("key");
    }

    private void inite() {
        viewPager = findViewById(R.id.viewpager);
        text_date = findViewById(R.id.text_date);
        text_id = findViewById(R.id.text_id);
        ic_back = findViewById(R.id.ic_back);
        tab = findViewById(R.id.tabs);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getList() {
        UtilHelper.showdialog(MyOrderActivity.this);
        String url = getString(R.string.link) + "vieworder/" + order_id;
        Log.e(TAG, "getSubList: " + url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "onResponse: " + response);
                        OrderDetailPojo detailPojo = gson.fromJson(response, OrderDetailPojo.class);
                        UtilHelper.hidedialog();
                        try {
                            if (detailPojo.getData().getStatus().equals(1)) {
                                String date = detailPojo.getData().getData().getOrderDate();
                                order_id = detailPojo.getData().getData().getOrderId();
                                OrderDetails orderdetail = detailPojo.getData().getData().getOrderDetails();
                                myApp.setOrderDetails(orderdetail);
                                OrderStatusDetails orderstatusdetail = detailPojo.getData().getData().getOrderStatusDetails();
                                myApp.setOrderStatusDetails(orderstatusdetail);
                                String staus = detailPojo.getData().getData().getOrderStatus();
                                myApp.setOrderstatus(staus);
                                text_id.setText(order_id);
                                setDate(date);
                                setViewpager();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        UtilHelper.hidedialog();
                        Log.e(TAG, "onError: " + anError.getMessage());
                    }
                });
    }

    private void setDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        try {
            Date newdate = dateFormat.parse(date);
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd, MMM yyyy", Locale.ENGLISH);
            String date1 = dateFormat1.format(newdate);
            text_date.setText(date1);
            myApp.setOrderdate(date1);
        } catch (ParseException e) {
            Log.e(TAG, "setDate: " + e.getMessage());
        }
    }

    private void setViewpager() {
        setupViewPager(viewPager);
        tab.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (key.equals("my_order")) {
            Intent i = new Intent(MyOrderActivity.this, OrderList.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else {
            Intent i = new Intent(MyOrderActivity.this, HomeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        String[] name = {getString(R.string.orderstats), getString(R.string.orderdetail)};

        public ViewPagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {

                case 0:
                    return new OrderStatus();
                case 1:
                    return new OrderDetail();
            }
            return null;

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
