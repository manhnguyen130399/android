package freaktemplate.shopping.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import freaktemplate.shopping.R;
import freaktemplate.shopping.getset.category.Categorypojjo;

public class CategorylistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Categorypojjo.Datum> catList;

    public CategorylistAdapter(List<Categorypojjo.Datum> catList) {
        this.catList = catList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_category, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Categorypojjo.Datum catPojjo = catList.get(position);
        ((MyViewHolder) holder).txt_catName.setText(catPojjo.getName());
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_catName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_catName = itemView.findViewById(R.id.txt_catName);
        }
    }
}
