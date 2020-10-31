package manhnguyen.shopping.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import manhnguyen.shopping.R;
import manhnguyen.shopping.getset.wishlist.Wishpojjo;
import manhnguyen.shopping.interfaces.CoutomProductLister;
import manhnguyen.shopping.interfaces.WishlistButtonListener;

public class WishlistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int CONTENT = 1;
    private static final int AD = 0;
    Context context;
    private List<Wishpojjo.Wish> wishlist;
    private WishlistButtonListener wishlistener;
    private CoutomProductLister listener;

    public WishlistAdapter(Context context, List<Wishpojjo.Wish> wishlist) {
        this.context = context;
        this.wishlist = wishlist;
    }

    public void CoutomProductButtonListener(CoutomProductLister listener) {
        this.listener = listener;
    }

    public void setCustomWishButtonListener(WishlistButtonListener listener) {
        this.wishlistener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        Object recyclerViewItem = wishlist.get(position);
        if (recyclerViewItem != null) {
            return CONTENT;
        }
        return AD;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case AD:
                if (context.getString(R.string.show_facebook_ads).equals("yes")) {
                    View bannerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_facebook_native, parent, false);
                    return new FacebookNativeAdViewholder(bannerView);
                } else {
                    View bannerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_admob, parent, false);
                    return new ViewHolderAdMob(bannerView);
                }
            case CONTENT:
                // Fall through.
            default:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_wishlist, parent, false);
                return new MyViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {


        int viewType = getItemViewType(position);

        switch (viewType) {
            case AD:
                break;

            case CONTENT:
                // fall through
            default: {
                final Wishpojjo.Product product = wishlist.get(position).getProduct();
                ((MyViewHolder) holder).txt_price.setText(context.getString(R.string.dolar) + product.getDiscount());
                ((MyViewHolder) holder).add_cart.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).txt_remove.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).txt_wishname.setText(product.getName());
                ((MyViewHolder) holder).content_load.setVisibility(View.VISIBLE);
                Glide.with(context).load(product.getBasicImage()).addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        ((MyViewHolder) holder).content_load.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        ((MyViewHolder) holder).content_load.setVisibility(View.GONE);
                        return false;
                    }
                }).into(((MyViewHolder) holder).img_wish);

                ((MyViewHolder) holder).txt_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        wishlistener.onremovelist(position, product.getId());
                    }
                });
                ((MyViewHolder) holder).add_cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.layoutClick(position);
                    }
                });
            }
        }
    }


    @Override
    public int getItemCount() {
        return wishlist.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_wishname, txt_price, add_cart, txt_remove;
        ImageView img_wish;
        LottieAnimationView content_load;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_wishname = itemView.findViewById(R.id.txt_wishname);
            txt_price = itemView.findViewById(R.id.txt_price);
            add_cart = itemView.findViewById(R.id.add_cart);
            txt_remove = itemView.findViewById(R.id.txt_remove);
            img_wish = itemView.findViewById(R.id.img_wish);
            content_load = itemView.findViewById(R.id.content_load);
        }
    }
}
