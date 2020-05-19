package freaktemplate.shopping.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freaktemplate.shopping.R;
import freaktemplate.shopping.fragment.DetailFragment;
import freaktemplate.shopping.fragment.MoreproductFragment;
import freaktemplate.shopping.getset.Offer.BigOffer;
import freaktemplate.shopping.utils.UtilHelper;

public class BigOfferAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mcontext;
    private List<BigOffer> adsList;
    private Bitmap image;

    public BigOfferAdapter(Context context, List<BigOffer> adsList) {

        mcontext = context;
        this.adsList = adsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_topoffer, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final BigOffer toplist = adsList.get(position);
        ((ItemViewHolder) holder).txt_brand.setText(toplist.getTitle());
        ((ItemViewHolder) holder).txt_latest_fashion.setText(toplist.getMainTitle());
        String isProduct = toplist.getIsProduct();
        if (isProduct.equals("1")) {
            ((ItemViewHolder) holder).txt_saveup_to.setText(mcontext.getString(R.string.save_upto) + " " + toplist.getFixed() + mcontext.getString(R.string.percentage) + mcontext.getString(R.string.offf));
        } else {
            ((ItemViewHolder) holder).txt_saveup_to.setText(mcontext.getString(R.string.new_price) + mcontext.getString(R.string.dolar) + toplist.getNewPrice());
        }
        Glide.with(mcontext).load(mcontext.getString(R.string.imagelink) + "public/upload/offer/image/" + toplist.getBanner()).into(((ItemViewHolder) holder).img_topoffer);
       /* try {
            URL url = new URL(mcontext.getString(R.string.imagelink) + "public/upload/offer/image/" + toplist.getBanner());
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            ((ItemViewHolder) holder).img_topoffer.setImageBitmap(image);
        } catch (IOException e) {
            System.out.println(e);
        }*/
        ((ItemViewHolder) holder).btn_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toplist.getCategoryId() != null || toplist.getCoupon_id() != null) {
                    Map<String, String> data = new HashMap<>();
                    data.put("category", UtilHelper.setValue(toplist.getCategoryId()));
                    data.put("subcategory", "0");
                    data.put("brand", "0");
                    data.put("price", "0");
                    data.put("discount", UtilHelper.setValue(toplist.getCoupon_id()));
                    data.put("ratting", "0");
                    data.put("filter", "0");
                    data.put("color", "0");
                    data.put("size", "0");
                    data.put("mainCat", toplist.getTitle());
                    data.put("coupon_id", UtilHelper.setValue(toplist.getCoupon_id()));
                    EventBus.getDefault().postSticky(data);
                    Fragment fragment = new MoreproductFragment();
                    FragmentManager fragmentManager = ((FragmentActivity) mcontext).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } else {
                    Map<String, String> data = new HashMap<>();
                    data.put("itemID", String.valueOf(adsList.get(position).getProductId()));
                    data.put("key", "home");
                    EventBus.getDefault().postSticky(data);
                    Fragment fragment = new DetailFragment();
                    FragmentManager fragmentManager = ((FragmentActivity) mcontext).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });
    }

    private void layoutImageView(ImageView imageView, Bitmap image) {

        float[] matrixv = new float[9];

        imageView.getImageMatrix().getValues(matrixv);

        int w = (int) (matrixv[Matrix.MSCALE_X] * image.getWidth());
        int h = (int) (matrixv[Matrix.MSCALE_Y] * image.getHeight());

        imageView.setMaxHeight(h);
        imageView.setMaxWidth(w);

    }

    @Override
    public int getItemCount() {
        return adsList.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView txt_brand, txt_latest_fashion, txt_saveup_to;
        ImageView img_topoffer;
        Button btn_shop;

        ItemViewHolder(@NonNull View imageLayout) {
            super(imageLayout);


            txt_brand = imageLayout.findViewById(R.id.txt_brand);
            txt_latest_fashion = imageLayout.findViewById(R.id.txt_latest_fashion);
            txt_saveup_to = imageLayout.findViewById(R.id.txt_saveup_to);
            img_topoffer = imageLayout.findViewById(R.id.img_topoffer);
            btn_shop = imageLayout.findViewById(R.id.btn_shop);

        }
    }
}
