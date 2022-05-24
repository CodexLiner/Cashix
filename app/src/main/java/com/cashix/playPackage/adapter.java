package com.cashix.playPackage;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cashix.R;

import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.holder> {
    List<model> list;

    public adapter(List<model> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pitemrow , parent , false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        model mo = list.get(position);
        holder.imageView.setImageResource(list.get(position).getImage());
        holder.recHead.setText(list.get(position).getHeading());
        holder.recDesc.setText(list.get(position).getDesc());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.imageView.getContext() , playpmnt.class);
                intent.putExtra("work" ,mo.getHeading() );
                intent.putExtra("rate" ,mo.getRate());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
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
