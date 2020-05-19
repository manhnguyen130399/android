package freaktemplate.shopping.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.List;

import freaktemplate.shopping.R;
import freaktemplate.shopping.getset.Search.Datum;
import freaktemplate.shopping.interfaces.CoutomProductLister;
import freaktemplate.shopping.interfaces.WishlistButtonListener;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Datum> searchList;
    private Context mcontext;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private CoutomProductLister listener;
    private int visibleThreshold = 5;
    private WishlistButtonListener wishlist;

    public SearchAdapter(RecyclerView recyclerView, Context context, List<Datum> searchList) {
        this.searchList = searchList;
        mcontext = context;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                assert linearLayoutManager != null;
                visibleThreshold = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int lastVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                final int lastItem = lastVisibleItem + visibleThreshold;
                if (totalItemCount >= mcontext.getResources().getInteger(R.integer.numberOfRecord)) {
                    if (!isLoading && totalItemCount == (lastItem)) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        isLoading = true;
                    }
                }
            }
        });
    }

    public void WishButtonListener(WishlistButtonListener listener) {
        this.wishlist = listener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public void CoutomProductButtonListener(CoutomProductLister listener) {
        this.listener = listener;
    }

    public void setLoaded() {
        isLoading = false;
    }

    public void addItem(List<Datum> item, int position) {
        if (item.size() != 0) {
            searchList.addAll(item);
            notifyItemInserted(position);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_igrid_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        Datum itemPojjo = searchList.get(position);
        ((MyViewHolder) holder).txt_itemname.setText(itemPojjo.getName());
        ((MyViewHolder) holder).txt_totalreview.setText("(" + itemPojjo.getTotalreview() + " " + mcontext.getString(R.string.reviews) + ")");
        ((MyViewHolder) holder).txt_price.setText(mcontext.getString(R.string.dolar) + " " + itemPojjo.getPrice());
        ((MyViewHolder) holder).txt_original.setText(mcontext.getString(R.string.dolar) + " " + itemPojjo.getMRP());
        if (itemPojjo.getDiscount() != null) {
            ((MyViewHolder) holder).txt_discount.setVisibility(View.VISIBLE);
            ((MyViewHolder) holder).txt_discount.setText(itemPojjo.getDiscount() + mcontext.getString(R.string.percentage));
        }else {
            ((MyViewHolder) holder).txt_discount.setVisibility(View.GONE);
        }
        try {
            ((MyViewHolder) holder).rate_detail.setRating(Float.parseFloat(String.valueOf(itemPojjo.getRatting())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((MyViewHolder) holder).txt_original.setPaintFlags(((MyViewHolder) holder).txt_original.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        Glide.with(mcontext).load(itemPojjo.getBasicImage()).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                ((MyViewHolder) holder).progress.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                ((MyViewHolder) holder).progress.setVisibility(View.GONE);
                return false;
            }
        }).into(((MyViewHolder) holder).ic_image);
        allclickEvent(((MyViewHolder) holder), position);
    }

    private void allclickEvent(MyViewHolder holder, int position) {
        holder.ic_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishlist.onAddClicked(position, holder.ic_like, holder.ic_unlike);

            }
        });
        holder.ic_unlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishlist.onAddClicked(position, holder.ic_like, holder.ic_unlike);
            }
        });
        holder.rel_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.layoutClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_catname, txt_buynow, txt_totalreview, txt_itemname, txt_discount, txt_price, txt_original;
        ImageView ic_like, ic_unlike, ic_image;
        ScaleRatingBar rate_detail;
        ProgressBar progress;
        RelativeLayout rel_main;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_buynow = itemView.findViewById(R.id.txt_buynow);
            txt_totalreview = itemView.findViewById(R.id.txt_totalreview);
            txt_itemname = itemView.findViewById(R.id.txt_itemname);
            txt_discount = itemView.findViewById(R.id.txt_discount);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_original = itemView.findViewById(R.id.txt_original);
            ic_like = itemView.findViewById(R.id.ic_like);
            ic_unlike = itemView.findViewById(R.id.ic_unlike);
            ic_image = itemView.findViewById(R.id.ic_image);
            rate_detail = itemView.findViewById(R.id.rate_detail);
            progress = itemView.findViewById(R.id.progress);
            rel_main = itemView.findViewById(R.id.rel_main);

        }
    }
}
