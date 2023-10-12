package com.cashix.kotlin.UI.SignUi.VerifyOTP;

import com.google.gson.annotations.SerializedName;

public class RequestBody {
    @SerializedName("otp")
    private String otp;

    @SerializedName("mobile")
    private String mobile;

    public RequestBody(String otp, String mobile) {
        this.otp = otp;
        this.mobile = mobile;
    }
}
