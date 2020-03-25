package com.example.knowyogrow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {

    @SerializedName("user")
    @Expose
    public String user;

    @SerializedName("password")
    @Expose
    public String password;

    public LoginData(String user, String password) {
        this.user = user;
        this.password = password;
    }
}
