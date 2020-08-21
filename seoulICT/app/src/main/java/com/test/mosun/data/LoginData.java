package com.test.mosun.data;

import com.google.gson.annotations.SerializedName;

public class LoginData {
//    @SerializedName("userEmail")
//    String userEmail;
//
//    @SerializedName("userPwd")
//    String userPwd;

    @SerializedName("userId")
    String userId;
    @SerializedName("userAge")
    String userAge;
    @SerializedName("userGender")
    String userGender;
    @SerializedName("userEmail")
    String userEmail;
    @SerializedName("userName")
    String userName;
    @SerializedName("userBirthday")
    String userBirthday;
    @SerializedName("sns")
    String userSns;

    //id,age,gender,email,name,birthday
    public LoginData(String userId, String userAge, String userGender, String userEmail, String userName, String userBirthday, String userSns) {
//        this.userEmail = userEmail;
//        this.userPwd = userPwd;
        this.userId = userId;
        this.userAge = userAge;
        this.userGender = userGender;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userBirthday = userBirthday;
        this.userSns = userSns;
    }


    public LoginData(String userId)
    {
        this.userId = userId;
    }
    public String getUserId() {
        return userId;
    }
}