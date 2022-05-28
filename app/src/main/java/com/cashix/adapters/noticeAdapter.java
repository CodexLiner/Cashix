package com.cashix.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cashix.R;
import com.cashix.adapters.models.noticeModel;

import java.util.List;

public class noticeAdapter extends RecyclerView.Adapter<noticeAdapter.Holder> {
    List<noticeModel> mlist;

    public noticeAdapter(List<noticeModel> mlist) {
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_itemlist , parent , false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if (mlist!=null){
//            noticeModel model = new noticeModel(mlist.get(position).getUrl());
            Glide.with(holder.itemView.getContext()).load(mlist.get(position).getUrl()).into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView image;
        public Holder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.noticeAdd);
        }
    }
}
