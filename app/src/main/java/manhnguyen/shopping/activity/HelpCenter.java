package manhnguyen.shopping.activity;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import manhnguyen.shopping.R;

public class HelpCenter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);
        setTermsCondition();
    }

    private void setTermsCondition() {
        WebView web = findViewById(R.id.web);
        web.loadUrl("file:///android_asset/" + getString(R.string.help_center1));
    }
}
