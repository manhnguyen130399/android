package freaktemplate.shopping.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nshmura.snappysmoothscroller.SnappyLinearLayoutManager;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import freaktemplate.shopping.R;
import freaktemplate.shopping.activity.MyOrderActivity;
import freaktemplate.shopping.activity.PaymentActivity;
import freaktemplate.shopping.adapter.PaymentMethodAdapter;
import freaktemplate.shopping.getset.Bankgetset;
import freaktemplate.shopping.getset.PlaceOrder.OrderPojo;
import freaktemplate.shopping.interfaces.RadioListner;
import freaktemplate.shopping.utils.SPmanager;
import freaktemplate.shopping.utils.SqliteHelper;
import freaktemplate.shopping.utils.UtilHelper;

import static android.app.Activity.RESULT_OK;
import static freaktemplate.shopping.activity.HomeActivity.txt_cartitem;
import static freaktemplate.shopping.activity.PaymentActivity.viewPager;

public class PaymentMethodFragment extends Fragment implements TextWatcher, RadioListner {
    private static final String TAG = "PaymentMethodFragment";
    private static final int PAYPAL_REQUEST_CODE = 999;
    private RecyclerView recycle_method;
    private ArrayList<Bankgetset> bankList;
    private View view;
    private String address;
    private String pincode;
    private String town;
    private String country_name;
    private PaymentActivity paymentActivity;
    private String jsondata;
    private String total;
    private String f_name;
    private String notes;
    private String email;
    private String userid;
    private String phone;
    private Gson gson;
    private String payment_method;
    private Context context;
    private EditText edt_cvv;
    private EditText edt_year;
    private EditText edt_month;
    private EditText edt_card;
    private Card card;
    private String tokenid;
    private PayPalConfiguration payPalConfiguration;
    private String shippingaddress;
    private String shippingpin;
    private String shippingtown;


    public PaymentMethodFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroy() {
        getActivity().stopService(new Intent(getActivity(), PayPalService.class));
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_payment_method, container, false);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        context = view.getContext();
        bankList = new ArrayList<>();
        payPalConfiguration = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK).clientId(getString(R.string.paypalclient_id));
        Intent mservices = new Intent(context, PayPalService.class);
        mservices.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration);
        context.startService(mservices);
        paymentActivity = (PaymentActivity) getActivity();
        assert paymentActivity != null;
        jsondata = paymentActivity.getJsondata();
        total = paymentActivity.getTotal();
        userid = SPmanager.getPreference(view.getContext(), "userID");


        init();
        setList();
        return view;
    }

    private void setList() {

        Bankgetset bankgetset = new Bankgetset();
        bankgetset.setPayment_name(getString(R.string.paypal_transfer));
        bankgetset.setType("1");
        bankgetset.setImage(getURLForResource(R.drawable.paypal));
        bankgetset.setDescription(getString(R.string.pay_onpaypal));
        bankList.add(bankgetset);

        Bankgetset bankgetset3 = new Bankgetset();
        bankgetset3.setPayment_name(getString(R.string.visa_card));
        bankgetset3.setType("2");
        bankgetset3.setImage(getURLForResource(R.drawable.visa_card));
        bankgetset3.setDescription(getString(R.string.payvisa));
        bankList.add(bankgetset3);

        Bankgetset bankgetset4 = new Bankgetset();
        bankgetset4.setPayment_name(getString(R.string.master_card));
        bankgetset4.setType("2");
        bankgetset4.setImage(getURLForResource(R.drawable.maestro));
        bankgetset4.setDescription(getString(R.string.paymaster));
        bankList.add(bankgetset4);

        Bankgetset bankgetset5 = new Bankgetset();
        bankgetset5.setPayment_name(getString(R.string.cashon));
        bankgetset5.setType("3");
        bankgetset5.setImage(getURLForResource(R.drawable.cod));
        bankgetset5.setDescription(getString(R.string.pay_cash));
        bankList.add(bankgetset5);

//        payment_method = bankList.get(0).getType();
        final SnappyLinearLayoutManager snappyLinearLayoutManager = new SnappyLinearLayoutManager(view.getContext(), SnappyLinearLayoutManager.VERTICAL, false);
        recycle_method.setLayoutManager(snappyLinearLayoutManager);
        PaymentMethodAdapter paymentMethodAdapter = new PaymentMethodAdapter(view.getContext(), bankList);
        recycle_method.setAdapter(paymentMethodAdapter);
        paymentMethodAdapter.setRadioListener(this);
    }

    @Override
    public void RadioClick(int position, int mSelctedposition) {
        if (mSelctedposition != -1) {
            payment_method = bankList.get(position).getType();
        }else {
            payment_method = "";
        }
    }

    private void init() {
        recycle_method = view.findViewById(R.id.recycle_method);
        TextView txt_address = view.findViewById(R.id.txt_address);
        TextView txt_town = view.findViewById(R.id.txt_town);
        TextView txt_pin = view.findViewById(R.id.txt_pin);
        TextView txt_country = view.findViewById(R.id.txt_country);
        TextView txt_edit = view.findViewById(R.id.txt_edit);
        try {
            Map<String, String> data = EventBus.getDefault().getStickyEvent(HashMap.class);
            address = data.get("address");
            pincode = data.get("pin");
            town = data.get("town");
            country_name = data.get("country");
            f_name = data.get("f_name");
            notes = data.get("notes");
            email = data.get("email");
            phone = data.get("order_mobile");
            shippingaddress = data.get("shippingaddress");
            shippingpin = data.get("shippingpin");
            shippingtown = data.get("shippingtown");
        } catch (Exception e) {
            e.printStackTrace();
        }
        txt_address.setText(this.address);
        txt_town.setText(this.town);
        txt_pin.setText(this.pincode);
        txt_country.setText(this.country_name);
        txt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });
        PaymentActivity.txt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    private void validate() {
        if (userid != null && f_name != null) {
            if (address != null && town != null) {
                if (pincode != null && phone != null && email != null && total != null) {
                    if (jsondata != null && notes != null) {
                        if (payment_method.equals("1")) {
                            getPaymentFrompaypal();
                        } else if (payment_method.equals("2")) {
                            openstripedialog();

                        } else if (payment_method.equals("3")) {
                            senddata(tokenid);
                        }else {
                            Toast.makeText(paymentActivity, "Select Payment Type", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    }

    private void getPaymentFrompaypal() {
        PayPalPayment payment = new PayPalPayment(new BigDecimal(total), "USD", getString(R.string.shop_payment), PayPalPayment.PAYMENT_INTENT_AUTHORIZE);
        Intent intent = new Intent(context, com.paypal.android.sdk.payments.PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration);
        intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        JSONObject jsonDetails = new JSONObject(paymentDetails);
                        JSONObject response = jsonDetails.optJSONObject("response");
                        String paymentId = response.getString("id");
                        senddata(paymentId);
                        Log.i("paymentExample", paymentId);
                    } catch (JSONException e) {
                        Log.e("paymentExample", getString(R.string.pay_failure) + e.getMessage());
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show();
        } else if (resultCode == com.paypal.android.sdk.payments.PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(context, "Invalid", Toast.LENGTH_SHORT).show();
    }

    private void openstripedialog() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.stripe_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        edt_card = dialog.findViewById(R.id.edt_card);
        edt_month = dialog.findViewById(R.id.edt_month);
        edt_year = dialog.findViewById(R.id.edt_year);
        edt_cvv = dialog.findViewById(R.id.edt_cvv);
        TextView txt_cancel = dialog.findViewById(R.id.txt_cancel);
        TextView txt_payment = dialog.findViewById(R.id.txt_payment);

        /*edt_card.addTextChangedListener(this);
        edt_month.addTextChangedListener(this);
        edt_year.addTextChangedListener(this);
        edt_cvv.addTextChangedListener(this);*/

        txt_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String card_number = edt_card.getText().toString().trim();
                final String card_month = edt_month.getText().toString().trim();
                final String card_year = edt_year.getText().toString().trim();
                final String card_cvv = edt_cvv.getText().toString().trim();
                validation(card_number, card_month, card_year, card_cvv);

            }
        });
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void validation(String card_number, String card_month, String card_year, String card_cvv) {

        if (card_number.length() == 16) {

            if (card_month.length() == 2 && Integer.parseInt(card_month) <= 12) {
                if (card_year.length() == 4) {
                    if (card_cvv.length() == 3) {
                        card = new Card(
                                card_number, //card number
                                Integer.valueOf(card_month), //expMonth
                                Integer.valueOf(card_year),//expYear
                                card_cvv//cvc
                        );

                        buy();

                    } else {
                        edt_cvv.setError(getString(R.string.valid_cvv));
                    }
                } else {
                    edt_year.setError(getString(R.string.valid_year));
                }

            } else {
                edt_month.setError(getString(R.string.valid_month));
            }


        } else {
            edt_card.setError(getString(R.string.valid_card));
        }

    }

    private void buy() {

        UtilHelper.showdialog(context);

        boolean validation = card.validateCard();
        if (validation) {
            new Stripe(context).createToken(
                    card,
                    getString(R.string.stripe_key),
                    new TokenCallback() {
                        @Override
                        public void onError(Exception error) {
                            Log.e(TAG, "onError: " + error.getMessage());
                            UtilHelper.hidedialog();
                        }

                        @Override
                        public void onSuccess(Token token) {
                            Log.e(TAG, "onSuccess: " + token);
                            UtilHelper.hidedialog();
                            Log.e(TAG, "onSuccess: " + token.getCard());
                            senddata(token.getId());

                        }
                    });
        } else if (!card.validateNumber()) {
            UtilHelper.hidedialog();
            Toast.makeText(context, getString(R.string.valid_card), Toast.LENGTH_LONG).show();
        } else if (!card.validateExpiryDate()) {
            UtilHelper.hidedialog();
            Toast.makeText(context, R.string.expirydate, Toast.LENGTH_LONG).show();
        } else if (!card.validateCVC()) {
            UtilHelper.hidedialog();
            Toast.makeText(context, R.string.cvc_error, Toast.LENGTH_LONG).show();
        } else {
            UtilHelper.hidedialog();
            Toast.makeText(context, R.string.invalidcard, Toast.LENGTH_LONG).show();
        }
    }

    private void senddata(String stripe_token) {
        Dialog dialog_main = new Dialog(context);
        dialog_main.setContentView(R.layout.progress);
        dialog_main.setCancelable(false);
        dialog_main.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_main.show();
        String url = getString(R.string.link) + "placeorder";
        Log.e(TAG, "senddata: " + url);
        ANRequest.PostRequestBuilder postRequestBuilder = new ANRequest.PostRequestBuilder(url);
        postRequestBuilder.addBodyParameter("user_id", userid);
        postRequestBuilder.addBodyParameter("payment_method", payment_method);
        postRequestBuilder.addBodyParameter("shipping_method", "1");
        postRequestBuilder.addBodyParameter("order_name", f_name);
        postRequestBuilder.addBodyParameter("order_billing_address", address);
        postRequestBuilder.addBodyParameter("order_billing_city", town);
        postRequestBuilder.addBodyParameter("order_billing_pincode", pincode);
        postRequestBuilder.addBodyParameter("order_phone", phone);
        postRequestBuilder.addBodyParameter("order_email", email);
        postRequestBuilder.addBodyParameter("shipping_charges", SPmanager.getPreference(context, "delCharge"));
        postRequestBuilder.addBodyParameter("freeshipping", "0");
        postRequestBuilder.addBodyParameter("total_taxes", "0.00");
        postRequestBuilder.addBodyParameter("total_order_price", total);
        postRequestBuilder.addBodyParameter("orderjson", jsondata);
        postRequestBuilder.addBodyParameter("subtotal", SPmanager.getPreference(context, "subtotal"));
        int toShip;
        if (SPmanager.getBilling(view.getContext())) {
            toShip = 0;
        } else {
            toShip = 1;
        }
        postRequestBuilder.addBodyParameter("to_ship", String.valueOf(toShip));
        postRequestBuilder.addBodyParameter("order_notes", notes);
        if (toShip == 1) {
            postRequestBuilder.addBodyParameter("order_shipping_city", shippingtown);
            postRequestBuilder.addBodyParameter("order_shipping_pincode", shippingpin);
            postRequestBuilder.addBodyParameter("order_shipping_address", shippingaddress);
            postRequestBuilder.addBodyParameter("order_ship_name", f_name);
        }
        if (payment_method.equals("2")) {
            postRequestBuilder.addBodyParameter("stripeToken", stripe_token);
        }
        if (payment_method.equals("1")) {
            postRequestBuilder.addBodyParameter("pay_pal_paymentId", stripe_token);
        }
        postRequestBuilder.setExecutor(Executors.newSingleThreadExecutor());
        ANRequest anRequest = postRequestBuilder.build();
        anRequest.getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "onResponse: " + response);
                try {
                    OrderPojo orderPojo = gson.fromJson(String.valueOf(response), OrderPojo.class);
                    if (orderPojo.getData().getStatus().equals(1)) {
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(view.getContext(), orderPojo.getData().getMsg(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(view.getContext(), MyOrderActivity.class);
                                intent.putExtra("order_id", String.valueOf(orderPojo.getData().getData()));
                                intent.putExtra("key", "order_place");
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                removeData();
                                removeaddress();
                                dialog_main.dismiss();
                            }
                        });


                    } else {
                        dialog_main.dismiss();
//                        Toast.makeText(view.getContext(), getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    dialog_main.dismiss();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(paymentActivity, R.string.somethingwroing, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

            @Override
            public void onError(ANError anError) {
                Log.e(TAG, "onError: " + anError.getErrorBody());
            }
        });
/*
        anRequest.getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG, "onResponse: " + response);
                try {
                    OrderPojo orderPojo = gson.fromJson(String.valueOf(response), OrderPojo.class);
                    if (orderPojo.getData().getStatus().equals(1)) {
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(view.getContext(), orderPojo.getData().getMsg(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(view.getContext(), MyOrderActivity.class);
                                intent.putExtra("order_id", String.valueOf(orderPojo.getData().getData()));
                                intent.putExtra("key", "order_place");
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                removeData();
                                removeaddress();
                                dialog_main.dismiss();
                            }
                        });


                    } else {
                        dialog_main.dismiss();
//                        Toast.makeText(view.getContext(), getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    dialog_main.dismiss();
                   */
/* PaymentIntent intent = PaymentIntent.retrieve("pi_rfv0yXom9wX6b0UnBE3L");
                    intent.cancel()*//*

                }
            }

            @Override
            public void onError(ANError anError) {
                dialog_main.dismiss();
                Toast.makeText(view.getContext(), getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onError: " + anError.getMessage());
                Log.e(TAG, "onError: " + anError.getErrorBody());
            }
        });
*/
    }

    private void removeaddress() {
        SPmanager.saveValue(view.getContext(), "address", null);
        SPmanager.saveValue(view.getContext(), "city", null);
        SPmanager.saveValue(view.getContext(), "pincode", null);
        SPmanager.saveValue(view.getContext(), "billaddress", null);
        SPmanager.saveValue(view.getContext(), "billcity", null);
        SPmanager.saveValue(view.getContext(), "billpincode", null);
        SPmanager.saveValue(context, "edtcity", null);
        SPmanager.saveValue(context, "edtpincode", null);
        SPmanager.saveValue(context, "edtbillcity", null);
        SPmanager.saveValue(context, "edtbillpincode", null);
        SPmanager.saveValue(context, "country", null);
    }

    private void removeData() {
        SPmanager.saveValue(context, "delCharge", null);
        SqliteHelper sqliteHelper = new SqliteHelper(view.getContext());
        SQLiteDatabase db1 = sqliteHelper.getWritableDatabase();
        db1.execSQL("DELETE FROM cart");
        db1.close();
        SQLiteDatabase sqLiteDatabases = sqliteHelper.getWritableDatabase();
        long numCart = DatabaseUtils.queryNumEntries(sqLiteDatabases, "cart");
        txt_cartitem.setText(String.valueOf(numCart));
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


    private String getURLForResource(int resourceId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resourceId).toString();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (edt_card.getText().toString().length() == 16) {
            edt_month.requestFocus();
        }
        if (edt_month.getText().toString().length() == 2) {
            edt_year.requestFocus();
        }
        if (edt_year.getText().toString().length() == 4) {
            edt_cvv.requestFocus();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


}
