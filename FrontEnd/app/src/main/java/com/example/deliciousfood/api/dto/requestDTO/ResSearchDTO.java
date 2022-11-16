package com.example.deliciousfood.api.dto.requestDTO;

import com.google.gson.annotations.SerializedName;

public class ResSearchDTO {
    @SerializedName("id")
    private String location;

    public ResSearchDTO(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
