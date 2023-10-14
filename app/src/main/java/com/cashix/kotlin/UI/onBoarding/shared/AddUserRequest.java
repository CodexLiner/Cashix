package com.cashix.kotlin.UI.onBoarding.shared;

public class AddUserRequest {
    String name , email, pincode;

    public AddUserRequest(String name, String email, String pincode) {
        this.name = name;
        this.email = email;
        this.pincode = pincode;
    }
}
