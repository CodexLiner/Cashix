package com.cashix.kotlin.UI.onBoarding.shared;

public class UserResponse {
    public class user {
        String _id, name, email, mobile, pincode;

        public user(String _id, String name, String email, String mobile, String pincode) {
            this._id = _id;
            this.name = name;
            this.email = email;
            this.mobile = mobile;
            this.pincode = pincode;
        }

        public String get_id() {
            return _id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getMobile() {
            return mobile;
        }

        public String getPincode() {
            return pincode;
        }
    }

    String status;
    user user;

    public UserResponse(String status, UserResponse.user user) {
        this.status = status;
        this.user = user;
    }

    public UserResponse() {
    }

    public String getStatus() {
        return status;
    }

    public UserResponse.user getUser() {
        return user;
    }
}
