package com.example.knowyogrow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Description {

    @SerializedName("desc")
    @Expose
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}