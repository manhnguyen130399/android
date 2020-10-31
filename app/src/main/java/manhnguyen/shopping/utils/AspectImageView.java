package manhnguyen.shopping.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @author Sherif elKhatib
 *
 * ImageView Class that maintains the width of the view and changes height to keep the aspect ratio.
 */
public class AspectImageView extends ImageView {

    public AspectImageView(Context context) {
        super(context);
    }

    public AspectImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AspectImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(getBackground() == null || getBackground().getIntrinsicHeight()==0 || getBackground().getIntrinsicWidth()==0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width * getBackground().getIntrinsicHeight() / getBackground().getIntrinsicWidth();
        setMeasuredDimension(width, height);
    }
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    @Override
    public void setImageBitmap(Bitmap bm) {
        if(bm == null)
            return;
        BitmapDrawable bd = new BitmapDrawable(getContext().getResources(), bm);
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN)
            setBackground(bd);
        else
            setBackgroundDrawable(bd);
    }
}