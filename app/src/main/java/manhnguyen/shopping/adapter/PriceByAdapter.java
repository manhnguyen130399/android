package manhnguyen.shopping.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import manhnguyen.shopping.R;
import manhnguyen.shopping.interfaces.CoutomSortbyListner;

public class PriceByAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<String> catList;
    private CoutomSortbyListner listener;
    private int mSelectedPosition = -1;

    public PriceByAdapter(List<String> catList) {
        this.catList = catList;
    }

    public void setSortListener(CoutomSortbyListner listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_sizeby, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((MyViewHolder) holder).txt_catName.setText(catList.get(position));

        if (position == mSelectedPosition) {
            // customize the selected item's background and sub views
            ((MyViewHolder) holder).radio_sortby.setVisibility(View.VISIBLE);
        } else {
            ((MyViewHolder) holder).radio_sortby.setVisibility(View.GONE);
        }

        ((MyViewHolder) holder).rel_main.setOnClickListener(v -> {
            mSelectedPosition = position;
            notifyDataSetChanged();
            listener.priceClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_catName;
        ImageView radio_sortby;
        RelativeLayout rel_main;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_catName = itemView.findViewById(R.id.txt_name);
            radio_sortby = itemView.findViewById(R.id.radio_sortby);
            rel_main = itemView.findViewById(R.id.rel_main);
        }
    }

}
