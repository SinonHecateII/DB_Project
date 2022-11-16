package com.example.deliciousfood.api.dto.requestDTO;

import com.google.gson.annotations.SerializedName;

public class ResIdSearchDTO {
    @SerializedName("restaurantID")
    private int restaurantID;

    public ResIdSearchDTO(int restaurantID) {
        this.restaurantID = restaurantID;
    }

    public int getLocation() {
        return restaurantID;
    }

    public void setLocation(int restaurantID) {
        this.restaurantID = restaurantID;
    }
}
