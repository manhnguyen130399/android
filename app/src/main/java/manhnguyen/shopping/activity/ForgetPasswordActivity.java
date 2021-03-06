package manhnguyen.shopping.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import manhnguyen.shopping.utils.SPmanager;
import manhnguyen.shopping.utils.UtilHelper;

import manhnguyen.shopping.R;


public class ForgetPasswordActivity extends AppCompatActivity {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static final String TAG = "ForgetPasswordActivity";
    private EditText edt_mail;
    private ForgetPasswordActivity context;
    private String status,msg,code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        context = ForgetPasswordActivity.this;
        initlise();
    }

    private void initlise() {
        Button bn_confirm = findViewById(R.id.bn_confirm);
        edt_mail = findViewById(R.id.edt_mail);
        bn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = edt_mail.getText().toString().trim();
                if (!email.isEmpty() && isMail(email)) {
                    ForgotPass(email);
                } else {
                    edt_mail.setError("Enter Valid Email");
                }
            }
        });
    }

    private void ForgotPass( final String email) {
        UtilHelper.showdialog(context);
        String url = getString(R.string.link) + "forgotpassword";
        AndroidNetworking.post(url)
                .addBodyParameter("email", email)
                .setContentType("application/json; charset=utf-8")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            status = response.getString("status");
                            code = response.getString("code");
                            msg = response.getString("msg");
                        } catch (JSONException e) {
                            status = "0";
                        }
                        if (status.equals("1")){
                            SPmanager.saveValue(context, "code", code);
                            SPmanager.saveValue(context, "mailSent", email);
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            UtilHelper.hidedialog();
                            Intent intent = new Intent(ForgetPasswordActivity.this, SendCodeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            UtilHelper.hidedialog();
                            Toast.makeText(ForgetPasswordActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        UtilHelper.hidedialog();
                        Log.e(TAG, "onError: " + anError.getErrorBody());
                        Toast.makeText(ForgetPasswordActivity.this, getString(R.string.try_again), Toast.LENGTH_SHORT).show();

                    }
                });
    }
    private boolean isMail(final String email){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }


    public void backpress( final View view) {
        onBackPressed();
    }
}
