package freaktemplate.shopping.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import freaktemplate.shopping.R;
import freaktemplate.shopping.getset.CatPojjo;
import freaktemplate.shopping.getset.Orderlist.SubList;

public class OrderlistAdapter extends BaseAdapter {
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_HEADER = 2;
    private static final int AD = 0;
    Context context;
    private LayoutInflater inflater;
    private ArrayList data;


    public OrderlistAdapter(Context context, ArrayList data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public boolean isEnabled(int position) {
        return (getItemViewType(position) == TYPE_ITEM);
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position) == null) {
            return AD;
        } else {
            if (data.get(position) instanceof CatPojjo) {
                /*Main Categories*/
                return TYPE_HEADER;
            } else
                /*Sub Categories*/
                return TYPE_ITEM;
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_ITEM:
                vi = inflater.inflate(R.layout.cell_suborderitem, parent, false);
                break;
            case TYPE_HEADER:
                vi = inflater.inflate(R.layout.cell_header, parent, false);
                break;
            case AD:
                if (context.getString(R.string.show_facebook_ads).equals("yes")) {
                    vi = inflater.inflate(R.layout.cell_facebook_native, parent, false);
                } else {
                    vi = inflater.inflate(R.layout.cell_admob, parent, false);
                }

                break;
        }

        switch (type) {
            case TYPE_ITEM:
                TextView txt_status = vi.findViewById(R.id.txt_status);
                TextView txt_date = vi.findViewById(R.id.txt_date);
                TextView txt_orderno = vi.findViewById(R.id.txt_orderno);
                TextView txt_item = vi.findViewById(R.id.txt_item);
                TextView txt_bill = vi.findViewById(R.id.txt_bill);
                ImageView img_wish = vi.findViewById(R.id.img_wish);
                ProgressBar progress = vi.findViewById(R.id.progress);
                SubList item = (SubList) data.get(position);
                txt_status.setText(item.getOrderStatus());
                txt_orderno.setText(context.getString(R.string.order_no) + item.getOrderNo());
                txt_item.setText(context.getString(R.string.items) + " : " + item.getItem());
                txt_bill.setText(context.getString(R.string.bill) + context.getString(R.string.dolar) + item.getBill());
                txt_date.setText(item.getOrderDate());
                progress.setVisibility(View.VISIBLE);
                Glide.with(context).load(item.getImage()).addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progress.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progress.setVisibility(View.GONE);
                        return false;
                    }
                }).into(img_wish);
                break;
            case TYPE_HEADER:
                TextView txt_header = vi.findViewById(R.id.txt_header);
                CatPojjo head = (CatPojjo) data.get(position);
                txt_header.setText(head.getName());
                break;
            case AD:
                final AdView mAdView = vi.findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
                View finalVi = vi;
                mAdView.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        finalVi.findViewById(R.id.rel_ad).setVisibility(View.VISIBLE);
                    }
                });


                break;
        }

        return vi;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_status, txt_date, txt_orderno, txt_item, txt_bill, txt_view;
        ImageView img_wish, img_right;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_status = itemView.findViewById(R.id.txt_status);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_orderno = itemView.findViewById(R.id.txt_orderno);
            txt_item = itemView.findViewById(R.id.txt_item);
            txt_bill = itemView.findViewById(R.id.txt_bill);
            txt_view = itemView.findViewById(R.id.txt_view);
            img_wish = itemView.findViewById(R.id.img_wish);
            img_right = itemView.findViewById(R.id.img_right);
        }
    }

}
