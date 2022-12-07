package com.example.deliciousfood.api.dto.requestDTO;


public class ResEditDTO {
    private int restaurantID;
    private String location;
    private String name;
    private String mood;

    public ResEditDTO(int restaurantID, String location, String name, String mood) {
        this.restaurantID = restaurantID;
        this.location = location;
        this.name = name;
        this.mood = mood;
    }

}
