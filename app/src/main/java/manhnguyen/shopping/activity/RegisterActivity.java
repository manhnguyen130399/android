package manhnguyen.shopping.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import manhnguyen.shopping.R;
import manhnguyen.shopping.getset.Data;
import manhnguyen.shopping.getset.Register;
import manhnguyen.shopping.utils.SPmanager;
import manhnguyen.shopping.utils.UtilHelper;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RegisterActivity";
    private CheckBox checkBox;
    private EditText edt_phone;
    private EditText edt_email;
    private EditText edt_password;
    private EditText edt_cpassword;
    private EditText edt_name;
    private Gson gson;
    private RegisterActivity context;

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = RegisterActivity.this;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        inite();
    }

    private void inite() {
        TextView txt_brief = findViewById(R.id.txt_brief);
        checkBox = findViewById(R.id.check);
        edt_name = findViewById(R.id.edt_name);
        edt_phone = findViewById(R.id.edt_phone);
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        edt_cpassword = findViewById(R.id.edt_cpassword);
        TextView txt_register = findViewById(R.id.txt_register);
        String first = getString(R.string.txt_regdesc);
        String second = getString(R.string.termsand);
        txt_brief.setText(Html.fromHtml(first + " " + second));
        txt_brief.setOnClickListener(this);
        txt_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_register:
                validate();
                break;
            case R.id.txt_brief:
                Intent intent = new Intent(RegisterActivity.this, TermsAndCondition.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }

    private void validate() {
        String name = edt_name.getText().toString();
        String phone = edt_phone.getText().toString();
        String email = edt_email.getText().toString();
        String password = edt_password.getText().toString();
        String confirm_password = edt_cpassword.getText().toString();

        if (!name.isEmpty()) {
            if (!phone.isEmpty()) {
                if (isValidEmail(email)) {

                    if (!password.isEmpty()) {
                        if (confirm_password.equals(password)) {
                            if (checkBox.isChecked()) {
                                registerUser(name, phone, email, password);
                            } else {
                                Toast.makeText(this, R.string.accept_terms, Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            if (confirm_password.isEmpty()) {
                                edt_cpassword.setError(getString(R.string.enter_password));
                            } else {
                                edt_cpassword.setError(getString(R.string.password_error));
                            }

                        }
                    } else {
                        edt_password.setError(getString(R.string.enter_password));
                    }

                } else {
                    if (email.isEmpty()) {
                        edt_email.setError(getString(R.string.enter_email));
                    } else {
                        edt_email.setError(getString(R.string.invalid_email));
                    }
                }

            } else {
                edt_phone.setError(getString(R.string.enter_mobile));
            }

        } else {
            edt_name.setError(getString(R.string.enter_name));
        }

    }

    private void registerUser(String name, String phone, String email, String password) {
        UtilHelper.showdialog(context);
        String token = SPmanager.getPreference(context, "token");
        String url = getString(R.string.link) + "userregister";
        //String url = "http://192.168.0.30/api/userregister";
       // Log.e(TAG, "registerUser: " + url);
        AndroidNetworking.post(url)

                .addBodyParameter("name", name)
                .addBodyParameter("phone", phone)
                .addBodyParameter("email", email)
                .addBodyParameter("password", password)
                .addBodyParameter("token", token)
                .setContentType("application/json; charset=utf-8")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                       // Log.e(TAG, "onResponse: " + response);


                        Register responseObject = gson.fromJson(String.valueOf(response), Register.class);


                        Log.e(TAG, "onResponse: " + response);

                        if (responseObject.getStatus().equals(1)) {
                            Data data = responseObject.getData();
                            String id = data.getId().toString();
                            String first_name = data.getFirstName();
                            String phone1 = data.getPhone();
                            String email = data.getEmail();
                            Toast.makeText(RegisterActivity.this, id, Toast.LENGTH_SHORT).show();
                            SPmanager.saveValue(context, "userID", id);
                            SPmanager.saveValue(context, "email", email);
                            SPmanager.saveValue(context, "user_name", first_name);
                            SPmanager.saveValue(context, "first_name", first_name);
                            SPmanager.saveValue(context, "user_phone", phone1);
                            Toast.makeText(context, R.string.register_success, Toast.LENGTH_SHORT).show();
                            UtilHelper.hidedialog();
                            Intent intent = new Intent(context, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            UtilHelper.hidedialog();
                            Toast.makeText(RegisterActivity.this, responseObject.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        UtilHelper.hidedialog();
                        Log.e(TAG, "onError: " + anError.getErrorBody());
                        Toast.makeText(RegisterActivity.this, getString(R.string.try_again), Toast.LENGTH_SHORT).show();

                    }
                });
    }
}