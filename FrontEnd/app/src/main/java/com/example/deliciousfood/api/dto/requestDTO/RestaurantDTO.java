package com.example.deliciousfood.api.dto.requestDTO;

public class RestaurantDTO {
    String location;
    String name;
    String mood;
    int photoCnt;

    public RestaurantDTO(String location, String name, String mood, int photoCnt) {
        this.location = location;
        this.name = name;
        this.mood = mood;
        this.photoCnt = photoCnt;
    }

    public RestaurantDTO() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public int getPhotoCnt() {
        return photoCnt;
    }

    public void setPhotoCnt(int photoCnt) {
        this.photoCnt = photoCnt;
    }
}
