package freaktemplate.shopping.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import freaktemplate.shopping.R;
import freaktemplate.shopping.getset.refine.Sortbypojjo;
import freaktemplate.shopping.interfaces.CoutomSortbyListner;

public class SortByAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Sortbypojjo> catList;
    private CoutomSortbyListner listener;
    private int mCheckedPostion = -1;

    public SortByAdapter(List<Sortbypojjo> catList) {
        this.catList = catList;
    }

    public void setSortListener(CoutomSortbyListner listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_sortby, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Sortbypojjo catPojjo = catList.get(position);
        ((MyViewHolder) holder).txt_catName.setText(catPojjo.getName());
        ((MyViewHolder) holder).radio_sortby.setChecked(mCheckedPostion == position);
        ((MyViewHolder) holder).rel_main.setOnClickListener(v -> {
            if (position == mCheckedPostion) {
                ((MyViewHolder) holder).radio_sortby.setChecked(false);
                mCheckedPostion = -1;
                notifyDataSetChanged();
            } else {
                mCheckedPostion = position;
                notifyDataSetChanged();

            }
            listener.sortclick(position);
        });
        ((MyViewHolder) holder).radio_sortby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == mCheckedPostion) {
                    ((MyViewHolder) holder).radio_sortby.setChecked(false);
                    mCheckedPostion = -1;
                    notifyDataSetChanged();
                } else {
                    mCheckedPostion = position;
                    notifyDataSetChanged();

                }
                listener.sortclick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_catName;
        RadioButton radio_sortby;
        RelativeLayout rel_main;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_catName = itemView.findViewById(R.id.txt_name);
            radio_sortby = itemView.findViewById(R.id.radio_sortby);
            rel_main = itemView.findViewById(R.id.rel_main);
        }
    }

}
