package manhnguyen.shopping.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import manhnguyen.shopping.R;


public class ForgetPasswordActivity extends AppCompatActivity {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

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
                if (!email.isEmpty() && isMail(email)) {
                    ForgotPass(email);
                } else {
                    edt_mail.setError("Enter Valid Email");
                }
            }
        });
    }

    private void ForgotPass( final String email) {
        Toast.makeText(this,"We can't support you forgot password. ",Toast.LENGTH_LONG).show();
    }
    private boolean isMail(final String email){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }


    public void backpress( final View view) {
        onBackPressed();
    }
}
