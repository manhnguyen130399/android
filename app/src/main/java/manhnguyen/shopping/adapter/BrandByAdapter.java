package manhnguyen.shopping.adapter;

import android.content.Context;
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
import manhnguyen.shopping.getset.Filter.Brand;
import manhnguyen.shopping.interfaces.CoutomSortbyListner;

public class BrandByAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Brand> catList;
    private Context mcontext;
    private CoutomSortbyListner listener;
    private int mSelectedPosition = -1;

    public BrandByAdapter(Context context, List<Brand> catList) {
        this.catList = catList;
        mcontext = context;
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
        Brand catPojjo = catList.get(position);
        ((MyViewHolder) holder).txt_catName.setText(catPojjo.getBrandName());

        if (position == mSelectedPosition) {
            ((MyViewHolder) holder).radio_sortby.setVisibility(View.VISIBLE);
        } else {
            ((MyViewHolder) holder).radio_sortby.setVisibility(View.GONE);
        }

        ((MyViewHolder) holder).rel_main.setOnClickListener(v -> {
            mSelectedPosition = position;
            notifyDataSetChanged();
            listener.brandClick(position);
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
