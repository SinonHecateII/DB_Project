package com.example.deliciousfood.api.dto.requestDTO;

public class ReviewSearchResIdDTO {
    int restaurantID;

    public ReviewSearchResIdDTO(int restaurantID) {
        this.restaurantID = restaurantID;
    }

    public int getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }
}
