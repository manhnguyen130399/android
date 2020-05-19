package freaktemplate.shopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import freaktemplate.shopping.R;
import freaktemplate.shopping.getset.Filter.Subcategory;


public class SubCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Subcategory> catList;
    private Context mcontext;

    public SubCategoryAdapter(Context context, List<Subcategory> catList) {
        this.catList = catList;
        mcontext = context;
    }

    public void swapItems(int itemAIndex, int itemBIndex) {

//        Collections.swap(catList, itemBIndex, itemAIndex); notifyItemMoved(itemBIndex, itemAIndex);
        //make sure to check if dataset is null and if itemA and itemB are valid indexes.
        Subcategory itemA = catList.get(itemAIndex);
        Subcategory itemB = catList.get(itemBIndex);
        catList.set(itemAIndex, itemB);
        catList.set(itemBIndex, itemA);
        notifyDataSetChanged(); //This will trigger onBindViewHolder method from the adapter.
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_cat_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Subcategory brand = catList.get(position);
        ((MyViewHolder) holder).txt_catname.setText(brand.getName());
        ((MyViewHolder) holder).txt_catname.setTextColor(mcontext.getResources().getColor(R.color.black));
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_catname;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_catname = itemView.findViewById(R.id.txt_catname);
        }
    }
}
