package com.example.knowyogrow;

import java.io.Serializable;

public class StrainComplete implements Serializable {

    String name;
    Strain strain;

    public StrainComplete(String name, Strain strain) {
        this.name = name;
        this.strain = strain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Strain getStrain() {
        return strain;
    }

    public void setStrain(Strain strain) {
        this.strain = strain;
    }
}
