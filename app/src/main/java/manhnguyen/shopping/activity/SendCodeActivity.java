package manhnguyen.shopping.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import manhnguyen.shopping.R;
import manhnguyen.shopping.utils.SPmanager;
import manhnguyen.shopping.utils.UtilHelper;
public class SendCodeActivity extends AppCompatActivity {
    private static final String TAG = "SendCodeActivity";
    private EditText edt_code;
    private SendCodeActivity context;
    private String status,msg,currentCode,email,code = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_code);
        context = SendCodeActivity.this;
        initlise();
    }

    private void initlise() {
        Button bn_confirm = findViewById(R.id.bn_confirm);
        edt_code = findViewById(R.id.edt_code);
        bn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String code = edt_code.getText().toString().trim();
                if (!code.isEmpty()) {
                    currentCode = SPmanager.getPreference(SendCodeActivity.this, "code");
                    if(currentCode.equals(code))
                    {
                        Intent intent = new Intent(context, ResetPasswordActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(SendCodeActivity.this, "your code is incorrect", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    edt_code.setError("Enter Valid Email");
                }
            }
        });
    }

}
