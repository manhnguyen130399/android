package freaktemplate.shopping.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.willy.ratingbar.ScaleRatingBar;

import java.util.List;

import freaktemplate.shopping.R;
import freaktemplate.shopping.getset.RateGetSet;
import freaktemplate.shopping.interfaces.CoutomSortbyListner;

public class RatingByAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<RateGetSet> catList;
    private CoutomSortbyListner listener;
    private int mSelectedPosition = -1;

    public RatingByAdapter(List<RateGetSet> catList) {
        this.catList = catList;
    }

    public void setSortListener(CoutomSortbyListner listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_rateby, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        RateGetSet rateGetSet = catList.get(position);
        ((MyViewHolder) holder).ratingbar.setRating(Float.parseFloat(rateGetSet.getRate()));

        if (position == mSelectedPosition) {
            // customize the selected item's background and sub views
            ((MyViewHolder) holder).radio_sortby.setVisibility(View.VISIBLE);
        } else {
            ((MyViewHolder) holder).radio_sortby.setVisibility(View.GONE);
        }

        ((MyViewHolder) holder).rel_main.setOnClickListener(v -> {
            mSelectedPosition = position;
            notifyDataSetChanged();
            listener.reviewClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView radio_sortby;
        ScaleRatingBar ratingbar;
        RelativeLayout rel_main;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingbar = itemView.findViewById(R.id.ratingbar);
            radio_sortby = itemView.findViewById(R.id.radio_sortby);
            rel_main = itemView.findViewById(R.id.rel_main);
        }
    }

}
