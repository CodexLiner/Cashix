package com.cashix.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.cashix.R;

public class Toaster extends Toast {
    public Toaster(Context context, View view) {
       super(context);
       setView(view);
       setGravity(Gravity.TOP , 0 , 100);
       setDuration(Toast.LENGTH_LONG);
       super.show();

    }

    @Override
    public void setView(View view) {
        super.setView(view);
    }

    @Override
    public void setGravity(int gravity, int xOffset, int yOffset) {
        super.setGravity(gravity, xOffset, yOffset);
    }

    @Override
    public void setDuration(int duration) {
        super.setDuration(duration);
    }

}
