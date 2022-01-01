package com.crepaid.keyboard;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.crepaid.R;

public class custom_keyboard extends LinearLayout implements View.OnClickListener {

    private Button btn_1 , btn_2 ,
            btn_3 , btn_4 , btn_5 ,
            btn_6 , btn_7 , btn_8 ,
            btn_9 , btn_0 ,
            btn_dot , btn_del;
    private SparseArray<String> keyVal = new SparseArray<>();
    private InputConnection inputConnection ;

    public custom_keyboard(Context context) {
        this(context , null , 0);
    }

    public custom_keyboard(Context context, AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public custom_keyboard(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context , attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        Log.d("TAG", "inuu: hii init ");
          LayoutInflater.from(context).inflate(R.layout.custom_keyboard , this , true);
        btn_0 = (Button) findViewById(R.id.btn_0);
        btn_0.setOnClickListener(this);
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_1.setOnClickListener(this);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_2.setOnClickListener(this);
        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_3.setOnClickListener(this);
        btn_4 = (Button) findViewById(R.id.btn_4);
        btn_4.setOnClickListener(this);
        btn_5 = (Button) findViewById(R.id.btn_5);
        btn_5.setOnClickListener(this);
        btn_6= (Button) findViewById(R.id.btn_6);
        btn_6.setOnClickListener(this);
        btn_7 = (Button) findViewById(R.id.btn_7);
        btn_7.setOnClickListener(this);
        btn_8 = (Button) findViewById(R.id.btn_8);
        btn_8.setOnClickListener(this);
        btn_9= (Button) findViewById(R.id.btn_9);
        btn_9.setOnClickListener(this);
        btn_dot = (Button) findViewById(R.id.btn_dot);
        btn_dot.setOnClickListener(this);
        btn_del = (Button) findViewById(R.id.btn_del);
        btn_del.setOnClickListener(this);

        keyVal.put(R.id.btn_0 , "0");
        keyVal.put(R.id.btn_1 , "1");
        keyVal.put(R.id.btn_2 , "2");
        keyVal.put(R.id.btn_3 , "3");
        keyVal.put(R.id.btn_4 , "4");
        keyVal.put(R.id.btn_5 , "5");
        keyVal.put(R.id.btn_6 , "6");
        keyVal.put(R.id.btn_7 , "7");
        keyVal.put(R.id.btn_8 , "8");
        keyVal.put(R.id.btn_9 , "9");
        keyVal.put(R.id.btn_dot , ".");
//        keyVal.put(R.id.btn_0 , "0");


    }

    public custom_keyboard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onClick(View v) {
        if (inputConnection == null){
            return;
        }
        if (v.getId()==R.id.btn_del){
            CharSequence text = inputConnection.getSelectedText(0);
            if (TextUtils.isEmpty(text)){
                inputConnection.deleteSurroundingText(1 , 0);
            }else {
                inputConnection.commitText("", 1);
            }
        }else {
            String kv = keyVal.get(v.getId());
            inputConnection.commitText(kv , 1);
        }

    }
    public void setInputConnection (InputConnection ic){
        inputConnection = ic;
    }
}
