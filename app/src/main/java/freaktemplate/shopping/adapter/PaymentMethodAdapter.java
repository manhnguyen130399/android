package freaktemplate.shopping.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import freaktemplate.shopping.R;
import freaktemplate.shopping.getset.Bankgetset;
import freaktemplate.shopping.interfaces.RadioListner;


public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodAdapter.MyViewHolder> {
    Context context;
    private ArrayList<Bankgetset> bankList;
    private int mSelectedPosition = 0;
    private RadioListner listner;

    public PaymentMethodAdapter(Context context, ArrayList<Bankgetset> bankList) {
        this.context = context;
        this.bankList = bankList;

    }

    public void setRadioListener(RadioListner listener) {
        this.listner = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cell_payment_method, viewGroup, false);


        return new MyViewHolder(itemView);
    }


    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        Bankgetset videocatGetset = bankList.get(i);
        Glide.with(context).load(videocatGetset.getImage()).into(myViewHolder.ic_payment);
        myViewHolder.txtPayment.setText(videocatGetset.getPayment_name());
        myViewHolder.txtDesc.setText(videocatGetset.getDescription());
        myViewHolder.ic_check.setChecked(mSelectedPosition == i);

        if (i == mSelectedPosition) {
            myViewHolder.ic_check.setChecked(true);
        } else {
            myViewHolder.ic_check.setChecked(false);
        }
        myViewHolder.ic_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == mSelectedPosition) {
                    myViewHolder.ic_check.setChecked(false);
                    mSelectedPosition = -1;
                    notifyDataSetChanged();
                } else {
                    mSelectedPosition = i;
                    notifyDataSetChanged();

                }
                listner.RadioClick(i,mSelectedPosition);
            }
        });
        myViewHolder.rel_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (i == mSelectedPosition) {
                    myViewHolder.ic_check.setChecked(false);
                    mSelectedPosition = -1;
                    notifyDataSetChanged();
                } else {
                    mSelectedPosition = i;
                    notifyDataSetChanged();

                }
                listner.RadioClick(i,mSelectedPosition);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bankList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtPayment, txtDesc;
        RelativeLayout rel_main;
        ImageView ic_payment;
        RadioButton ic_check;


        MyViewHolder(View view) {
            super(view);

            txtPayment = view.findViewById(R.id.txtPayment);
            txtDesc = view.findViewById(R.id.txtDesc);
            ic_payment = view.findViewById(R.id.ic_payment);
            ic_check = view.findViewById(R.id.ic_check);
            rel_main = view.findViewById(R.id.rel_main);


        }
    }


}
