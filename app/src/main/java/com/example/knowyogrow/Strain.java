

package com.example.knowyogrow;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Strain implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("race")
    @Expose
    private String race;
    @SerializedName("flavors")
    @Expose
    private List<String> flavors = null;
    @SerializedName("effects")
    @Expose
    private Effects effects;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public List<String> getFlavors() {
        return flavors;
    }

    public void setFlavors(List<String> flavors) {
        this.flavors = flavors;
    }

    public Effects getEffects() {
        return effects;
    }

    public void setEffects(Effects effects) {
        this.effects = effects;
    }


    public class Effects implements Serializable{

        @SerializedName("positive")
        @Expose
        private List<String> positive = null;
        @SerializedName("negative")
        @Expose
        private List<String> negative = null;
        @SerializedName("medical")
        @Expose
        private List<String> medical = null;

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
}