package manhnguyen.shopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import manhnguyen.shopping.R;
import manhnguyen.shopping.getset.OrderDetail.Product;

public class OrderCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    private List<Product> orderlist;

    public OrderCartAdapter(Context context, List<Product> orderlist) {
        this.context = context;
        this.orderlist = orderlist;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_cartorder, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).txt_price.setText(context.getString(R.string.dolar) + orderlist.get(position).getPrice());
        ((MyViewHolder) holder).txt_wishname.setText(orderlist.get(position).getName());
        ((MyViewHolder) holder).txt_category.setText(/*context.getString(R.string.category) + " " + ":" + " " +*/ orderlist.get(position).getCategory());
        ((MyViewHolder) holder).txt_item.setText(orderlist.get(position).getQty() + " " + context.getString(R.string.item));
        Glide.with(context).load(orderlist.get(position).getBasicImage()).into(((MyViewHolder) holder).img_wish);
    }

    @Override
    public int getItemCount() {
        return orderlist.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_wishname, txt_price, txt_category, txt_item;
        ImageView img_wish;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_wishname = itemView.findViewById(R.id.txt_wishname);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_category = itemView.findViewById(R.id.txt_category);
            txt_item = itemView.findViewById(R.id.txt_item);
            img_wish = itemView.findViewById(R.id.img_wish);
        }
    }
}
