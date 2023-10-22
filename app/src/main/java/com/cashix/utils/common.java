package com.cashix.utils;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.FragmentActivity;

import com.cashix.R;

public class common {
   private static final String required = "required";

   public static boolean isEmpty(EditText text){
        if (text == null || (text.getText().toString().equals(""))){
            text.setError(required);
            return false;
        }
        return true;
    }
    public static void confirmDialog(Context context){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.call_dialog);
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        LinearLayout inActive = dialog.findViewById(R.id.inActive);
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setBackgroundDrawable(null);
        inActive.setVisibility(View.VISIBLE);
        Button close = dialog.findViewById(R.id.buttonClose2);
        close.setOnClickListener((View x)->{
            dialog.dismiss();
        });
        dialog.show();
    }
    public static void editTextLength(CharSequence charSequence, Button button){
        if (!charSequence.toString().equals("") && Integer.parseInt(charSequence.toString()) > 0 && !charSequence.equals("0")) {
            button.setEnabled(true);
        }else {
            button.setEnabled(false);
        }
    }
    public static void Open(Context context , String url){
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setShowTitle(true);
        builder.setUrlBarHidingEnabled(true);
        CustomTabsIntent intent = builder.build();
        intent.launchUrl(context, Uri.parse("https://yash.meenagopal24.live/src/html/about.html"));
    }
    public static void inValidateSession(Context context){

    }
    public static void back(FragmentActivity fragmentActivity) {
        fragmentActivity.onBackPressed();
    }
    public static void load(){

    }
}
