package com.cashix.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cashix.R;

import java.util.ArrayList;

public class TransactionAdapters extends RecyclerView.Adapter<TransactionAdapters.Holder>{
    private ArrayList<TransactionModel> modelArrayList;
    private String authKey;
    public TransactionAdapters(ArrayList<TransactionModel> mList, String authKey){
        this.modelArrayList = mList;
        this.authKey = authKey;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.t_list , parent , false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
       if (modelArrayList!=null){
           if (modelArrayList.get(position).getStatus().equals("success")){
               holder.imageView.setImageResource(R.drawable.ic_mright);
           }else if (modelArrayList.get(position).getStatus().equals("failed")){
               holder.imageView.setImageResource(R.drawable.ic_mcross);
           }else {
               holder.imageView.setImageResource(R.drawable.ic_mcross);
           }
           holder.amount.setText("₹"+modelArrayList.get(position).getAmount());
           holder.Tdate.setText(modelArrayList.get(position).get__v());
           holder.Type.setText(modelArrayList.get(position).getType());
       }
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView amount , Type , Tdate ;
        ImageView imageView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.Tamount);
            imageView = itemView.findViewById(R.id.Timage);
            Tdate = itemView.findViewById(R.id.Tdate);
            Type = itemView.findViewById(R.id.Ttype);
        }
    }
}
