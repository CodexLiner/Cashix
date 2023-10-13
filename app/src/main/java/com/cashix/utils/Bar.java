package com.cashix.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.cashix.R;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class Bar {
    private final Dialog dialog;
    @Inject
    public Bar(@ApplicationContext Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.progress_bar);
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setBackgroundDrawable(null);
    }
    public void show(){
        dialog.show();
    }
    public void hide(){
        dialog.dismiss();
    }
    public void setCancelable(boolean bool){
        dialog.setCancelable(bool);
    }
}
