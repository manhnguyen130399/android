package manhnguyen.shopping.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import manhnguyen.shopping.R;


public class ForgetPasswordActivity extends AppCompatActivity {

    private static final String TAG = "ForgetPasswordActivity";
    private EditText edt_mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initlise();
    }

    private void initlise() {
        Button bn_confirm = findViewById(R.id.bn_confirm);
        edt_mail = findViewById(R.id.edt_mail);
        bn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = edt_mail.getText().toString().trim();
                if (!email.isEmpty()) {
                    ForgotPass(email);

                } else {
                    edt_mail.setError("Enter Valid Email");
                }
            }
        });
    }

    private void ForgotPass(String email) {

    }


    public void backpress(View view) {
        onBackPressed();
    }
}
