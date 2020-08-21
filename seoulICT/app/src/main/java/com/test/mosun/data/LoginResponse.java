package com.test.mosun.data;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("userId")
    private int userId;
    @SerializedName("userName")
    private String userName;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getUserId() {
        return userId;
    }
    public String getUserName() {
        return userName;
    }
}