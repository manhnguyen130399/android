package freaktemplate.shopping.activity;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import freaktemplate.shopping.R;

public class TermsAndCondition extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);

        setTermsCondition();
    }

    private void setTermsCondition() {
        WebView web = findViewById(R.id.web);
        web.loadUrl("file:///android_asset/" + getString(R.string.terms_condition_filename));
    }

}
