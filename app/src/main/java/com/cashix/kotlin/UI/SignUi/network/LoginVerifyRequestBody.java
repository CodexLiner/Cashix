package com.cashix.kotlin.UI.SignUi.network;

import com.google.gson.annotations.SerializedName;

public class LoginVerifyRequestBody {
    @SerializedName("otp")
    private String otp;

    @SerializedName("mobile")
    private String mobile;

    public LoginVerifyRequestBody(String otp, String mobile) {
        this.otp = otp;
        this.mobile = mobile;
    }
}
