

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

    public Strain(Integer id, String race, List<String> flavors, Effects effects) {
        this.id = id;
        this.race = race;
        this.flavors = flavors;
        this.effects = effects;
    }

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



}