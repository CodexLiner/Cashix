package com.cashix.utils.Binder;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

public class BindViews {
  private Context context;
  private   RecyclerView view;
  private Object object;
  private RecyclerBinder binder;

    public BindViews(Context context) {
        this.context = context;
    }
    /** Function For Vertical Recycler View */
    public void setVerticalRecycler( RecyclerView view, Object object){
         new RecyclerBinder(context , view).bindView(true, object);
    }
    /** Function For Horizontal Recycler View */
    public void setHorizontalRecycler( RecyclerView view, Object object){
        new RecyclerBinder(context , view).bindView(false , object);
    }
}
