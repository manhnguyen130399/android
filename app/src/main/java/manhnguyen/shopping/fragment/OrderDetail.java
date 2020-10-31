package manhnguyen.shopping.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import manhnguyen.shopping.MyApplication;
import manhnguyen.shopping.R;
import manhnguyen.shopping.adapter.OrderCartAdapter;
import manhnguyen.shopping.getset.OrderDetail.OrderDetails;
import manhnguyen.shopping.getset.OrderDetail.Product;


public class OrderDetail extends Fragment {

    private RecyclerView cart_list;
    private OrderDetails orderDetails;
    private List<Product> productlis;
    private View rootview;

    public OrderDetail() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_order_detail, container, false);
        MyApplication myApp = MyApplication.getInstance();
        orderDetails = myApp.getOrderDetails();
        inite();
        getList();
        return rootview;
    }

    private void inite() {
        productlis = new ArrayList<>();
        cart_list = rootview.findViewById(R.id.cart_list);
        TextView txt_subtotal = rootview.findViewById(R.id.txt_subtotal);
        TextView text_total = rootview.findViewById(R.id.text_total);
        TextView txt_billname = rootview.findViewById(R.id.txt_billname);
        TextView text_city = rootview.findViewById(R.id.text_city);
        TextView txt_pincode = rootview.findViewById(R.id.txt_pincode);
        TextView text_billingaddress = rootview.findViewById(R.id.text_billingaddress);
        TextView txt_delcharge = rootview.findViewById(R.id.txt_delcharge);

        String subtotal = orderDetails.getSubtotal();
        String total = orderDetails.getTotal();
        String del_charge = orderDetails.getShippingCharge();
        String bilingaddress = orderDetails.getBillingAddress();
        String billingname = orderDetails.getBillingName();
        String billingcity = orderDetails.getBillingCity();
        String billingpincode = orderDetails.getBillingPincode();

        txt_subtotal.setText(getString(R.string.dolar) + subtotal);
        txt_delcharge.setText(getString(R.string.dolar) + del_charge);
        text_total.setText(getString(R.string.dolar) + total);
        txt_billname.setText(billingname);
        text_city.setText(billingcity);
        text_billingaddress.setText(bilingaddress);
        txt_pincode.setText(billingpincode);
    }

    private void getList() {
        productlis = orderDetails.getProducts();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        cart_list.setLayoutManager(layoutManager);
        OrderCartAdapter adapter = new OrderCartAdapter(getContext(), productlis);
        cart_list.setAdapter(adapter);
    }
}