package freaktemplate.shopping.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import freaktemplate.shopping.R;
import freaktemplate.shopping.getset.CartGet.Cartdatum;
import freaktemplate.shopping.interfaces.PlusMinusButtonListener;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Cartdatum> cartlist;
    private int val;
    private PlusMinusButtonListener listener;

    public CartAdapter(Context context, List<Cartdatum> cartlist) {
        this.context = context;
        this.cartlist = cartlist;

    }

    public void PlusMinusButtonListener(PlusMinusButtonListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_cart, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        ((MyViewHolder) holder).txt_wishname.setText(cartlist.get(position).getName());
        Cartdatum cartGetSet = cartlist.get(position);
        String option = cartlist.get(position).getOption();
        String label = cartlist.get(position).getLabel();
       try {
           if (option != null && label != null) {
               if (!option.equals("null") && !label.equals("null")) {
                   List<String> list = new ArrayList<>(Arrays.asList(option.split(",")));
                   List<String> list1 = new ArrayList<>(Arrays.asList(label.split(",")));
                   if (list.size() != 0) {
                       if (list1.size() != 0) {

                           if (list1.size() == 1) {
                               ((MyViewHolder) holder).txt_color.setText(list.get(0) + " : " + list1.get(0));
                           } else if (list1.size() == 2) {
                               ((MyViewHolder) holder).txt_color.setText(list.get(0) + " : " + list1.get(0) + "      " + list.get(1) + " : " + list1.get(1));
                           } else if (list1.size() == 3) {
                               ((MyViewHolder) holder).txt_color.setText(list.get(0) + " : " + list1.get(0) + "      " + list.get(1) + " : " + list1.get(1) + "\n" + list.get(2) + " : " + list1.get(2));
                           }

                       }
                   } else {
                       ((MyViewHolder) holder).txt_color.setVisibility(View.GONE);
                   }
               } else {
                   ((MyViewHolder) holder).txt_color.setVisibility(View.GONE);
               }

           }
       } catch (Exception e) {
           e.printStackTrace();
       }


        ((MyViewHolder) holder).txt_price.setText(context.getString(R.string.dolar) + cartlist.get(position).getPrice());
        Glide.with(context).load(cartGetSet.getImage()).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(((MyViewHolder) holder).img_wish);
        ((MyViewHolder) holder).txt_count.setText(cartGetSet.getQty());
        ((MyViewHolder) holder).plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val = Integer.valueOf(((MyViewHolder) holder).txt_count.getText().toString());
                val++;
                ((MyViewHolder) holder).txt_count.setText(String.valueOf(val));
                cartlist.get(position).setQty(String.valueOf(val));
                listener.plusClick(position);
            }
        });
        ((MyViewHolder) holder).minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val = Integer.valueOf(((MyViewHolder) holder).txt_count.getText().toString());
                val--;
                if (val <= 0) {
                    if (val == 0) {
                        ((MyViewHolder) holder).txt_count.setText(String.valueOf(val));
                    }
                    cartlist.get(position).setQty(String.valueOf(val));
                    listener.minusClick(position);
                    listener.removeClick(position);
                }
                if (val > 0) {
                    ((MyViewHolder) holder).txt_count.setText(String.valueOf(val));
                    cartlist.get(position).setQty(String.valueOf(val));
                    listener.minusClick(position);
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return cartlist.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_wishname, txt_price, txt_material, txt_color, txt_count, txt_rom, txt_ram;
        ImageView img_wish, minus, plus;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_wishname = itemView.findViewById(R.id.txt_wishname);
            txt_count = itemView.findViewById(R.id.txt_count);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_material = itemView.findViewById(R.id.txt_material);
            txt_color = itemView.findViewById(R.id.txt_color);
            img_wish = itemView.findViewById(R.id.img_wish);
            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);
            txt_ram = itemView.findViewById(R.id.txt_ram);
            txt_rom = itemView.findViewById(R.id.txt_rom);
        }
    }
}
