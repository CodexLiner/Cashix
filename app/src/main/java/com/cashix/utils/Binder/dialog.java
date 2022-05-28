package com.cashix.utils.Binder;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cashix.R;

public class dialog {
    private Context context;
    private String message = "hello world how are you today ?";
    Dialog dialog;

    public dialog(Context context) {
        this.context = context;
        dialog = new Dialog(context);
    }

    public void show(){
        dialog.setContentView(R.layout.info_dialog);
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setBackgroundDrawable(null);
        Button close = dialog.findViewById(R.id.buttonConfirm);
        TextView tv = dialog.findViewById(R.id.dialogText);
        tv.setText(message);
        close.setOnClickListener((View x)->{
            dialog.dismiss();
        });
        dialog.show();
    }
    public void setMessage(String message){
        this.message = message;
    }
    public void dismiss(){
        dialog.dismiss();
    }
}
