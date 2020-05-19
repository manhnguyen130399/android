package freaktemplate.shopping.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import freaktemplate.shopping.MyApplication;
import freaktemplate.shopping.R;
import freaktemplate.shopping.getset.OrderDetail.OrderStatusDetails;

public class OrderStatus extends Fragment {

    private static final String TAG = "OrderStatus";
    private TextView txt_pickuptime;
    private TextView txt_ordertime;
    private TextView txt_paymenttime;
    private TextView txt_pickup;
    private TextView txt_placetime;
    private View rootView;
    private MyApplication myApp;
    private OrderStatusDetails orderStatusDetails;
    private String orderstatus;
    private RelativeLayout rel_deliveryboy;
    private ImageView img_place;
    private ImageView img_paymentconfirmed;
    private ImageView img_orderproceed;
    private ImageView img_pickup;
    private TextView txtDeldes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_order_status, container, false);
        myApp = MyApplication.getInstance();
        orderStatusDetails = myApp.getOrderStatusDetails();
        orderstatus = myApp.getOrderstatus();
        inite();
        return rootView;
    }

    private void inite() {
        TextView txt_date = rootView.findViewById(R.id.txt_date);
        txt_pickuptime = rootView.findViewById(R.id.txt_pickuptime);
        txtDeldes = rootView.findViewById(R.id.txtDeldes);
        txt_ordertime = rootView.findViewById(R.id.txt_ordertime);
        txt_paymenttime = rootView.findViewById(R.id.txt_paymenttime);
        txt_placetime = rootView.findViewById(R.id.txt_placetime);
        txt_pickup = rootView.findViewById(R.id.txt_pickup);
        img_place = rootView.findViewById(R.id.img_place);
        img_paymentconfirmed = rootView.findViewById(R.id.img_paymentconfirmed);
        img_orderproceed = rootView.findViewById(R.id.img_orderproceed);
        img_pickup = rootView.findViewById(R.id.img_pickup);
        rel_deliveryboy = rootView.findViewById(R.id.rel_deliveryboy);
        String date = myApp.getOrderdate();
        txt_date.setText(getString(R.string.eta) + " " + ": " + date);
        showstatus();
        showsdate();
    }


    private void showstatus() {
        switch (orderstatus) {
            case "1":
                img_place.setImageResource(R.drawable.place);
                img_paymentconfirmed.setImageResource(R.drawable.place);
                img_orderproceed.setImageResource(R.drawable.placed);
                img_pickup.setImageResource(R.drawable.placed);
                rel_deliveryboy.setVisibility(View.GONE);
                break;
            case "2":
                img_place.setImageResource(R.drawable.place);
                img_paymentconfirmed.setImageResource(R.drawable.place);
                img_orderproceed.setImageResource(R.drawable.place);
                img_pickup.setImageResource(R.drawable.placed);
                rel_deliveryboy.setVisibility(View.VISIBLE);
                break;
            case "3":
                img_place.setImageResource(R.drawable.place);
                img_paymentconfirmed.setImageResource(R.drawable.placed);
                img_orderproceed.setImageResource(R.drawable.placed);
                img_pickup.setImageResource(R.drawable.placed);
                rel_deliveryboy.setVisibility(View.GONE);
                break;
            case "5":
                img_place.setImageResource(R.drawable.place);
                img_paymentconfirmed.setImageResource(R.drawable.place);
                img_orderproceed.setImageResource(R.drawable.place);
                img_pickup.setImageResource(R.drawable.place);
                rel_deliveryboy.setVisibility(View.VISIBLE);
                break;
            case "6":
                img_place.setImageResource(R.drawable.place);
                img_paymentconfirmed.setImageResource(R.drawable.placed);
                img_orderproceed.setImageResource(R.drawable.placed);
                img_pickup.setImageResource(R.drawable.place);
                rel_deliveryboy.setVisibility(View.VISIBLE);
                txt_pickup.setText(R.string.order_cancelled);
                txt_pickup.setText(R.string.ordercan);
                break;
            case "4":
                img_place.setImageResource(R.drawable.place);
                img_paymentconfirmed.setImageResource(R.drawable.place);
                img_orderproceed.setImageResource(R.drawable.place);
                img_pickup.setImageResource(R.drawable.placed);
                rel_deliveryboy.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void showsdate() {
        String placedate = orderStatusDetails.getOrderPlaced();
        String holddate = orderStatusDetails.getOnhold();
        String pendding = orderStatusDetails.getProcessing();
        String completed = orderStatusDetails.getCompletedDatetime();

        if (placedate != null) {
            ShowDate(placedate);

        }
        if (holddate != null) {
            ShowDate1(holddate);

        }
        if (pendding != null) {
            ShowDate2(pendding);
        }
        if (completed != null) {
            ShowDate3(completed);
        }
    }

    private void ShowDate3(String completed) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        try {
            Date newdate = dateFormat.parse(completed);
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd, MMM yyyy h:mm a", Locale.ENGLISH);
            assert newdate != null;
            String date1 = dateFormat1.format(newdate);
            txt_pickuptime.setText(date1);
        } catch (ParseException e) {
            Log.e(TAG, "setDate: " + e.getMessage());
        }
    }

    private void ShowDate2(String pendding) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        try {
            Date newdate = dateFormat.parse(pendding);
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd, MMM yyyy h:mm a", Locale.ENGLISH);
            assert newdate != null;
            String date1 = dateFormat1.format(newdate);
            txt_paymenttime.setText(date1);
        } catch (ParseException e) {
            Log.e(TAG, "setDate: " + e.getMessage());
        }
    }

    private void ShowDate1(String holddate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        try {
            Date newdate = dateFormat.parse(holddate);
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd, MMM yyyy h:mm a", Locale.ENGLISH);
            assert newdate != null;
            String date1 = dateFormat1.format(newdate);
            txt_ordertime.setText(date1);
        } catch (ParseException e) {
            Log.e(TAG, "setDate: " + e.getMessage());
        }
    }

    private void ShowDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        try {
            Date newdate = dateFormat.parse(date);
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd, MMM yyyy h:mm a", Locale.ENGLISH);
            assert newdate != null;
            String date1 = dateFormat1.format(newdate);
            txt_placetime.setText(date1);
        } catch (ParseException e) {
            Log.e(TAG, "setDate: " + e.getMessage());
        }
    }
}
