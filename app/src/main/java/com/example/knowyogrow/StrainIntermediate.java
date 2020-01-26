package com.example.knowyogrow;

public class StrainIntermediate {

    private int id;
    private  String name;
    private  String race;

    public StrainIntermediate(int id, String name, String race) {
        this.id = id;
        this.name = name;
        this.race = race;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }
}
