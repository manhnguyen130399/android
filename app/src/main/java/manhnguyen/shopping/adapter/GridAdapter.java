package manhnguyen.shopping.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import manhnguyen.shopping.R;
import manhnguyen.shopping.getset.HeaderGetSet;

public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<HeaderGetSet> data;

    public GridAdapter(Context context, ArrayList<HeaderGetSet> data) {
        this.context = context;
        this.data = data;
        Log.d("SIZE_NEED", "" + data.size());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HeaderGetSet getSet1 = data.get(position);
        int count = (position) % 3;


        if (position == 0) {
            ((MyViewHolder) holder).txt_name1.setText(getSet1.getName());
            ((MyViewHolder) holder).rel_spec.setBackgroundColor(context.getResources().getColor(R.color.orange));
            ((MyViewHolder) holder).txt_name1.setTextColor(context.getResources().getColor(R.color.white));
            ((MyViewHolder) holder).txt_brand1.setText(getSet1.getBrand());
            ((MyViewHolder) holder).rel_brand.setBackgroundColor(context.getResources().getColor(R.color.orange));
            ((MyViewHolder) holder).txt_brand1.setTextColor(context.getResources().getColor(R.color.white));
            ((MyViewHolder) holder).txt_spec1.setText(getSet1.getDpec());
            ((MyViewHolder) holder).rel_sam.setBackgroundColor(context.getResources().getColor(R.color.orange));
            ((MyViewHolder) holder).txt_spec1.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            String name = getSet1.getName();
            if (name != null) {
                if (name.equals("null")) {
                    ((MyViewHolder) holder).line.setVisibility(View.GONE);
                } else {
                    ((MyViewHolder) holder).line.setVisibility(View.VISIBLE);
                }
            } else {
                ((MyViewHolder) holder).line.setVisibility(View.GONE);

            }
            if (position == data.size() - 1) {
                ((MyViewHolder) holder).line10.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).icLine.setVisibility(View.GONE);

            } else {
                ((MyViewHolder) holder).line10.setVisibility(View.GONE);
            }
            ((MyViewHolder) holder).txt_name1.setText(name);
            ((MyViewHolder) holder).txt_brand1.setText(getSet1.getBrand());
            ((MyViewHolder) holder).txt_spec1.setText(getSet1.getDpec());
            ((MyViewHolder) holder).rel_spec.setBackgroundColor(context.getResources().getColor(R.color.white));
            ((MyViewHolder) holder).rel_brand.setBackgroundColor(context.getResources().getColor(R.color.white));
            ((MyViewHolder) holder).rel_sam.setBackgroundColor(context.getResources().getColor(R.color.white));
            ((MyViewHolder) holder).txt_name1.setTextColor(context.getResources().getColor(R.color.greay));
            ((MyViewHolder) holder).txt_brand1.setTextColor(context.getResources().getColor(R.color.black));
            ((MyViewHolder) holder).txt_spec1.setTextColor(context.getResources().getColor(R.color.black));
        }
        if (count == 2) {
            ((MyViewHolder) holder).rel_spec.setBackgroundColor(context.getResources().getColor(R.color.white));
            ((MyViewHolder) holder).rel_brand.setBackgroundColor(context.getResources().getColor(R.color.table_bg));
            ((MyViewHolder) holder).rel_sam.setBackgroundColor(context.getResources().getColor(R.color.table_bg));
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name1, txt_brand1, txt_spec1;
        RelativeLayout rel_spec, rel_brand, rel_sam;
        ImageView icLine, line3rd, line2nd, line10, line;

        MyViewHolder(@NonNull View vi) {
            super(vi);
            txt_name1 = vi.findViewById(R.id.txt_name);
            txt_brand1 = vi.findViewById(R.id.txt_brand);
            txt_spec1 = vi.findViewById(R.id.txt_spec);
            rel_spec = vi.findViewById(R.id.rel_spec);
            rel_brand = vi.findViewById(R.id.rel_brand);
            rel_sam = vi.findViewById(R.id.rel_sam);
            line = vi.findViewById(R.id.line);
            line10 = vi.findViewById(R.id.line10);
            icLine = vi.findViewById(R.id.icLine);
//            line2nd = vi.findViewById(R.id.line2nd);
        }
    }


}