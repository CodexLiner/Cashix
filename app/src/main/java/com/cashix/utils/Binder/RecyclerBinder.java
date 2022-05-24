package com.cashix.utils.Binder;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerBinder {
   private Context context;
   private  RecyclerView recyclerView;

    public RecyclerBinder(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
    }
    public void bindView(boolean vertical, Object object){
        RecyclerView.LayoutManager layoutManager;
        if (vertical){
            layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        }else {
            layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter((RecyclerView.Adapter) object);
    }
}
