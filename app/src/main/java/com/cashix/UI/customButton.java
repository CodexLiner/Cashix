package com.cashix.UI;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.cashix.R;

public class customButton {
    private boolean isEnabled = false;
    CardView cardView ;
    ConstraintLayout relativeLayout;
    ProgressBar progressBar;
    TextView buttonTex;

    public customButton(Context context , View view) {
        progressBar = view.findViewById(R.id.progress_bar);
        buttonTex = view.findViewById(R.id.buttonText);
        relativeLayout = view.findViewById(R.id.btnCustRet);
        cardView = view.findViewById(R.id.btnCustCard);
    }
    public void setText(String s ){
        buttonTex.setText(s);
    }
    public void setEnabled(Boolean enabled){
        isEnabled = enabled;
        if (enabled){
            relativeLayout.setBackgroundResource(R.drawable.custom_btn_shape_active);
            progressBar.setVisibility(View.VISIBLE);
        }else {
            relativeLayout.setBackgroundResource(R.drawable.custom_btn_shape);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
    public boolean isEnable(){
        return isEnabled;
    }
    public void activeButton(String string){
        relativeLayout.setBackgroundResource(R.drawable.custom_btn_shape_active);
        progressBar.setVisibility(View.VISIBLE);
        buttonTex.setText(string);
    }
    public void deActiveButton(String string){
        if (!isEnabled){
            relativeLayout.setBackgroundResource(R.drawable.custom_btn_shape);
            progressBar.setVisibility(View.INVISIBLE);
            buttonTex.setText(string);
        }
    }
    public void deActiveButton(){
        if (!isEnabled){
            relativeLayout.setBackgroundResource(R.drawable.custom_btn_shape);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
