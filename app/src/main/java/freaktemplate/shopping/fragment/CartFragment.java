package freaktemplate.shopping.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import freaktemplate.shopping.R;
import freaktemplate.shopping.activity.HomeActivity;
import freaktemplate.shopping.activity.PaymentActivity;
import freaktemplate.shopping.adapter.CartAdapter;
import freaktemplate.shopping.getset.CartGet.CartGet;
import freaktemplate.shopping.getset.CartGet.Cartdatum;
import freaktemplate.shopping.getset.CartGet.Shipping;
import freaktemplate.shopping.getset.Discount.CoupanPojo;
import freaktemplate.shopping.getset.Removcart.Removcart;
import freaktemplate.shopping.interfaces.PlusMinusButtonListener;
import freaktemplate.shopping.utils.SPmanager;
import freaktemplate.shopping.utils.UtilHelper;

public class CartFragment extends Fragment implements View.OnClickListener, PlusMinusButtonListener {

    private static final String TAG = "CartFragment";
    private RecyclerView recycle_cart;
    private View rootview;
    private Context context;
    private TextView txt_subtotal;
    private TextView txt_total;
    private TextView txt_delcharge;
    private TextView txt_items;
    private TextView txt_discount;
    private TextView txt_discountprice;
    private double sumOfDolar;
    private String delivery_charge;
    private double total_price;
    private JSONObject product;
    private EditText edt_code;
    private String itemId;
    private ArrayList<String> idList;
    private Gson gson;
    private String discount;
    private String freeshipping;
    private String userID;
    private List<Cartdatum> cartlist;
    private List<Shipping> shipping;
    private double total_charge;
    private CartAdapter adapter;
    private double finaltotal;

    private static double round(double Rval, int numberOfDigitsAfterDecimal) {
        double p = (float) Math.pow(10, numberOfDigitsAfterDecimal);
        Rval = Rval * p;
        double tmp = Math.floor(Rval);
        System.out.println("~~~~~~tmp~~~~~" + tmp);
        return tmp / p;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.activity_cart, container, false);
        context = rootview.getContext();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        userID = SPmanager.getPreference(context, "userID");
        cartlist = new ArrayList<>();
        shipping = new ArrayList<>();
        setview();
        if (userID != null) {
            if (userID.equals("null")) {
                userID = "0";
            }
        } else {
            userID = "0";
        }
        inite();
        return rootview;
    }

    private void setview() {
        HomeActivity.rel_cart.setBackgroundResource(R.drawable.homerect);
        HomeActivity.rel_favorite.setBackgroundResource(0);
        HomeActivity.rel_user.setBackgroundResource(0);
        HomeActivity.rel_home.setBackgroundResource(0);
        HomeActivity.txt_home.setVisibility(View.GONE);
        HomeActivity.txt_user.setVisibility(View.GONE);
        HomeActivity.txt_cart.setVisibility(View.VISIBLE);
        HomeActivity.txt_favrite.setVisibility(View.GONE);

        HomeActivity.ic_favrite.setBackgroundResource(R.drawable.like_not);
        HomeActivity.ic_cart.setBackgroundResource(R.drawable.cart);
        HomeActivity.ic_user.setBackgroundResource(R.drawable.user_not);
        HomeActivity.ic_home.setBackgroundResource(R.drawable.home_not);
    }

    private void inite() {
        cartlist = new ArrayList<>();
        recycle_cart = rootview.findViewById(R.id.recycle_cart);
        TextView proceed_checkout = rootview.findViewById(R.id.proceed_checkout);
        ImageView ic_back = rootview.findViewById(R.id.ic_back);
        txt_subtotal = rootview.findViewById(R.id.txt_subtotal);
        txt_items = rootview.findViewById(R.id.txt_items);
        txt_delcharge = rootview.findViewById(R.id.txt_delcharge);
        txt_discountprice = rootview.findViewById(R.id.txt_discountprice);
        txt_discount = rootview.findViewById(R.id.txt_discount);
        txt_total = rootview.findViewById(R.id.txt_total);
        TextView txt_apply = rootview.findViewById(R.id.txt_apply);
        edt_code = rootview.findViewById(R.id.edt_code);
        delivery_charge = txt_delcharge.getText().toString().trim().replace(getString(R.string.dolar), "").replace(" ", "");
        idList = new ArrayList<>();
        ic_back.setOnClickListener(this);
        getList();
        proceed_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subtotal = txt_subtotal.getText().toString().replace(context.getString(R.string.dolar), "").replace(" ", "");
                if (!subtotal.equals("0.0")) {
                    if (product != null) {
                        Intent intent = new Intent(rootview.getContext(), PaymentActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("json", String.valueOf(product));
                        if (freeshipping != null) {
                            intent.putExtra("freeshipping", freeshipping);
                        } else {
                            intent.putExtra("freeshipping", "0");
                        }
                        SPmanager.saveValue(context, "subtotal", String.valueOf(total_price));
                        intent.putExtra("total", /*txt_total.getText().toString()*/String.valueOf(finaltotal));
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, R.string.cart_empty, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, R.string.cart_empty, Toast.LENGTH_SHORT).show();
                }


            }
        });
        txt_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coupan = edt_code.getText().toString();
                String subtotal = String.valueOf(total_charge);

                if (!coupan.isEmpty()) {
                    if (!subtotal.isEmpty()) {
                        if (userID != null) {
                            if (idList != null) {
                                applycode(coupan, subtotal, userID, idList);
                            } else {
                                Toast.makeText(context, R.string.cart_empty, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, getString(R.string.login_warning), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, R.string.cart_empty, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    edt_code.setError(getString(R.string.coupan_error));
                }
            }
        });
    }

    private void applycode(String coupan, String subtotal, String userid, ArrayList<String> productid) {
        UtilHelper.showdialog(rootview.getContext());
        String url = getString(R.string.link) + "verifiedcoupon?coupon_code=" + coupan + "&user_id=" + userid + "&total=" + subtotal + "&product=" + TextUtils.join(",", productid);
        Log.e(TAG, "applycode: " + url);

        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse: " + response);
                        String msg = getString(R.string.coupan_error);

                        try {
                            CoupanPojo coupanPojo = gson.fromJson(String.valueOf(response), CoupanPojo.class);
                            if (coupanPojo.getData().getStatus().equals(1)) {
                                discount = coupanPojo.getData().getDiscount().getDiscountPrice();
                                freeshipping = coupanPojo.getData().getDiscount().getFreeshipping();
                                txt_discount.setVisibility(View.VISIBLE);
                                txt_discountprice.setVisibility(View.VISIBLE);
                                txt_discountprice.setText(context.getString(R.string.dolar) + discount);
                                totalSum(discount, 0);
                                UtilHelper.hidedialog();
                            } else if (coupanPojo.getData().getStatus().equals(0)) {
                                UtilHelper.hidedialog();
                                txt_discount.setVisibility(View.GONE);
                                txt_discountprice.setVisibility(View.GONE);
                                try {
                                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    msg = data.getString("discount");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            UtilHelper.hidedialog();
                            e.printStackTrace();
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(String.valueOf(response));
                                JSONObject data = jsonObject.getJSONObject("data");
                                msg = data.getString("discount");
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }

                            Log.e(TAG, "onResponse: " + e.getMessage());
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        }

                    }


                    @Override
                    public void onError(ANError anError) {
                        UtilHelper.hidedialog();
                        Log.e(TAG, "onError: " + anError.getErrorBody());
                        Log.e(TAG, "onError: " + anError.getMessage());
                        Toast.makeText(context, getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void getList() {
        cartlist.clear();
        String url = getString(R.string.link) + "getcart/" + userID;
        Log.e(TAG, "getList: " + url);
        UtilHelper.showdialog(rootview.getContext());
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse: " + response);
                        try {
                            CartGet cartGet = gson.fromJson(String.valueOf(response), CartGet.class);
                            if (cartGet.getData().getStatus().equals(1)) {
                                cartlist = cartGet.getData().getData().getCartdata();
                                for (int i = 0; i < cartlist.size(); i++) {
                                    itemId = String.valueOf(cartlist.get(i).getProductId());
                                    idList.add(itemId.replaceAll("\\s+", ""));
                                }
                                UtilHelper.hidedialog();
                                shipping = cartGet.getData().getData().getShipping();
                                delivery_charge = shipping.get(0).getCost();
                                txt_delcharge.setText(context.getString(R.string.dolar) + Double.parseDouble(delivery_charge));
                                SPmanager.saveValue(context, "delCharge", String.valueOf(Double.parseDouble(delivery_charge)));
                                txt_items.setText(cartlist.size() + " " + getString(R.string.items));
                                setCart();
                                createJson();

                            } else {
                                UtilHelper.hidedialog();
                            }
                        } catch (Exception e) {
                            UtilHelper.hidedialog();
                            Toast.makeText(context, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(context, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                        UtilHelper.hidedialog();
                    }
                });

    }

    private void setCart() {

        for (int i = 0; i < cartlist.size(); i++) {
            sumOfDolar += (Double.parseDouble(cartlist.get(i).getPrice()) * Double.parseDouble(cartlist.get(i).getQty()));
            total_price = round(sumOfDolar, 2);
            txt_subtotal.setText(context.getString(R.string.dolar) + total_price);
            total_charge = total_price + Double.parseDouble(delivery_charge);
            finaltotal = total_price + Double.parseDouble(delivery_charge);
            txt_total.setText(getString(R.string.dolar) + String.valueOf(finaltotal));
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(rootview.getContext());
        recycle_cart.setLayoutManager(layoutManager);
        adapter = new CartAdapter(rootview.getContext(), cartlist);
        recycle_cart.setAdapter(adapter);
        adapter.PlusMinusButtonListener(this);
    }

    private void createJson() {

        product = new JSONObject();

        JSONArray order = new JSONArray();

        for (int i = 0; i < cartlist.size(); i++) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("ProductId", cartlist.get(i).getProductId());
                obj.put("ProductAmt", cartlist.get(i).getPrice());
                obj.put("tax_name", " ");
                obj.put("tax_amount", " ");
                obj.put("ProductQty", cartlist.get(i).getQty());
                float total = Float.parseFloat(cartlist.get(i).getQty()) * Float.parseFloat(cartlist.get(i).getPrice());
                obj.put("ProductTotal", total);
                JSONObject exterdata = new JSONObject();
                exterdata.put("option", cartlist.get(i).getOption());
                exterdata.put("label", cartlist.get(i).getLabel());
                exterdata.put("price", " ");
                obj.put("exterdata", exterdata);

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "createJson: " + e.getMessage());
            }
            order.put(obj);
        }

        try {
            product.put("order", order);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "createJson: " + e.getMessage());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        rootview.setFocusableInTouchMode(true);
        rootview.requestFocus();
        rootview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    // handle back button
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    getActivity().startActivity(intent);
                    return true;

                }

                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ic_back) {
            Intent i = new Intent(rootview.getContext(), HomeActivity.class);
            startActivity(i);
        }

    }

    @Override
    public void plusClick(int position) {
        edt_code.getText().clear();
        txt_discount.setVisibility(View.GONE);
        txt_discountprice.setVisibility(View.GONE);
        discount = "0.00";
        totalSum(discount, position);
        createJson();


    }

    @Override
    public void minusClick(int position) {
        edt_code.getText().clear();
        txt_discount.setVisibility(View.GONE);
        txt_discountprice.setVisibility(View.GONE);
        discount = "0.00";
        totalSum(discount, position);
        createJson();


    }

    private void totalSum(String discount, int position) {
        float total = 0;

        for (int i = 0; i < cartlist.size(); i++) {
            String discount_price = cartlist.get(i).getPrice();
            String quantity = cartlist.get(i).getQty();

            try {
                float dolar = Float.parseFloat(discount_price);

                float quant1 = Float.parseFloat(quantity);
                float quant = Float.parseFloat(quantity);
                float totalsum = dolar * quant;
                total = totalsum + total;
                total_price = round(total, 2);
            } catch (NumberFormatException e) {
                Log.e("Error", e.getMessage());
            }
        }
        delivery_charge = shipping.get(0).getCost();
        total_charge = total_price + Double.parseDouble(delivery_charge);
        txt_subtotal.setText(context.getString(R.string.dolar) + total_price);
        finaltotal = total_charge - Double.parseDouble(discount);
        txt_total.setText(context.getString(R.string.dolar) + finaltotal);
    }

    @Override
    public void removeClick(int position) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        MaterialButton rele_close, rele_submit;
        rele_submit = dialog.findViewById(R.id.rele_submit);
        rele_close = dialog.findViewById(R.id.rele_close);
        TextView txt_desc = dialog.findViewById(R.id.txt_desc);
        txt_desc.setText(R.string.are_sure_remove);
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
                removeCart(position);
                dialog.dismiss();
            }
        });
    }

    private void removeCart(int position) {
        UtilHelper.showdialog(context);
        itemId = String.valueOf(cartlist.get(position).getProductId());
        String url = getString(R.string.link) + "removecart/" + cartlist.get(position).getCart_id();
        Log.e(TAG, "removeCart: " + url);

        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG, "onResponse:cart " + response);
                UtilHelper.hidedialog();
                try {
                    Removcart removcart = gson.fromJson(String.valueOf(response), Removcart.class);
                    if (removcart.getData().getStatus().equals(1)) {
                        HomeActivity.txt_cartitem.setText(String.valueOf(removcart.getData().getData()));
                        txt_items.setText(removcart.getData().getData() + " " + getString(R.string.items));
                        idList.remove(itemId.replaceAll("\\s+", ""));
                        Toast.makeText(context, removcart.getData().getMsg(), Toast.LENGTH_SHORT).show();
                        cartlist.remove(position);
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(position, cartlist.size());
                    } else {
                        Toast.makeText(context, removcart.getData().getMsg(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    UtilHelper.hidedialog();
                }
            }

            @Override
            public void onError(ANError anError) {
                Log.e(TAG, "onError:cart " + anError.getErrorBody());
                UtilHelper.hidedialog();
            }
        });
    }
}