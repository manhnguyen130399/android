package manhnguyen.shopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import manhnguyen.shopping.R;
import manhnguyen.shopping.getset.category.Categorypojjo;
import manhnguyen.shopping.interfaces.CoutomProductLister;

public class CatOfferAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mcontext;
    private List<Categorypojjo.Offer> adsList;
    private CoutomProductLister listener;


    public CatOfferAdapter(Context context, List<Categorypojjo.Offer> adsList) {

        mcontext = context;
        this.adsList = adsList;
    }

    public void setListener(CoutomProductLister listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_catoffer, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Categorypojjo.Offer toplist = adsList.get(position);
        String price;
        if (toplist.getOfferType().equals("1")) {
            price = toplist.getFixed();
        } else {
            price = String.valueOf(toplist.getNewPrice());
        }
        ((ItemViewHolder) holder).txt_saveup_to.setText(mcontext.getString(R.string.save_upto) + " " + price + mcontext.getString(R.string.percentage) + " " + mcontext.getString(R.string.offf));
        Glide.with(mcontext.getApplicationContext()).load(mcontext.getString(R.string.imagelink) + "upload/offer/image/" + toplist.getBanner()).into(((ItemViewHolder) holder).image);
        ((ItemViewHolder) holder).btn_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.layoutClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return adsList.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView txt_saveup_to;
        ImageView image;
        Button btn_shop;

        ItemViewHolder(@NonNull View imageLayout) {
            super(imageLayout);
            txt_saveup_to = imageLayout.findViewById(R.id.txt_banner);
            image = imageLayout.findViewById(R.id.image);
            btn_shop = imageLayout.findViewById(R.id.btn_shop);

        }
    }
}
