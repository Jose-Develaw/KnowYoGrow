package com.example.knowyogrow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Effect {

    @SerializedName("effect")
    @Expose
    private String effect;
    @SerializedName("type")
    @Expose
    private String type;

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}