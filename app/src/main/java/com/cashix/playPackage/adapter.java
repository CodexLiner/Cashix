package com.cashix.playPackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cashix.R;

public class adapter extends RecyclerView.Adapter<adapter.holder> {
    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pitemrow , parent , false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class  holder extends RecyclerView.ViewHolder {
        TextView recHead , recDesc;
        ImageView imageView ;
        Button button;
        public holder(@NonNull View itemView) {
            super(itemView);
            recDesc = itemView.findViewById(R.id.recDesc);
            recHead = itemView.findViewById(R.id.recHead);
            imageView = itemView.findViewById(R.id.recImage);
            button = itemView.findViewById(R.id.recbuttom);
        }
    }
}
