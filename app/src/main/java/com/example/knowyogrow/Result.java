package com.example.knowyogrow;

public class Result {

    String image;
    String strainVariety;
    String race;

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public Result(String imagen, String nombreVariedad, String race) {
        this.image = imagen;
        this.strainVariety = nombreVariedad;
        this.race = race;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String imagen) {
        this.image = imagen;
    }

    public String getStrainVariety() {
        return strainVariety;
    }

    public void setStrainVariety(String nombreVariedad) {
        this.strainVariety = nombreVariedad;
    }
}
