package manhnguyen.shopping.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import manhnguyen.shopping.R;
import manhnguyen.shopping.getset.Profile.Data;
import manhnguyen.shopping.getset.Profile.Profilepojo;
import manhnguyen.shopping.utils.SPmanager;
import manhnguyen.shopping.utils.UtilHelper;

public class AccountDetail extends AppCompatActivity {
    private static final String TAG = "AccountDetail";
    private TextView txt_edit;
    private TextView txt_save;
    private EditText txt_name;
    private EditText txt_email;
    private EditText edt_phone;
    private EditText edt_address;
    private int isEdit = 1;
    private String uname, phone;
    private String userId;
    private String adress;
    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        initialise();
    }

    private void initialise() {
        edt_phone = findViewById(R.id.edt_phone);
        txt_email = findViewById(R.id.txt_email);
        txt_name = findViewById(R.id.txt_name);
        txt_edit = findViewById(R.id.txt_edit);
        txt_save = findViewById(R.id.txt_save);
        edt_address = findViewById(R.id.edt_address);

        txt_name.setEnabled(false);
        edt_phone.setEnabled(false);
        txt_email.setEnabled(false);
        edt_address.setEnabled(false);

        showdata();
        clickevent();

    }

    private void clickevent() {
        txt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEdit = 0;
                showtext();
            }
        });
        txt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });

    }

    private void showdata() {
        userId = SPmanager.getPreference(this, "userID");
        String uname = SPmanager.getPreference(this, "first_name");
        String adress = SPmanager.getPreference(this, "user_address");
        String phone_no = SPmanager.getPreference(this, "user_phone");
        String email = SPmanager.getPreference(this, "email");
        txt_name.setText(uname);
        if (adress != null) {
            edt_address.setText(adress);
        }
        if (phone_no != null) {
            edt_phone.setText(phone_no);
        }
        if (email != null) {
            txt_email.setText(email);
        }
    }


    private void validation() {
        uname = txt_name.getText().toString();
        adress = edt_address.getText().toString();
        phone = edt_phone.getText().toString();
        if (!uname.isEmpty()) {
            if (!adress.isEmpty()) {
                if (!phone.isEmpty()) {
                    sendData();
                } else {
                    edt_phone.setError(getString(R.string.select_phone));
                }
            } else {
                edt_address.setError(getString(R.string.select_address));
            }
        } else {
            txt_name.setError(getString(R.string.enterfname));
        }
    }

    private void sendData() {
        UtilHelper.showdialog(AccountDetail.this);
        String url = getString(R.string.link) + "editprofile";
        Log.e(TAG, "sendData: " + url);
        ANRequest.MultiPartBuilder multiPartBuilder = new ANRequest.MultiPartBuilder(url);
        multiPartBuilder.addMultipartParameter("name", uname);
        multiPartBuilder.addMultipartParameter("address", adress);
        multiPartBuilder.addMultipartParameter("phone", phone);
        multiPartBuilder.addMultipartParameter("user_id", userId);
        multiPartBuilder.setPriority(Priority.HIGH);
        ANRequest anRequest = multiPartBuilder.build();
        anRequest.getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "onResponse: " + response);
                Profilepojo profilepojo = gson.fromJson(String.valueOf(response), Profilepojo.class);
                if (profilepojo.getStatus().equals(1)) {
                    Data data = profilepojo.getData();
                    String email = data.getEmail();
                    String fname = data.getFirstName();
                    String lname = data.getLastName();
                    String address = data.getAddress();
                    String phone = data.getPhone();
                    String id = String.valueOf(data.getId());
                    SPmanager.saveValue(AccountDetail.this, "userID", id);
                    SPmanager.saveValue(AccountDetail.this, "user_name", fname);
                    SPmanager.saveValue(AccountDetail.this, "last_name", lname);
                    SPmanager.saveValue(AccountDetail.this, "user_email", email);
                    SPmanager.saveValue(AccountDetail.this, "user_address", address);
                    SPmanager.saveValue(AccountDetail.this, "user_phone", phone);
                    Toast.makeText(AccountDetail.this, R.string.update_success, Toast.LENGTH_SHORT).show();
                    UtilHelper.hidedialog();
                    Intent intent = new Intent(AccountDetail.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    UtilHelper.hidedialog();
                    Toast.makeText(AccountDetail.this, profilepojo.getMsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(ANError anError) {
                Log.e(TAG, "onError: " + anError.getErrorDetail());
                Log.e(TAG, "onError: " + anError.getErrorBody());
                UtilHelper.hidedialog();
            }
        });
    }

    private void showtext() {
        if (isEdit == 0) {
            txt_edit.setVisibility(View.GONE);
            txt_save.setVisibility(View.VISIBLE);
            txt_name.setEnabled(true);
            edt_phone.setEnabled(true);
            txt_email.setEnabled(false);
            edt_address.setEnabled(true);
            isEdit = 1;
        }
    }
}
