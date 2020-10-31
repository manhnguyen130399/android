package manhnguyen.shopping.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatCheckBox;

public class MyCheckBox extends AppCompatCheckBox {

    // Constructors
    public MyCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCheckBox(Context context) {
        super(context);
        init();
    }

    // This class requires myfont.ttf to be in the assets/fonts folder
    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "font/opensans_semibold.ttf");
        setTypeface(tf);
    }
}