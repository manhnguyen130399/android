package freaktemplate.shopping.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import freaktemplate.shopping.R;
import freaktemplate.shopping.adapter.PaymentPagerAdapter;
import freaktemplate.shopping.utils.CustomViewPagers;
import freaktemplate.shopping.utils.SPmanager;

@SuppressLint("StaticFieldLeak")
public class PaymentActivity extends AppCompatActivity implements CustomViewPagers.OnPageChangeListener {
    public static CustomViewPagers viewPager;
    public static TextView txt_back;
    public static TextView txt_next;
    private RelativeLayout rel_payment;
    private ImageView ic_payment;
    private String address;
    private String jsondata;
    private String total;
    private String original;
    private PaymentActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        context = this;
        getintents();
        init();
    }

    private void getintents() {
        Intent intent = getIntent();
        jsondata = intent.getStringExtra("json");
        total = intent.getStringExtra("total");
        original = intent.getStringExtra("original");
    }

    public String getJsondata() {
        return jsondata;
    }

    public String getTotal() {
        return total;
    }


    public String getAddress() {
        return address;
    }

    private void init() {
        viewPager = findViewById(R.id.viewpager);
        txt_next = findViewById(R.id.txt_next);
        txt_back = findViewById(R.id.txt_back);
        rel_payment = findViewById(R.id.rel_payment);
        ic_payment = findViewById(R.id.ic_payment);
        viewPager.setPagingEnabled(false);
        viewPager.setOffscreenPageLimit(0);
        setupViewPager(viewPager);
        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SPmanager.saveValue(context, "address", null);
        SPmanager.saveValue(context, "city", null);
        SPmanager.saveValue(context, "pincode", null);
        SPmanager.saveValue(context, "billaddress", null);
        SPmanager.saveValue(context, "billcity", null);
        SPmanager.saveValue(context, "billpincode", null);
        SPmanager.saveValue(context, "country", null);
        SPmanager.saveValue(context, "edtcity", null);
        SPmanager.saveValue(context, "edtpincode", null);
        SPmanager.saveValue(context, "edtbillcity", null);
        SPmanager.saveValue(context, "edtbillpincode", null);
        SPmanager.saveValue(context, "note", null);
    }

    private void setupViewPager(CustomViewPagers viewPager) {
        PaymentPagerAdapter adapter = new PaymentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(0);
        viewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 1) {
            rel_payment.setBackground(getResources().getDrawable(R.drawable.address_background));
            ic_payment.setBackground(getResources().getDrawable(R.drawable.card_white));
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}