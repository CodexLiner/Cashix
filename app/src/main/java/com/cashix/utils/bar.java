package com.cashix.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.cashix.R;

public class bar {
    private final Dialog dialog;
    public bar(Context context) {
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
