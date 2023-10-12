package com.cashix.network;

public class loginSendOtpResponse {
    String status;
    String token;
    boolean oldUser;

    public loginSendOtpResponse(String status, String token, boolean oldUser) {
        this.status = status;
        this.token = token;
        this.oldUser = oldUser;
    }

    public String getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }

    public boolean isOldUser() {
        return oldUser;
    }

    @Override
    public String toString() {
        return "loginSendOtpResponse{" +
                "status='" + status + '\'' +
                ", token='" + token + '\'' +
                ", oldUser=" + oldUser +
                '}';
    }
}
