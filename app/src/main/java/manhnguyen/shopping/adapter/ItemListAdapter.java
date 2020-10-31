package manhnguyen.shopping.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import manhnguyen.shopping.R;
import manhnguyen.shopping.getset.bestseling.Datum;
import manhnguyen.shopping.getset.bestseling.Productdetail;
import manhnguyen.shopping.interfaces.CoutomProductLister;
import manhnguyen.shopping.interfaces.WishlistButtonListener;
import manhnguyen.shopping.utils.SqliteHelper;


public class ItemListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int CONTENT = 1;
    private static final int AD = 0;
    private List<Datum> catList;
    private Context mcontext;
    private CoutomProductLister listener;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private WishlistButtonListener wishlistener;

    public ItemListAdapter(RecyclerView recyclerView, Context context, List<Datum> catList, String layout) {
        this.catList = catList;
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

    public void setCustomWishButtonListener(WishlistButtonListener listener) {
        this.wishlistener = listener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public void setLoaded() {
        isLoading = false;
    }

    public void addItem(List<Datum> item, int position) {
        if (item.size() != 0) {
            catList.addAll(item);
            notifyItemInserted(position);
        }
    }

    public void addData(List<Datum> item) {
        catList.addAll(item);
        notifyDataSetChanged();
    }

    public void CoutomProductButtonListener(CoutomProductLister listener) {
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        Object recyclerViewItem = catList.get(position);
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
                if (mcontext.getString(R.string.show_facebook_ads).equals("yes")) {
                    View bannerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_facebook_native, parent, false);
                    return new FacebookNativeAdViewholder(bannerView);
                } else {
                    View bannerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_admob, parent, false);
                    return new ViewHolderAdMob(bannerView);
                }

            case CONTENT:
                // Fall through.
            default:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_home_items, parent, false);
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
               try {
                   Productdetail itemPojjo = catList.get(position).getProductdetail();
                ((MyViewHolder) holder).ic_dummy.setVisibility(View.GONE);
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

                ((MyViewHolder) holder).rate_detail.setRating(Float.parseFloat(String.valueOf(itemPojjo.getRatting())));
                ((MyViewHolder) holder).txt_original.setPaintFlags(((MyViewHolder) holder).txt_original.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                if (itemPojjo.getWish() == 0) {
                    ((MyViewHolder) holder).ic_like.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.unlike));
                } else {
                    ((MyViewHolder) holder).ic_like.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.fill_heart));
                }
                ((MyViewHolder) holder).progress.setVisibility(View.VISIBLE);
                Glide.with(mcontext).load(itemPojjo.getImage()).addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        ((MyViewHolder) holder).progress.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        ((MyViewHolder) holder).progress.setVisibility(View.GONE);
                        return false;
                    }
                }).into(((MyViewHolder) holder).ic_image);
                changeimage(((MyViewHolder) holder), position);
                allclickEvent(((MyViewHolder) holder), position);
               } catch (Exception e) {
                   e.printStackTrace();
               }
            }
        }
    }

    private void allclickEvent(final MyViewHolder holder, final int position) {
        holder.ic_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishlistener.onAddClicked(position, holder.ic_like, holder.ic_unlike);

            }
        });
        holder.ic_unlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishlistener.onAddClicked(position, holder.ic_like, holder.ic_unlike);
            }
        });
        holder.rel_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.layoutClick(position);
            }
        });

    }

    private void changeimage(MyViewHolder holder, int position) {
        SqliteHelper sqliteHelper = new SqliteHelper(mcontext);
        SQLiteDatabase db1 = sqliteHelper.getWritableDatabase();
        Cursor cur = db1.rawQuery("SELECT * FROM wishlist Where itemId ='" + catList.get(position).getProductdetail().getId() + "';", null);
        if (cur.moveToFirst()) {
            holder.ic_like.setVisibility(View.GONE);
            holder.ic_unlike.setVisibility(View.VISIBLE);
            cur.close();
            db1.close();
        } else {
            holder.ic_like.setVisibility(View.VISIBLE);
            holder.ic_unlike.setVisibility(View.GONE);
            cur.close();
            db1.close();
        }

    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_catname, txt_buynow, txt_totalreview, txt_itemname, txt_discount, txt_price, txt_original;
        ImageView ic_like, ic_unlike, ic_dummy, ic_image;
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
            ic_dummy = itemView.findViewById(R.id.ic_dummy);
        }
    }
}
