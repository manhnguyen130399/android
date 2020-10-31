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
import com.willy.ratingbar.ScaleRatingBar;

import java.util.List;

import manhnguyen.shopping.R;
import manhnguyen.shopping.getset.Detail.Review;

public class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    private List<Review> reviewlist;

    public ReviewAdapter(Context context, List<Review> reviewlist) {
        this.context = context;
        this.reviewlist = reviewlist;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_review, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        float i = Float.parseFloat(reviewlist.get(position).getRatting());
        ((MyViewHolder) holder).rate_detail.setRating(i);
        ((MyViewHolder) holder).txt_dec.setText(reviewlist.get(position).getReview().replace("%20", " "));
        if (reviewlist.get(position).getUserdata() != null) {
            ((MyViewHolder) holder).txt_name.setText(reviewlist.get(position).getUserdata().getFirstName());
            Glide.with(context).load(reviewlist.get(position).getUserdata().getProfilePic()).error(R.drawable.user1).into(((MyViewHolder) holder).img_profile);
        }
    }

    @Override
    public int getItemCount() {
        return reviewlist.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_dec, txt_name, txt_date;
        ImageView img_profile;
        ScaleRatingBar rate_detail;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_dec = itemView.findViewById(R.id.txt_dec);
            img_profile = itemView.findViewById(R.id.img_profile);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_date = itemView.findViewById(R.id.txt_date);
            rate_detail = itemView.findViewById(R.id.rate_detail);
        }
    }
}
