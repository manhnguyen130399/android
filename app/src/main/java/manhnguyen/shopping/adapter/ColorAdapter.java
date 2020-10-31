package manhnguyen.shopping.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import manhnguyen.shopping.R;
import manhnguyen.shopping.getset.Filter.Color;
import manhnguyen.shopping.interfaces.CoutomSortbyListner;
import manhnguyen.shopping.utils.ColorCircleDrawable;

public class ColorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Color> catList;
    private CoutomSortbyListner listener;
    private int mSelectedPosition = -1;
    private CompoundButton lastCheckedRB = null;
    private int pos;

    public ColorAdapter(List<Color> catList) {
        this.catList = catList;
    }

    public void setSortListener(CoutomSortbyListner listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_colorby, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        pos = position;
        ((MyViewHolder) holder).txt_catName.setText(catList.get(position).getName());

        if (position == mSelectedPosition) {
            ((MyViewHolder) holder).radio_sortby.setVisibility(View.VISIBLE);
        } else {
            ((MyViewHolder) holder).radio_sortby.setVisibility(View.GONE);
        }

        try {
            ((MyViewHolder) holder).rel_bg.setBackground(new ColorCircleDrawable(android.graphics.Color.parseColor(catList.get(position).getCode())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((MyViewHolder) holder).rel_main.setOnClickListener(v -> {
            mSelectedPosition = position;
            notifyDataSetChanged();
            listener.colorClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_catName;
        ImageView radio_sortby, rel_bg;
        RelativeLayout rel_main;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_catName = itemView.findViewById(R.id.txt_name);
            radio_sortby = itemView.findViewById(R.id.radio_sortby);
            rel_bg = itemView.findViewById(R.id.rel_bg);
            rel_main = itemView.findViewById(R.id.rel_main);
        }
    }

}
