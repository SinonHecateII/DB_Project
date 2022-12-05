package com.example.deliciousfood.api.dto.responseDTO;

public class RestaurantAddResponseDTO {
    String result;
    int restaurantID;

    public RestaurantAddResponseDTO(String result, int restaurantID) {
        this.result = result;
        this.restaurantID = restaurantID;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }
}
