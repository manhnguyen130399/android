package freaktemplate.shopping.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import freaktemplate.shopping.R;
import freaktemplate.shopping.getset.category.Categorypojjo;

public class CatItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Categorypojjo.Subcategory> catList;

    public CatItemAdapter(List<Categorypojjo.Subcategory> catList) {
        this.catList = catList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_catitem, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Categorypojjo.Subcategory catPojjo = catList.get(position);
        ((MyViewHolder) holder).txt_catitem.setText(catPojjo.getName());
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_catitem;
        ImageView arrow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_catitem = itemView.findViewById(R.id.txt_catitem);
            arrow = itemView.findViewById(R.id.arrow);
        }
    }
}
