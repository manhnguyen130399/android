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

import org.json.JSONException;
import org.json.JSONObject;

import manhnguyen.shopping.R;
import manhnguyen.shopping.utils.SPmanager;
import manhnguyen.shopping.utils.UtilHelper;

public class ResetPasswordActivity extends AppCompatActivity {
    private static final String TAG = "ResetPasswordActivity";
    private EditText edt_new_password;
    private ResetPasswordActivity context;
    private String status,msg,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        context = ResetPasswordActivity.this;
        initlise();
    }

    private void initlise() {
        Button bn_confirm = findViewById(R.id.bn_confirm);
        edt_new_password = findViewById(R.id.edt_new_password);
        bn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newPassword = edt_new_password.getText().toString().trim();
                ResetPassword(newPassword);
            }
        });
    }
    private void ResetPassword( final String newPassword) {
        UtilHelper.showdialog(context);
        email = SPmanager.getPreference(ResetPasswordActivity.this, "mailSent");
        String url = getString(R.string.link) + "resetpassword";
        AndroidNetworking.post(url)
                .addBodyParameter("password", newPassword)
                .addBodyParameter("email", email)
                .setContentType("application/json; charset=utf-8")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            status = response.getString("status");
                            msg = response.getString("msg");
                        } catch (JSONException e) {
                            status = "0";
                        }
                        if (status.equals("1")){
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            UtilHelper.hidedialog();
                            Intent intent = new Intent(ResetPasswordActivity.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            UtilHelper.hidedialog();
                            Toast.makeText(ResetPasswordActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        UtilHelper.hidedialog();
                        Log.e(TAG, "onError: " + anError.getErrorBody());
                        Toast.makeText(ResetPasswordActivity.this, getString(R.string.try_again), Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
