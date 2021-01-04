package manhnguyen.shopping.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.suke.widget.SwitchButton;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import manhnguyen.shopping.R;
import manhnguyen.shopping.activity.LocationActivity;
import manhnguyen.shopping.activity.PaymentActivity;
import manhnguyen.shopping.adapter.CustomSpinnerAdapter;
import manhnguyen.shopping.getset.CategoryGetSet;
import manhnguyen.shopping.utils.SPmanager;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class AddressFragment extends Fragment {


    private View view;
    private Spinner spinner_country;
    private ArrayList<CategoryGetSet> countryList;
    private String[] country = new String[]{"India", "England", "America", "Australia", "Portugal", "Spain"};
    private PaymentActivity paymentActivity;
    private EditText edt_firstname;
    private EditText edt_mobile;
    private EditText edt_address;
    private EditText edt_town;
    private EditText edt_pincode;
    private EditText edt_email;
    private EditText edt_billaddress;
    private EditText edt_billpincode;
    private EditText edt_billtown;
    private EditText edt_note;
    private EditText edt_country;
    private RelativeLayout rel_billaddress;
    private String country_name;
    private SwitchButton switch_button;
    private LinearLayout lay_billtown;
    private Context context;
    private boolean ispress = false;
    private String email, name;
    private String jsondata;
    private String total;
    private String phone;
    private String edtcity;
    private String edtpincode;
    private String edtbillcity;
    private String edtbillpincode;
    private String note;

    public AddressFragment() {
        // Required empty public constructor
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_address, container, false);
        context = view.getContext();
        paymentActivity = (PaymentActivity) getActivity();
        assert paymentActivity != null;
        jsondata = paymentActivity.getJsondata();
        total = paymentActivity.getTotal();
        email = SPmanager.getPreference(view.getContext(), "email");
        name = SPmanager.getPreference(view.getContext(), "user_name");
        phone = SPmanager.getPreference(view.getContext(), "user_phone");
        edtcity = SPmanager.getPreference(view.getContext(), "edtcity");
        edtpincode = SPmanager.getPreference(view.getContext(), "edtpincode");
        edtbillcity = SPmanager.getPreference(view.getContext(), "edtbillcity");
        edtbillpincode = SPmanager.getPreference(view.getContext(), "edtbillpincode");
        note = SPmanager.getPreference(context, "note");
        ;
        countryList = new ArrayList<>();
        getIntens();
        init();
        return view;
    }

    private void getIntens() {
        Intent intent = getActivity().getIntent();
        ispress = intent.getBooleanExtra("ispress", false);
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

    private void init() {
        spinner_country = view.findViewById(R.id.spinner_country);
        RelativeLayout rel_location = view.findViewById(R.id.rel_location);
        edt_firstname = view.findViewById(R.id.edt_firstname);
        edt_mobile = view.findViewById(R.id.edt_mobile);
        edt_address = view.findViewById(R.id.edt_address);
        edt_town = view.findViewById(R.id.edt_town);
        edt_pincode = view.findViewById(R.id.edt_pincode);
        edt_email = view.findViewById(R.id.edt_email);
        edt_note = view.findViewById(R.id.edt_note);
        edt_country = view.findViewById(R.id.edt_country);
        rel_billaddress = view.findViewById(R.id.rel_billaddress);
        edt_billaddress = view.findViewById(R.id.edt_billaddress);
        edt_billtown = view.findViewById(R.id.edt_billtown);
        edt_billpincode = view.findViewById(R.id.edt_billpincode);
        switch_button = view.findViewById(R.id.switch_button);
        lay_billtown = view.findViewById(R.id.lay_billtown);
        RelativeLayout rel_billlocation = view.findViewById(R.id.rel_billlocation);
        edt_email.setText(email);
        edt_firstname.setText(name);

        if (SPmanager.getBilling(view.getContext())) {
            SPmanager.setBilling(view.getContext(), true);
            switch_button.setChecked(true);
            rel_billaddress.setVisibility(View.GONE);
        } else {
            SPmanager.setBilling(view.getContext(), false);
            rel_billaddress.setVisibility(View.VISIBLE);
            switch_button.setChecked(false);
            lay_billtown.getParent().requestChildFocus(lay_billtown, lay_billtown);
        }
        switch_button.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    SPmanager.setBilling(view.getContext(), true);
                    rel_billaddress.setVisibility(View.GONE);
                } else {
                    SPmanager.setBilling(view.getContext(), false);
                    rel_billaddress.setVisibility(View.VISIBLE);
                    lay_billtown.getParent().requestChildFocus(lay_billtown, lay_billtown);
                }
            }
        });
        if (phone != null) {
            edt_mobile.setText(phone);
        }
        loadCountrydata();
        PaymentActivity.txt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
        String address;
        rel_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPmanager.saveValue(context, "edtcity", edt_town.getText().toString());
                SPmanager.saveValue(context, "edtpincode", edt_pincode.getText().toString());
                SPmanager.saveValue(context, "note", edt_note.getText().toString());

                Intent intent = new Intent(view.getContext(), LocationActivity.class);
                intent.putExtra("ispress", ispress);
                intent.putExtra("json", jsondata);
                intent.putExtra("total", total);
                intent.putExtra("original", "yes");
                intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        rel_billlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPmanager.saveValue(context, "edtbillcity", edt_billtown.getText().toString());
                SPmanager.saveValue(context, "edtbillpincode", edt_billpincode.getText().toString());
                SPmanager.saveValue(context, "note", edt_note.getText().toString());
                SPmanager.saveValue(context, "edtcity", edt_town.getText().toString());
                SPmanager.saveValue(context, "edtpincode", edt_pincode.getText().toString());
                Intent intent = new Intent(view.getContext(), LocationActivity.class);
                intent.putExtra("ispress", ispress);
                intent.putExtra("json", jsondata);
                intent.putExtra("total", total);
                intent.putExtra("original", "no");
                intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        try {
            address = SPmanager.getPreference(context, "address");
            String town = SPmanager.getPreference(context, "city");
            String pincode = SPmanager.getPreference(context, "pincode");
            String country = SPmanager.getPreference(context, "country");
            String note1 = SPmanager.getPreference(context, "note");

            String billaddress = SPmanager.getPreference(context, "billaddress");
            String billtown = SPmanager.getPreference(context, "billcity");
            String billpincode = SPmanager.getPreference(context, "billpincode");
            String billcountry = SPmanager.getPreference(context, "billcountry");


            edt_address.setText(address);

            edt_country.setText(country);
            if (town != null) {
                edt_town.setText(town);
            } else {
                edt_town.setText(edtcity);
            }

            edt_note.setText(note);

            if (pincode != null) {
                edt_pincode.setText(pincode);
            } else {
                edt_pincode.setText(edtpincode);
            }

            if (!billaddress.equals("null")) {
                edt_billaddress.setText(billaddress);
            }
            if (!billtown.equals("null")) {
                edt_billtown.setText(billtown);
            } else {
                edt_billtown.setText(edtbillcity);
            }
            if (!billpincode.equals("null")) {
                edt_billpincode.setText(billpincode);
            } else {
                edt_billpincode.setText(edtbillpincode);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void validate() {
            SPmanager.saveValue(context, "edtcity", edt_town.getText().toString());
            SPmanager.saveValue(context, "edtpincode", edt_pincode.getText().toString());
            SPmanager.saveValue(context, "edtbillcity", edt_billtown.getText().toString());
            SPmanager.saveValue(context, "edtbillpincode", edt_billpincode.getText().toString());
            if (!edt_firstname.getText().toString().isEmpty()) {
                if (!edt_address.getText().toString().isEmpty()) {

                    if (!edt_town.getText().toString().isEmpty()) {
                        if (!edt_pincode.getText().toString().isEmpty()) {

                            if (isValidEmail(edt_email.getText().toString().trim())) {

                                if (!edt_mobile.getText().toString().trim().isEmpty()) {
//                                    if (!edt_note.getText().toString().isEmpty()) {
                                        if (switch_button.isChecked()) {
                                            Map<String, String> data = new HashMap<>();
                                            data.put("address", edt_address.getText().toString());
                                            data.put("pin", edt_pincode.getText().toString().trim());
                                            data.put("town", edt_town.getText().toString());
                                            data.put("country", edt_country.getText().toString());
                                            data.put("f_name", edt_firstname.getText().toString());
                                            data.put("notes", edt_note.getText().toString());
                                            data.put("email", edt_email.getText().toString());
                                            data.put("order_mobile", edt_mobile.getText().toString());
                                            EventBus.getDefault().postSticky(data);
                                            PaymentActivity.viewPager.setCurrentItem(PaymentActivity.viewPager.getCurrentItem() + 1);
                                        } else {
                                            if (!edt_billaddress.getText().toString().isEmpty()) {

                                                if (!edt_billtown.getText().toString().isEmpty()) {

                                                    if (!edt_billpincode.getText().toString().isEmpty()) {
                                                        Map<String, String> data = new HashMap<>();
                                                        data.put("address", edt_address.getText().toString());
                                                        data.put("pin", edt_pincode.getText().toString().trim());
                                                        data.put("town", edt_town.getText().toString());
                                                        data.put("country", country_name);
                                                        data.put("f_name", edt_firstname.getText().toString());
                                                        data.put("notes", edt_note.getText().toString());
                                                        data.put("email", edt_email.getText().toString());
                                                        data.put("shippingaddress", edt_billaddress.getText().toString());
                                                        data.put("shippingpin", edt_billpincode.getText().toString().trim());
                                                        data.put("shippingtown", edt_billtown.getText().toString());
                                                        data.put("order_mobile", edt_mobile.getText().toString());

                                                        EventBus.getDefault().postSticky(data);
                                                        PaymentActivity.viewPager.setCurrentItem(PaymentActivity.viewPager.getCurrentItem() + 1);
                                                    } else {
                                                        edt_billpincode.requestFocus();
                                                        edt_billpincode.setError(getString(R.string.enter_billing_address));
                                                        edt_billpincode.getParent().requestChildFocus(edt_billpincode, edt_billpincode);
                                                    }

                                                } else {
                                                    edt_billtown.requestFocus();
                                                    edt_billtown.setError(getString(R.string.enter_billing_town));
                                                    edt_billtown.getParent().requestChildFocus(edt_billtown, edt_billtown);
                                                }

                                            } else {
                                                edt_billaddress.requestFocus();
                                                edt_billaddress.setError(getString(R.string.enter_billing_address));
                                                edt_billaddress.getParent().requestChildFocus(edt_billaddress, edt_billaddress);
                                            }
                                        }


//                                    } else {
//                                        edt_note.requestFocus();
//                                        edt_note.setError(getString(R.string.enter_notes));
//                                        edt_email.getParent().requestChildFocus(edt_email, edt_email);
//                                    }
                                } else {
                                    edt_mobile.requestFocus();
                                    edt_mobile.setError(getString(R.string.enter_mobile));
                                }

                            } else {
                                if (edt_email.getText().toString().isEmpty()) {
                                    edt_email.requestFocus();
                                    edt_email.setError(getString(R.string.enter_email));
                                } else {
                                    edt_email.requestFocus();
                                    edt_email.setError(getString(R.string.invalid_email));
                                    edt_email.getParent().requestChildFocus(edt_email, edt_email);
                                }
                            }

                        } else {
                            edt_pincode.requestFocus();
                            edt_pincode.setError(getString(R.string.enter_pincode));
                            edt_pincode.getParent().requestChildFocus(edt_pincode, edt_pincode);
                        }

                    } else {
                        edt_town.requestFocus();
                        edt_town.setError(getString(R.string.enter_town));
                        edt_town.getParent().requestChildFocus(edt_town, edt_town);
                    }

                } else {
                    edt_address.requestFocus();
                    edt_address.setError(getString(R.string.enter_address));
                    edt_address.getParent().requestChildFocus(edt_address, edt_address);
                }
            } else {
                edt_firstname.requestFocus();
                edt_firstname.setError(getString(R.string.enter_firstname));
                edt_firstname.getParent().requestChildFocus(edt_firstname, edt_firstname);
            }
//        }
//        else {
//            Toast.makeText(view.getContext(), R.string.select_location, Toast.LENGTH_SHORT).show();
//        }
    }

    private void loadCountrydata() {
        for (String s : country) {
            CategoryGetSet categoryGetSet = new CategoryGetSet();
            categoryGetSet.setName(s);
            countryList.add(categoryGetSet);
        }
        CustomSpinnerAdapter customAdapter = new CustomSpinnerAdapter(view.getContext(), countryList);
        spinner_country.setAdapter(customAdapter);
        spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country_name = countryList.get(position).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}