package com.example.knowyogrow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Effects implements Serializable {

    @SerializedName("positive")
    @Expose
    private List<String> positive = null;
    @SerializedName("negative")
    @Expose
    private List<String> negative = null;
    @SerializedName("medical")
    @Expose
    private List<String> medical = null;

    public Effects(List<String> positive, List<String> negative, List<String> medical) {
        this.positive = positive;
        this.negative = negative;
        this.medical = medical;
    }

    public List<String> getPositive() {
        return positive;
    }

    public void setPositive(List<String> positive) {
        this.positive = positive;
    }

    public List<String> getNegative() {
        return negative;
    }

    public void setNegative(List<String> negative) {
        this.negative = negative;
    }

    public List<String> getMedical() {
        return medical;
    }

    public void setMedical(List<String> medical) {
        this.medical = medical;
    }

}