package com.cashix.constants;

import android.os.AsyncTask;

public class Async_task_otp_send extends AsyncTask<Void , Void , Void> {
    private String mobile;
    private int otp;

    public Async_task_otp_send(String mNumber, int Otp){
        mobile = mNumber;
        otp = Otp;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        APIs.sendOtp(mobile , otp);
        return null;
    }
}
